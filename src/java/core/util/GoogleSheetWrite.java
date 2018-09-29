/*
 * @(#)GoogleSheetWrite.java	1.31 04/08/20
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */




package core.util;

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



public class GoogleSheetWrite {
    public static String spreadsheetId;
    public static String participantsRange;
    public static String registrantsRange;
	public static String eventsId;
	

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

    public GoogleSheetWrite() {
		init();
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = GoogleSheetWrite.class.getClassLoader().getResourceAsStream("core/util/client_secret.json");
		if ( in == null ) {
			System.err.println("Unable to read client_secret.json from " +  GoogleSheetWrite.class.getClassLoader());
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
		DebugHandler.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws AppException {
		Credential credential = null;
		try {
			credential = authorize();
		} catch (IOException ioe) {
			throw new AppException ("IOException during authorize: " + ioe.getMessage());
		}
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void init() {
		Properties prop = new Properties();
		try {
			InputStream in = GoogleSheetWrite.class.getClassLoader().getResourceAsStream("core/util/googlesheets.properties");
			//load a properties file from class path, inside static method
			prop.load(in);

			//get the property value and print it out
			spreadsheetId = prop.getProperty("googlesheetwrite.spreadsheetId");
			participantsRange = prop.getProperty("googlesheetwrite.participants.range");
			registrantsRange = prop.getProperty("googlesheetwrite.registrants.range");
			eventsId = prop.getProperty("googlesheetwrite.events.eventId");
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
    }
	
	public String toString() {
		return 	"spreadsheetId: " + spreadsheetId + "\n" +
			"registrantsRange: " + registrantsRange + "\n" +
			"participantsRange: " + participantsRange + "\n" +
			"eventsId: " + eventsId;
    }

}


