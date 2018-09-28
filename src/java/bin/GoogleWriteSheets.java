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
import app.util.AppConstants;
import app.busobj.*;
import app.businterface.*;
import app.busimpl.*;


public class GoogleWriteSheets {
    private static boolean debug = false;
    private static String spreadsheetId;
    private static String participantsRange;
    private static String registrantsRange;
	private static String eventsId;
	

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
			eventsId = prop.getProperty("googlewritesheets.events.eventId");
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
    }
	
    public static void updateRegistrants() throws IOException, AppException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		Sheets service = getSheetsService();
		List<ValueRange> data = new ArrayList<>();
		RegistrantEventInterface rEIf = new RegistrantEventImpl();
		RegistrantInterface rIf = new RegistrantImpl();
		RegistrationTypeInterface rTIf = new RegistrationTypeImpl();
		RegistrationSourceInterface rSIf = new RegistrationSourceImpl();
		RegistrationClassInterface rCIf = new RegistrationClassImpl();
		BeneficiaryInterface bIf = new BeneficiaryImpl();
		RegistrantPaymentInterface rPIf = new RegistrantPaymentImpl();
		PaymentTypeInterface pTIf = new PaymentTypeImpl();
		PaymentStatusInterface pSIf = new PaymentStatusImpl();
		
		RegistrantEventObject rEObj = new RegistrantEventObject();
		int event_id = Integer.parseInt(eventsId);
		
		rEObj.setRegistrantEvent(event_id);
		ArrayList<RegistrantEventObject> rEObjArr = rEIf.getRegistrantEvents(rEObj);
		List<List<Object>> rListOfList = new ArrayList<List<Object>>();
		ValueRange vR = new ValueRange();
		vR.setRange(registrantsRange);
		
		List<Object> registrantListHeader = new ArrayList<Object>();
		
		registrantListHeader.add(AppConstants.REGISTRANT_ID_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_NAME_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_MIDDLE_NAME_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_LAST_NAME_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_EMAIL_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_ADDITIONAL_EMAIL_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_PHONE_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_ADDRESS_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_CITY_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_STATE_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_PINCODE_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_PAN_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_TYPE_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_SOURCE_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_CLASS_LABEL);
		registrantListHeader.add(AppConstants.BENEFICIARY_NAME_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_EMERGENCY_CONTACT_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_EMERGENCY_PHONE_LABEL);
		registrantListHeader.add(AppConstants.PAYMENT_TYPE_LABEL);
		registrantListHeader.add(AppConstants.PAYMENT_STATUS_LABEL);
		registrantListHeader.add(AppConstants.PAYMENT_AMOUNT_LABEL);
		registrantListHeader.add(AppConstants.PAYMENT_ADDITIONAL_AMOUNT_LABEL);
		registrantListHeader.add(AppConstants.PAYMENT_DATE_LABEL);
		registrantListHeader.add(AppConstants.RECEIPT_DATE_LABEL);
		registrantListHeader.add(AppConstants.PAYMENT_DETAILS_LABEL);
		registrantListHeader.add(AppConstants.PAYMENT_TOWARDS_LABEL);
		registrantListHeader.add(AppConstants.PAYMENT_REFERENCE_ID_LABEL);
		registrantListHeader.add(AppConstants.PAYMENT_TAX_LABEL);
		registrantListHeader.add(AppConstants.PAYMENT_FEE_LABEL);
		rListOfList.add(registrantListHeader);
		if ( rEObjArr != null ) {
			for ( int i = 0; i < rEObjArr.size(); i++) {
				rEObj = rEObjArr.get(i);
				RegistrantObject rObj = rIf.getRegistrant(rEObj.getRegistrantId());
				List<Object> registrantList = rObj.asList();
				RegistrationTypeObject rTObj = rTIf.getRegistrationType(rEObj.getRegistrantType());
				registrantList.add(rTObj.getRegistrationTypeName());
				RegistrationSourceObject rSObj = rSIf.getRegistrationSource(rEObj.getRegistrantSource());
				registrantList.add(rSObj.getRegistrationSourceName());
				RegistrationClassObject rCObj = rCIf.getRegistrationClas(rEObj.getRegistrantClass());
				registrantList.add(rCObj.getRegistrationClassName());
				BeneficiaryObject bObj = bIf.getBeneficiary(rEObj.getRegistrantBeneficiary());
				registrantList.add(bObj.getBeneficiaryName());
				registrantList.add(rEObj.getRegistrantEmergencyContact());
				registrantList.add(rEObj.getRegistrantEmergencyPhone());
				RegistrantPaymentObject rPObj = new RegistrantPaymentObject();
				rPObj.setRegistrantEvent(event_id);
				rPObj.setRegistrant(rEObj.getRegistrantId());
				ArrayList<RegistrantPaymentObject> rPObjArr = rPIf.getRegistrantPayments(rPObj);
				if ( rPObjArr != null && rPObjArr.size() == 1) {
					rPObj = rPObjArr.get(0);
					PaymentTypeObject pTObj = pTIf.getPaymentType(rPObj.getPaymentType());
					registrantList.add(pTObj.getPaymentTypeName());
					PaymentStatusObject pSObj = pSIf.getPaymentStatu(rPObj.getPaymentStatus());
					registrantList.add(pSObj.getPaymentStatusName());
					registrantList.add(rPObj.getPaymentAmount());
					registrantList.add(rPObj.getPaymentAdditionalAmount());
					registrantList.add(dateFormatter.format(rPObj.getPaymentDate()));
					registrantList.add(dateFormatter.format(rPObj.getReceiptDate()));
					registrantList.add(rPObj.getPaymentDetails());
					registrantList.add(rPObj.getPaymentTowards());
					registrantList.add(rPObj.getPaymentReferenceId());
					registrantList.add(rPObj.getPaymentTax());
					registrantList.add(rPObj.getPaymentFee());
				} else {
					throw new AppException("Found none or more than one payment for registrant : " + rObj.toString());
				}
				rListOfList.add(registrantList);
			}
			vR.setValues(rListOfList);
			data.add(vR);
		}
		 
		BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
		  .setValueInputOption("USER_ENTERED")
		  .setData(data);
		 
		BatchUpdateValuesResponse batchResult = service.spreadsheets().values()
		  .batchUpdate(spreadsheetId, batchBody)
		  .execute();
    }
	
	public static void updateParticipants() throws IOException, AppException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		Sheets service = getSheetsService();
		List<ValueRange> data = new ArrayList<>();
		ParticipantEventInterface pEIf = new ParticipantEventImpl();
		ParticipantInterface pIf = new ParticipantImpl();
		RegistrationTypeInterface rTIf = new RegistrationTypeImpl();
		EventTypeInterface eTIf = new EventTypeImpl();
		RegistrantInterface rIf = new RegistrantImpl();
		GenderInterface gIf = new GenderImpl();
		AgeCategoryInterface aCIf = new AgeCategoryImpl();
		TShirtSizeInterface tSSIf = new TShirtSizeImpl();
		BloodGroupInterface bGIf = new BloodGroupImpl();
		ParticipantEventObject pEObj = new ParticipantEventObject();
		
		int event_id = Integer.parseInt(eventsId);
		
		pEObj.setParticipantEvent(event_id);
		ArrayList<ParticipantEventObject> pEObjArr = pEIf.getParticipantEvents(pEObj);
		List<List<Object>> rListOfList = new ArrayList<List<Object>>();
		ValueRange vR = new ValueRange();
		vR.setRange(participantsRange);
		
		List<Object> participantListHeader = new ArrayList<Object>();
		
		participantListHeader.add(AppConstants.PARTICIPANT_EVENT_ID_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_TYPE_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_EVENT_TYPE_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_BIB_NO_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_GROUP_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_FIRST_NAME_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_MIDDLE_NAME_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_LAST_NAME_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_GENDER_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_DATE_OF_BIRTH_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_AGE_CATEGORY_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_T_SHIRT_SIZE_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_BLOOD_GROUP_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_CELL_PHONE_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_EMAIL_LABEL);
		rListOfList.add(participantListHeader);
		
		if ( pEObjArr != null ) {
			for ( int i = 0; i < pEObjArr.size(); i++) {
				pEObj = pEObjArr.get(i);
				ParticipantObject pObj = pIf.getParticipant(pEObj.getParticipantId());
				List<Object> participantList = new ArrayList<Object>();
				participantList.add(pEObj.getParticipantEventId());
				RegistrationTypeObject rTObj = rTIf.getRegistrationType(pEObj.getParticipantType());
				participantList.add(rTObj.getRegistrationTypeName());
				EventTypeObject eTObj = eTIf.getEventType(pEObj.getParticipantEventType());
				participantList.add(eTObj.getEventTypeName());
				participantList.add(pEObj.getParticipantBibNo());
				RegistrantObject rObj = rIf.getRegistrant(pEObj.getParticipantGroup());
				participantList.add(rObj.getRegistrantName());
				participantList.add(pObj.getParticipantFirstName());
				participantList.add(pObj.getParticipantMiddleName());
				participantList.add(pObj.getParticipantLastName());
				GenderObject gObj = gIf.getGender(pObj.getParticipantGender());
				participantList.add(gObj.getGenderName());
				participantList.add(dateFormatter.format(pObj.getParticipantDateOfBirth()));
				AgeCategoryObject aCObj = aCIf.getAgeCategory(pObj.getParticipantAgeCategory());
				if ( aCObj != null )
					participantList.add(aCObj.getAgeCategory());
				else
					participantList.add("");
				TShirtSizeObject tSSObj = tSSIf.getTShirtSize(pObj.getParticipantTShirtSize());
				participantList.add(tSSObj.getTShirtSizeName());
				BloodGroupObject bGObj = bGIf.getBloodGroup(pObj.getParticipantBloodGroup());
				if ( bGObj != null )
					participantList.add(bGObj.getBloodGroupName());
				else
					participantList.add("Unknown");
				participantList.add(pObj.getParticipantCellPhone());
				participantList.add(pObj.getParticipantEmail());
				rListOfList.add(participantList);
			}
			vR.setValues(rListOfList);
			data.add(vR);
		}
		 
		BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
		  .setValueInputOption("USER_ENTERED")
		  .setData(data);
		 
		BatchUpdateValuesResponse batchResult = service.spreadsheets().values()
		  .batchUpdate(spreadsheetId, batchBody)
		  .execute();
    }

    public String toString() {
		return 	"spreadsheetId: " + spreadsheetId + "\n" +
			"registrantsRange: " + registrantsRange + "\n" +
			"participantsRange: " + participantsRange + "\n" +
			"eventsId: " + eventsId;
    }

    public static void main(String[] args) throws IOException, AppException {
		App theApp = App.getInstance();
		GoogleWriteSheets grs = new GoogleWriteSheets();
		System.out.println(grs);
		//GoogleWriteSheets.updateRegistrants();
		GoogleWriteSheets.updateParticipants();
    }
}
