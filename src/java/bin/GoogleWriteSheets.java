import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Date;
import java.text.SimpleDateFormat;
import core.util.AppException;
import core.util.Util;
import core.util.Constants;
import app.util.App;
import app.busobj.*;
import app.businterface.*;
import app.busimpl.*;


public class GoogleWriteSheets {
    private static boolean debug = false;
    private static String spreadsheetId;
    private static String participantsRange;
    private static String registrantsRange;
 

    /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Sheets API Java Read";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-read");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(SheetsScopes.SPREADSHEETS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public GoogleWriteSheets() {
		init();
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = GoogleWriteSheets.class.getClassLoader().getResourceAsStream("core/util/client_secret.json");
		if ( in == null ) {
			System.err.println("Unable to read client_secret.json from " +  GoogleReadSheets.class.getClassLoader());
		}
		GoogleClientSecrets clientSecrets =
			GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow =
				new GoogleAuthorizationCodeFlow.Builder(
						HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(DATA_STORE_FACTORY)
				.setAccessType("offline")
				.build();
		Credential credential = new AuthorizationCodeInstalledApp(
			flow, new LocalServerReceiver()).authorize("user");
		if ( debug )
				System.out.println(
						"Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void init() {
		Properties prop = new Properties();
		try {
			InputStream in = GoogleWriteSheets.class.getClassLoader().getResourceAsStream("core/util/googlesheets.properties");
			//load a properties file from class path, inside static method
			prop.load(in);

			//get the property value and print it out
			spreadsheetId = prop.getProperty("googlewritesheets.spreadsheetId");
			participantsRange = prop.getProperty("googlewritesheets.participants.range");
			registrantsRange = prop.getProperty("googlewritesheets.registrants.range");
		} 
		catch (IOException ex) {
				ex.printStackTrace();
		}
    }
	
    public static ArrayList<RegistrantObject> updateRegistrants() throws IOException, AppException {
		ArrayList<RegistrantObject> rObjAL = new ArrayList<RegistrantObject>();
		Sheets service = getSheetsService();
		List<ValueRange> data = new ArrayList<>();
		RegistrantInterface rIf = new RegistrantImpl();
		RegistrantObject[] rObjArr = rIf.getAllRegistrants();
		List<List<Object>> rListOfList = new ArrayList<List<Object>>();
		ValueRange vR = new ValueRange();
		vR.setRange(registrantsRange);
		
			
		if ( rObjArr != null ) {
			for ( int i = 0; i < rObjArr.length; i++)
				rListOfList.add(rObjArr[i].asList());
			vR.setValues(rListOfList);
			data.add(vR);
		}
		/*data.add(new ValueRange()
		  .setRange(registrantsRange)
		  .setValues(Arrays.asList(
			Arrays.asList("January Total", "=B2+B3"))));
		data.add(new ValueRange()
		  .setRange(registrantsRange)
		  .setValues(Arrays.asList(
			Arrays.asList("February Total", "=B5+B6"))));*/
		 
		BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
		  .setValueInputOption("USER_ENTERED")
		  .setData(data);
		 
		BatchUpdateValuesResponse batchResult = service.spreadsheets().values()
		  .batchUpdate(spreadsheetId, batchBody)
		  .execute();
		if (debug)
			System.out.println(rObjAL);
	
		return rObjAL;
    }
	
	public static ArrayList<ParticipantObject> getParticipants() throws IOException, AppException {
		ArrayList<ParticipantObject> rObjAL = new ArrayList<ParticipantObject>();
		Sheets service = getSheetsService();
		if (debug)
			System.out.println(rObjAL);
	
		return rObjAL;
    }

    public String toString() {
		return 	"spreadsheetId: " + spreadsheetId + "\n" +
			"registrantsRange: " + registrantsRange + "\n" +
			"participantsRange: " + participantsRange;
    }

    public static void main(String[] args) throws IOException, AppException {
		App theApp = App.getInstance();
		GoogleWriteSheets grs = new GoogleWriteSheets();
		System.out.println(grs);
		GoogleWriteSheets.updateRegistrants();
		GoogleWriteSheets.getParticipants();
    }
}
