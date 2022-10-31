/*
 * @(#)GoogleSheetWrite.java	1.31 04/08/20
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */




package app.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
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
import java.io.FileNotFoundException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Date;
import java.text.SimpleDateFormat;
import core.util.AppException;
import core.util.DebugHandler;



public class GoogleSheetWrite {
    public static String spreadsheetId;
    public static String participantsRange;
    public static String registrantsRange;
    public static String eventsId;
    
	
	

    /** Application name. */
    private static final String APPLICATION_NAME = "Google Sheets API Java Read"; 
    private static final String TOKENS_DIRECTORY_PATH = "tokens.sheets";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "client_secret.json";

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    public static final List<String> SCOPES =
        Arrays.asList(SheetsScopes.SPREADSHEETS);
	
    /**
   * Creates an authorized Credential object.
   *
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
      throws IOException {
    // Load client secrets.
    InputStream in = GoogleSheetWrite.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }

    public GoogleSheetWrite(String event_id) {
	init();
	eventsId = event_id;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws AppException {
	Credential credential = null;
        NetHttpTransport HTTP_TRANSPORT = null;
	try {
		HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	} catch (GeneralSecurityException gse) {
		gse.printStackTrace();
	} catch (IOException ioe1) {
		ioe1.printStackTrace();
	}
    
	try {
		credential = getCredentials(HTTP_TRANSPORT);
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
			InputStream in = GoogleSheetWrite.class.getClassLoader().getResourceAsStream("app/util/googlesheets.properties");
			//load a properties file from class path, inside static method
			prop.load(in);

			//get the property value and print it out
			spreadsheetId = prop.getProperty("googlesheetwrite.spreadsheetId");
			participantsRange = prop.getProperty("googlesheetwrite.participants.range");
			registrantsRange = prop.getProperty("googlesheetwrite.registrants.range");
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
    }
	
	public String toString() {
		return 	"spreadsheetId: " + spreadsheetId + "\n" +
			"registrantsRange: " + registrantsRange + "\n" +
			"participantsRange: " + participantsRange + "\n" +
			"eventsId: " + eventsId + "\n";
    }

}



