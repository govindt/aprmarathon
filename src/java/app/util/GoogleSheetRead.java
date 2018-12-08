/*
 * @(#)GoogleSheetRead.java	1.31 04/08/20
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
import app.busobj.CellObject;
import app.busobj.RegistrantSheetObject;
import app.busobj.ParticipantSheetObject;
import core.util.AppException;
import core.util.DebugHandler;
import core.util.Util;
import core.util.Constants;
import java.text.SimpleDateFormat;



public class GoogleSheetRead {
	private static String spreadsheetId;
	private static String range;
	private static String registrantId;
    private static String registrantName;
    private static String registrantMiddleName;
    private static String registrantLastName;
    private static String registrantEmail;
    private static String registrantAdditionalEmail;
    private static String registrantPhoneNumber;
    private static String registrantAddress;
    private static String registrantCity;
    private static String registrantState;
    private static String registrantPincode;
    private static String registrantPAN;
    private static String registrantEvent;
    private static String registrantTypeName;
    private static String registrantSourceName;
    private static String registrantClassName;
    private static String registrantBeneficiaryName;
    private static String registrantEmergencyContact;
    private static String registrantEmergencyPhone;
    private static String registrantPaymentTypeName;
    private static String registrantPaymentStatusName;
    private static String registrantPaymentAmount;
    private static String registrantAdditionalAmount;
    private static String registrantPaymentDate;
    private static String registrantReceiptDate;
    private static String registrantPaymentDetails;
    private static String registrantPaymentTowards;
    private static String registrantPaymentReferenceId;
    private static String registrantPaymentTax;
    private static String registrantPaymentFee;
	private static String registrantEventId;
	private static String registrantPaymentId;
	private static String registrantDbOperation;
	
	private static String participantRange;
	private static String participantEventIdCol;
	private static String participantTypeCol;
	private static String participantSourceCol;
	private static String participantEventTypeCol;
	private static String participantBibNoCol;
	private static String participantGroupCol;
	private static String participantFirstNameCol;
	private static String participantMiddleNameCol;
	private static String participantLastNameCol;
	private static String participantGenderCol;
	private static String participantDateOfBirthCol;
	private static String participantAgeCategoryCol;
	private static String participantTShirtSizeCol;
	private static String participantBloodGroupCol;
	private static String participantCellPhoneCol;
	private static String participantEmailCol;
	private static String participantIdCol;
	private static String participantDbOperationCol;
	private static String registrantParticipantEmailCol;
	private static String participantEventAgeCategoryCol;
	private static String participantEventNetTimeCol;
	private static String participantEventGunTimeCol;
	
	
	 public static void init(int eventYear) {
		Properties prop = new Properties();
		try {
			InputStream in = GoogleSheetWrite.class.getClassLoader().getResourceAsStream("app/util/googlesheets.properties");
			//load a properties file from class path, inside static method
			prop.load(in);

			// Old Format
			if ( eventYear <= 2017 ) {
				spreadsheetId = prop.getProperty("googlereadsheets.2017.spreadsheetId");
				range = prop.getProperty("googlereadsheets.2017.range");
				registrantName = prop.getProperty("googlereadsheets.2017.registrantName.column");
				registrantMiddleName = prop.getProperty("googlereadsheets.2017.registrantMiddleName.column");
				registrantLastName = prop.getProperty("googlereadsheets.2017.registrantLastName.column");
				registrantEmail = prop.getProperty("googlereadsheets.2017.registrantEmail.column");
				registrantAdditionalEmail = prop.getProperty("googlereadsheets.2017.registrantAdditionalEmail.column");
				registrantPhoneNumber = prop.getProperty("googlereadsheets.2017.registrantPhoneNumber.column");
				registrantAddress = prop.getProperty("googlereadsheets.2017.registrantAddress.column");
				registrantCity = prop.getProperty("googlereadsheets.2017.registrantCity.column");
				registrantState = prop.getProperty("googlereadsheets.2017.registrantState.column");
				registrantPincode = prop.getProperty("googlereadsheets.2017.registrantPincode.column");
				registrantPAN = prop.getProperty("googlereadsheets.2017.registrantPan.column");
				registrantEvent = prop.getProperty("googlereadsheets.2017.registrantEvent.column");
				registrantTypeName = prop.getProperty("googlereadsheets.2017.registrantTypeName.column");
				registrantSourceName = prop.getProperty("googlereadsheets.2017.registrantSourceName.column");
				registrantClassName = prop.getProperty("googlereadsheets.2017.registrantClassName.column");
				registrantBeneficiaryName = prop.getProperty("googlereadsheets.2017.registrantBeneficiaryName.column");
				registrantEmergencyContact = prop.getProperty("googlereadsheets.2017.registrantEmergencyContact.column");
				registrantEmergencyPhone = prop.getProperty("googlereadsheets.2017.registrantEmergencyPhone.column");
				registrantPaymentTypeName = prop.getProperty("googlereadsheets.2017.registrantPaymentTypeName.column");
				registrantPaymentStatusName = prop.getProperty("googlereadsheets.2017.registrantPaymentStatusName.column");
				registrantPaymentAmount = prop.getProperty("googlereadsheets.2017.registrantPaymentAmount.column");
				registrantAdditionalAmount = prop.getProperty("googlereadsheets.2017.registrantAdditionalAmount.column");
				registrantPaymentDate = prop.getProperty("googlereadsheets.2017.registrantPaymentDate.column");
				registrantReceiptDate = prop.getProperty("googlereadsheets.2017.registrantReceiptDate.column");
				registrantPaymentDetails = prop.getProperty("googlereadsheets.2017.registrantPaymentDetails.column");
				registrantPaymentTowards = prop.getProperty("googlereadsheets.2017.registrantPaymentTowards.column");
				registrantPaymentReferenceId = prop.getProperty("googlereadsheets.2017.registrantPaymentReferenceId.column");
				registrantPaymentTax = prop.getProperty("googlereadsheets.2017.registrantPaymentTax.column");
				registrantPaymentFee = prop.getProperty("googlereadsheets.2017.registrantPaymentFee.column");
			} else { // 2018
				spreadsheetId = prop.getProperty("googlereadsheets.2018.spreadsheetId");
				range = prop.getProperty("googlereadsheets.2018.range");
				registrantId = prop.getProperty("googlereadsheets.2018.registrantId.column");
				registrantName = prop.getProperty("googlereadsheets.2018.registrantName.column");
				registrantMiddleName = prop.getProperty("googlereadsheets.2018.registrantMiddleName.column");
				registrantLastName = prop.getProperty("googlereadsheets.2018.registrantLastName.column");
				registrantEmail = prop.getProperty("googlereadsheets.2018.registrantEmail.column");
				registrantAdditionalEmail = prop.getProperty("googlereadsheets.2018.registrantAdditionalEmail.column");
				registrantPhoneNumber = prop.getProperty("googlereadsheets.2018.registrantPhoneNumber.column");
				registrantAddress = prop.getProperty("googlereadsheets.2018.registrantAddress.column");
				registrantCity = prop.getProperty("googlereadsheets.2018.registrantCity.column");
				registrantState = prop.getProperty("googlereadsheets.2018.registrantState.column");
				registrantPincode = prop.getProperty("googlereadsheets.2018.registrantPincode.column");
				registrantPAN = prop.getProperty("googlereadsheets.2018.registrantPan.column");
				registrantEvent = prop.getProperty("googlereadsheets.2018.registrantEvent.column");
				registrantTypeName = prop.getProperty("googlereadsheets.2018.registrantTypeName.column");
				registrantSourceName = prop.getProperty("googlereadsheets.2018.registrantSourceName.column");
				registrantClassName = prop.getProperty("googlereadsheets.2018.registrantClassName.column");
				registrantBeneficiaryName = prop.getProperty("googlereadsheets.2018.registrantBeneficiaryName.column");
				registrantEmergencyContact = prop.getProperty("googlereadsheets.2018.registrantEmergencyContact.column");
				registrantEmergencyPhone = prop.getProperty("googlereadsheets.2018.registrantEmergencyPhone.column");
				registrantPaymentTypeName = prop.getProperty("googlereadsheets.2018.registrantPaymentTypeName.column");
				registrantPaymentStatusName = prop.getProperty("googlereadsheets.2018.registrantPaymentStatusName.column");
				registrantPaymentAmount = prop.getProperty("googlereadsheets.2018.registrantPaymentAmount.column");
				registrantAdditionalAmount = prop.getProperty("googlereadsheets.2018.registrantAdditionalAmount.column");
				registrantPaymentDate = prop.getProperty("googlereadsheets.2018.registrantPaymentDate.column");
				registrantReceiptDate = prop.getProperty("googlereadsheets.2018.registrantReceiptDate.column");
				registrantPaymentDetails = prop.getProperty("googlereadsheets.2018.registrantPaymentDetails.column");
				registrantPaymentTowards = prop.getProperty("googlereadsheets.2018.registrantPaymentTowards.column");
				registrantPaymentReferenceId = prop.getProperty("googlereadsheets.2018.registrantPaymentReferenceId.column");
				registrantPaymentTax = prop.getProperty("googlereadsheets.2018.registrantPaymentTax.column");
				registrantPaymentFee = prop.getProperty("googlereadsheets.2018.registrantPaymentFee.column");
				registrantEventId = prop.getProperty("googlereadsheets.2018.registrantEventId.column");
				registrantPaymentId = prop.getProperty("googlereadsheets.2018.registrantPaymentId.column");
				registrantDbOperation = prop.getProperty("googlereadsheets.2018.registrantDbOperation.column");
				
				participantRange = prop.getProperty("googlereadsheets.2018.participantRange");
				participantEventIdCol = prop.getProperty("googlereadsheets.2018.participantEventId.column");
				participantTypeCol = prop.getProperty("googlereadsheets.2018.participantType.column");
				participantSourceCol = prop.getProperty("googlereadsheets.2018.participantSource.column");
				participantEventTypeCol = prop.getProperty("googlereadsheets.2018.participantEventType.column");
				participantBibNoCol = prop.getProperty("googlereadsheets.2018.participantBibNo.column");
				registrantParticipantEmailCol = prop.getProperty("googlereadsheets.2018.registrantParticipantEmail.column");
				participantFirstNameCol = prop.getProperty("googlereadsheets.2018.participantFirstName.column");
				participantMiddleNameCol = prop.getProperty("googlereadsheets.2018.participantMiddleName.column");
				participantLastNameCol = prop.getProperty("googlereadsheets.2018.participantLastName.column");
				participantGenderCol = prop.getProperty("googlereadsheets.2018.participantGender.column");
				participantDateOfBirthCol = prop.getProperty("googlereadsheets.2018.participantDateOfBirth.column");
				participantAgeCategoryCol = prop.getProperty("googlereadsheets.2018.participantAgeCategory.column");
				participantTShirtSizeCol = prop.getProperty("googlereadsheets.2018.participantTShirtSize.column");
				participantBloodGroupCol = prop.getProperty("googlereadsheets.2018.participantBloodGroup.column");
				participantCellPhoneCol = prop.getProperty("googlereadsheets.2018.participantCellPhone.column");
				participantEmailCol = prop.getProperty("googlereadsheets.2018.participantEmail.column");
				participantIdCol = prop.getProperty("googlereadsheets.2018.participantId.column");
				participantDbOperationCol = prop.getProperty("googlereadsheets.2018.participantDbOperation.column");
				participantGroupCol = prop.getProperty("googlereadsheets.2018.participantGroup.column");
				participantEventAgeCategoryCol = prop.getProperty("googlereadsheets.2018.participantEventAgeCategory.column");
				participantEventNetTimeCol = prop.getProperty("googlereadsheets.2018.participantEventNetTime.column");
				participantEventGunTimeCol = prop.getProperty("googlereadsheets.2018.participantEventGunTime.column");
			}
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
    }
	
    public static int ColumnLetterToNumber(String inputColumnName) {
    	int outputColumnNumber = 0;
    	if (inputColumnName == null || inputColumnName.length() == 0) {
       		throw new IllegalArgumentException("Input is not valid!");
    	}

    	int i = inputColumnName.length() - 1;
    	int t = 0;
    	while (i >= 0) {
        	char curr = inputColumnName.charAt(i);
        	outputColumnNumber = outputColumnNumber + (int) Math.pow(26, t) * (curr - 'A' + 1);
	        t++;
        	i--;
    	}
		return outputColumnNumber - 1;
    }
	
	public static ArrayList<CellObject> getEmptyElements(ParticipantSheetObject pSObj, int rowNo, String sheetName) {
		ArrayList<CellObject> cObjAL = new ArrayList<CellObject>();
		if ( pSObj.getParticipantType() == null || pSObj.getParticipantType().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantTypeCol);
                cObjAL.add(cObj);
        }
		if ( pSObj.getParticipantSource() == null || pSObj.getParticipantSource().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantSourceCol);
                cObjAL.add(cObj);
        }
		if ( pSObj.getParticipantEventType() == null || pSObj.getParticipantEventType().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantEventTypeCol);
                cObjAL.add(cObj);
        }
		if ( pSObj.getParticipantGroup() == null || pSObj.getParticipantGroup().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantGroupCol);
                cObjAL.add(cObj);
        }
		if ( pSObj.getParticipantFirstName() == null || pSObj.getParticipantFirstName().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantFirstNameCol);
                cObjAL.add(cObj);
        }
		if ( pSObj.getParticipantGender() == null || pSObj.getParticipantGender().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantGenderCol);
                cObjAL.add(cObj);
        }
		if ( pSObj.getParticipantDateOfBirth() == null || pSObj.getParticipantDateOfBirth().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantDateOfBirthCol);
                cObjAL.add(cObj);
        }
		/*if ( pSObj.getParticipantAgeCategory() == null || pSObj.getParticipantAgeCategory().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantAgeCategoryCol);
                cObjAL.add(cObj);
        }*/
		if ( pSObj.getParticipantTShirtSize() == null || pSObj.getParticipantTShirtSize().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantTShirtSizeCol);
                cObjAL.add(cObj);
        }
		if ( pSObj.getParticipantBloodGroup() == null || pSObj.getParticipantBloodGroup().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantBloodGroupCol);
                cObjAL.add(cObj);
        }
		if ( pSObj.getParticipantCellPhone() == null || pSObj.getParticipantCellPhone().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantCellPhoneCol);
                cObjAL.add(cObj);
        }
		if ( pSObj.getParticipantEmail() == null || pSObj.getParticipantEmail().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantEmailCol);
                cObjAL.add(cObj);
        }
		if ( pSObj.getParticipantDbOperation() == null || pSObj.getParticipantDbOperation().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(participantDbOperationCol);
                cObjAL.add(cObj);
        }
		return cObjAL;
	}
	public static ArrayList<CellObject> getEmptyElements(RegistrantSheetObject rObj, int rowNo, String sheetName) {
		ArrayList<CellObject> cObjAL = new ArrayList<CellObject>();
		if ( rObj.getRegistrantName() == null || rObj.getRegistrantName().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantName);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantEmail() == null || rObj.getRegistrantEmail().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantEmail);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantPhoneNumber() == null || rObj.getRegistrantPhoneNumber().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantPhoneNumber);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantAddress() == null || rObj.getRegistrantAddress().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantAddress);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantTypeName() == null || rObj.getRegistrantTypeName().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantTypeName);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantSourceName() == null || rObj.getRegistrantSourceName().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantSourceName);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantClassName() == null || rObj.getRegistrantClassName().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantClassName);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantBeneficiaryName() == null || rObj.getRegistrantBeneficiaryName().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantBeneficiaryName);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantPaymentTypeName() == null || rObj.getRegistrantPaymentTypeName().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantPaymentTypeName);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantPaymentStatusName() == null || rObj.getRegistrantPaymentStatusName().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantPaymentStatusName);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantPaymentDate() == null || rObj.getRegistrantPaymentDate().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantPaymentDate);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantReceiptDate() == null || rObj.getRegistrantReceiptDate().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantReceiptDate);
                cObjAL.add(cObj);
        }
        if ( rObj.getRegistrantPaymentTowards() == null || rObj.getRegistrantPaymentTowards().equals("") ) {
                CellObject cObj = new CellObject();
                cObj.setSheetName(sheetName);
                cObj.setRowNo(rowNo);
                cObj.setColumn(registrantPaymentTowards);
                cObjAL.add(cObj);
        }
		return cObjAL;
    }
	
	public static ArrayList<ParticipantSheetObject>getParticipantList() throws IOException, AppException {
		ArrayList<ParticipantSheetObject> pSObjAL = new ArrayList<ParticipantSheetObject>();
		ArrayList<CellObject> errorList = new ArrayList<CellObject>();
		 // Build a new authorized API client service.
		Sheets service = GoogleSheetWrite.getSheetsService();
		String buf = null;
		List<List<Object>> values = null;
		List<List<Object>> allValues = null;
		int rowNo = 0;

		StringTokenizer st = new StringTokenizer(participantRange, ",");
		while (st.hasMoreTokens()) {
			buf = st.nextToken();
			StringTokenizer st1 = new StringTokenizer(buf, "!");
			String buf1 = st1.nextToken();
			rowNo = 0;
			allValues = new ArrayList<List<Object>>();
			DebugHandler.fine("Sheet: " + buf1);
			ValueRange response = service.spreadsheets().values()
					.get(spreadsheetId, buf)
						.execute();
			values = response.getValues();
			if (values == null || values.size() == 0) {
				DebugHandler.severe("No data found in " + buf1 + " sheet.");
			}
            for (List<Object> row : values) { 
				// Leave the heading row in each sheet
				if ( rowNo != 0 ) {
					DebugHandler.fine("Adding Row: " + row);
					allValues.add(row);
				}
				rowNo++;
			}
			if (allValues == null || allValues.size() == 0) {
				DebugHandler.severe("No data found in sheet " + buf1);
			} else {
				rowNo = 0;
				for (List<Object> row : allValues) { 
					ParticipantSheetObject pSObj = new ParticipantSheetObject();
					try {
						pSObj.setParticipantEventId(Integer.parseInt((String)row.get(ColumnLetterToNumber(participantEventIdCol))));
					} catch (NumberFormatException npe) {}
					pSObj.setParticipantType((String)row.get(ColumnLetterToNumber(participantTypeCol)));
					pSObj.setParticipantSource((String)row.get(ColumnLetterToNumber(participantSourceCol)));
					pSObj.setParticipantEventType((String)row.get(ColumnLetterToNumber(participantEventTypeCol)));
					pSObj.setParticipantBibNo((String)row.get(ColumnLetterToNumber(participantBibNoCol)));
					pSObj.setParticipantGroup((String)row.get(ColumnLetterToNumber(participantGroupCol)));
					pSObj.setRegistrantParticipantEmail((String)row.get(ColumnLetterToNumber(registrantParticipantEmailCol)));
					pSObj.setParticipantFirstName((String)row.get(ColumnLetterToNumber(participantFirstNameCol)));
					pSObj.setParticipantMiddleName((String)row.get(ColumnLetterToNumber(participantMiddleNameCol)));
					pSObj.setParticipantLastName((String)row.get(ColumnLetterToNumber(participantLastNameCol)));
					pSObj.setParticipantGender((String)row.get(ColumnLetterToNumber(participantGenderCol)));
					String tmp = Util.trim((String)row.get(ColumnLetterToNumber(participantDateOfBirthCol)));
					Date date = null;
					SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
					try {
						date = dateFormatter.parse(tmp);
						pSObj.setParticipantDateOfBirth(date);
					} catch (java.text.ParseException pe) {
						DebugHandler.severe("Parse Exception while parsing " + tmp);
					}
					
					pSObj.setParticipantAgeCategory((String)row.get(ColumnLetterToNumber(participantAgeCategoryCol)));
					pSObj.setParticipantTShirtSize((String)row.get(ColumnLetterToNumber(participantTShirtSizeCol)));
					pSObj.setParticipantBloodGroup((String)row.get(ColumnLetterToNumber(participantBloodGroupCol)));
					pSObj.setParticipantCellPhone((String)row.get(ColumnLetterToNumber(participantCellPhoneCol)));
					pSObj.setParticipantEmail((String)row.get(ColumnLetterToNumber(participantEmailCol)));
					try {
						pSObj.setParticipantId(Integer.parseInt((String)row.get(ColumnLetterToNumber(participantIdCol))));
					} catch (NumberFormatException npe) {}
					pSObj.setParticipantDbOperation((String)row.get(ColumnLetterToNumber(participantDbOperationCol)));
					DebugHandler.info(participantEventAgeCategoryCol);
					pSObj.setParticipantAgeCategory((String)row.get(ColumnLetterToNumber(participantEventAgeCategoryCol)));
					DebugHandler.info(participantEventNetTimeCol);
					pSObj.setParticipantEventNetTime((String)row.get(ColumnLetterToNumber(participantEventNetTimeCol)));
					DebugHandler.info(participantEventGunTimeCol);
					pSObj.setParticipantEventGunTime((String)row.get(ColumnLetterToNumber(participantEventGunTimeCol)));
					DebugHandler.fine(pSObj);
					pSObjAL.add(pSObj);
					
					ArrayList<CellObject> rowError = getEmptyElements(pSObj, (rowNo+2), buf1);
					for (CellObject cObj : rowError) {
						errorList.add(cObj);
					}
					rowNo++;
				}
			}
		}
		DebugHandler.fine(pSObjAL);
		if ( errorList.size() > 0 ) {
			DebugHandler.severe("ERROR!!!The following cells have errors: " + errorList);
			DebugHandler.severe("Exiting. Please correct and rerun");
			return null;
		}
		return pSObjAL;
	}
	
	public static ArrayList<RegistrantSheetObject> getRegistrantList() throws IOException, AppException {
		ArrayList<RegistrantSheetObject> rObjAL = new ArrayList<RegistrantSheetObject>();
		ArrayList<CellObject> errorList = new ArrayList<CellObject>();
		 // Build a new authorized API client service.
		Sheets service = GoogleSheetWrite.getSheetsService();
		String buf = null;
		List<List<Object>> values = null;
		List<List<Object>> allValues = null;
		int rowNo = 0;

		StringTokenizer st = new StringTokenizer(range, ",");
		while (st.hasMoreTokens()) {
			buf = st.nextToken();
			StringTokenizer st1 = new StringTokenizer(buf, "!");
			String buf1 = st1.nextToken();
			rowNo = 0;
			allValues = new ArrayList<List<Object>>();
			DebugHandler.fine("Sheet: " + buf1);
			ValueRange response = service.spreadsheets().values()
					.get(spreadsheetId, buf)
						.execute();
			values = response.getValues();
			if (values == null || values.size() == 0) {
				DebugHandler.severe("No data found in " + buf1 + " sheet.");
			}
            for (List<Object> row : values) { 
				// Leave the heading row in each sheet
				if ( rowNo != 0 ) {
					DebugHandler.fine("Adding Row: " + row);
					allValues.add(row);
				}
				rowNo++;
			}
			if (allValues == null || allValues.size() == 0) {
				DebugHandler.severe("No data found in sheet " + buf1);
			} else {
				rowNo = 0;
				for (List<Object> row : allValues) { 
					RegistrantSheetObject rObj = new RegistrantSheetObject();
					try {
						rObj.setRegistrantId(Integer.parseInt((String)row.get(ColumnLetterToNumber(registrantId))));
					} catch (NumberFormatException nfe) {
					} catch (IndexOutOfBoundsException ibe) {// Sometimes Google sheets gives less array size
					}
					rObj.setRegistrantName((String)row.get(ColumnLetterToNumber(registrantName)));
					rObj.setRegistrantMiddleName((String)row.get(ColumnLetterToNumber(registrantMiddleName)));
					rObj.setRegistrantLastName((String)row.get(ColumnLetterToNumber(registrantLastName)));
					rObj.setRegistrantEmail((String)row.get(ColumnLetterToNumber(registrantEmail)));
					rObj.setRegistrantAdditionalEmail((String)row.get(ColumnLetterToNumber(registrantAdditionalEmail)));
					rObj.setRegistrantPhoneNumber((String)row.get(ColumnLetterToNumber(registrantPhoneNumber)));
					rObj.setRegistrantAddress((String)row.get(ColumnLetterToNumber(registrantAddress)));
					rObj.setRegistrantCity((String)row.get(ColumnLetterToNumber(registrantCity)));
					rObj.setRegistrantState((String)row.get(ColumnLetterToNumber(registrantState)));
					rObj.setRegistrantPincode((String)row.get(ColumnLetterToNumber(registrantPincode)));
					rObj.setRegistrantPan((String)row.get(ColumnLetterToNumber(registrantPAN)));
					rObj.setRegistrantEvent((String)row.get(ColumnLetterToNumber(registrantEvent)));
					rObj.setRegistrantTypeName((String)row.get(ColumnLetterToNumber(registrantTypeName)));
					rObj.setRegistrantSourceName((String)row.get(ColumnLetterToNumber(registrantSourceName)));
					rObj.setRegistrantClassName((String)row.get(ColumnLetterToNumber(registrantClassName)));
					rObj.setRegistrantBeneficiaryName((String)row.get(ColumnLetterToNumber(registrantBeneficiaryName)));
					String tmp = Util.trim((String)row.get(ColumnLetterToNumber(registrantEmergencyContact)));
					if ( tmp.equals("") ) {
						rObj.setRegistrantEmergencyContact(rObj.getRegistrantName());
					}
					tmp = Util.trim((String)row.get(ColumnLetterToNumber(registrantEmergencyPhone)));
					if ( tmp.equals("") ) {
						rObj.setRegistrantEmergencyPhone(rObj.getRegistrantPhoneNumber());
					}
					rObj.setRegistrantEmergencyPhone((String)row.get(ColumnLetterToNumber(registrantEmergencyPhone)));
					rObj.setRegistrantPaymentTypeName((String)row.get(ColumnLetterToNumber(registrantPaymentTypeName)));
					rObj.setRegistrantPaymentStatusName((String)row.get(ColumnLetterToNumber(registrantPaymentStatusName)));
					try {
						rObj.setRegistrantPaymentAmount(Double.parseDouble((String)row.get(ColumnLetterToNumber(registrantPaymentAmount))));
					} catch (NumberFormatException npe) {
					}
					try {
						rObj.setRegistrantAdditionalAmount(Double.parseDouble((String)row.get(ColumnLetterToNumber(registrantAdditionalAmount))));
					} catch (NumberFormatException npe) {
					}
					tmp = Util.trim((String)row.get(ColumnLetterToNumber(registrantPaymentDate)));
					Date date = null;
					SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
					try {
						date = dateFormatter.parse(tmp);
						rObj.setRegistrantPaymentDate(date);
					} catch (java.text.ParseException pe) {
						DebugHandler.severe("Parse Exception while parsing " + tmp);
					}
					tmp = Util.trim((String)row.get(ColumnLetterToNumber(registrantReceiptDate)));
					try {
						date = dateFormatter.parse(tmp);
										rObj.setRegistrantReceiptDate(date);
					} catch (java.text.ParseException pe) {
						DebugHandler.severe("Parse Exception while parsing " + tmp);
					}
					rObj.setRegistrantPaymentDetails((String)row.get(ColumnLetterToNumber(registrantPaymentDetails)));
					rObj.setRegistrantPaymentTowards((String)row.get(ColumnLetterToNumber(registrantPaymentTowards)));
					rObj.setRegistrantPaymentReferenceId((String)row.get(ColumnLetterToNumber(registrantPaymentReferenceId)));
					DebugHandler.fine("registrantPaymentReferenceId Column : " + ColumnLetterToNumber(registrantPaymentReferenceId));
					try {
						DebugHandler.fine("registrantPaymentTax Column : " + ColumnLetterToNumber(registrantPaymentTax) + ", Row: " + rowNo );
						rObj.setRegistrantPaymentTax(Double.parseDouble((String)row.get(ColumnLetterToNumber(registrantPaymentTax))));
					} catch (NumberFormatException nfe) {
					} catch (IndexOutOfBoundsException ibe) {// Sometimes Google sheets gives less array size
					}

					try {
						DebugHandler.fine("registrantPaymentFee Column : " + ColumnLetterToNumber(registrantPaymentFee));
						rObj.setRegistrantPaymentFee(Double.parseDouble((String)row.get(ColumnLetterToNumber(registrantPaymentFee))));
					} catch (NumberFormatException nfe) {
					} catch (IndexOutOfBoundsException ibe) {// Sometimes Google sheets gives less array size
					}
					try {
						DebugHandler.fine("registrantEventId Column : " + ColumnLetterToNumber(registrantEventId));
						rObj.setRegistrantEventId(Integer.parseInt((String)row.get(ColumnLetterToNumber(registrantEventId))));
					} catch (NumberFormatException nfe) {
					} catch (IndexOutOfBoundsException ibe) {// Sometimes Google sheets gives less array size
					}
					try {
						DebugHandler.fine("registrantPaymentId Column : " + ColumnLetterToNumber(registrantPaymentId));
						rObj.setRegistrantPaymentId(Integer.parseInt((String)row.get(ColumnLetterToNumber(registrantPaymentId))));
					} catch (NumberFormatException nfe) {
					} catch (IndexOutOfBoundsException ibe) {// Sometimes Google sheets gives less array size
					}
					rObj.setRegistrantDbOperation((String)row.get(ColumnLetterToNumber(registrantDbOperation)));
					DebugHandler.fine(rObj);
					rObjAL.add(rObj);
					
					ArrayList<CellObject> rowError = getEmptyElements(rObj, (rowNo+2), buf1);
					for (CellObject cObj : rowError) {
						errorList.add(cObj);
					}
					rowNo++;
				}
			}
		}
		DebugHandler.fine(rObjAL);
		if ( errorList.size() > 0 ) {
			DebugHandler.severe("ERROR!!!The following cells have errors: " + errorList);
			DebugHandler.severe("Exiting. Please correct and rerun");
			return null;
		}
		return rObjAL;
    }
	
	public String toString() {
		return "spreadsheetId: " + spreadsheetId + "\n" +
			"range: " + range + "\n" +
			"registrantId: " + registrantId + "\n" +
			"registrantName: " + registrantName + "\n" +
			"registrantMiddleName: " + registrantMiddleName + "\n" +
			"registrantLastName: " + registrantLastName + "\n" +
			"registrantEmail: " + registrantEmail + "\n" +
			"registrantAdditionalEmail: " + registrantAdditionalEmail + "\n" +
			"registrantPhoneNumber: " + registrantPhoneNumber + "\n" +
			"registrantAddress: " + registrantAddress + "\n" +
			"registrantCity: " + registrantCity + "\n" +
			"registrantState: " + registrantState + "\n" +
			"registrantPincode: " + registrantPincode + "\n" +
			"registrantPAN: " + registrantPAN + "\n" +
			"registrantEvent: " + registrantEvent + "\n" +
			"registrantTypeName: " + registrantTypeName + "\n" +
			"registrantSourceName: " + registrantSourceName + "\n" +
			"registrantClassName: " + registrantClassName + "\n" +
			"registrantBeneficiaryName: " + registrantBeneficiaryName + "\n" +
			"registrantEmergencyContact: " + registrantEmergencyContact + "\n" +
			"registrantEmergencyPhone: " + registrantEmergencyPhone + "\n" +
			"registrantPaymentTypeName: " + registrantPaymentTypeName + "\n" +
			"registrantPaymentStatusName: " + registrantPaymentStatusName + "\n" +
			"registrantPaymentAmount: " + registrantPaymentAmount + "\n" +
			"registrantAdditionalAmount: " + registrantAdditionalAmount + "\n" +
			"registrantPaymentDate: " + registrantPaymentDate + "\n" +
			"registrantReceiptDate: " + registrantReceiptDate + "\n" +
			"registrantPaymentDetails: " + registrantPaymentDetails + "\n" +
			"registrantPaymentTowards: " + registrantPaymentTowards + "\n" +
			"registrantPaymentReferenceId: " + registrantPaymentReferenceId + "\n" +
			"registrantPaymentTax: " + registrantPaymentTax + "\n" +
			"registrantPaymentFee: " + registrantPaymentFee + "\n" +
			"registrantEventId: " + registrantEventId + "\n" +
			"registrantPaymentId: " + registrantPaymentId + "\n" +
			"registrantDbOperation: " + registrantDbOperation + "\n";
	}

    public GoogleSheetRead(String event_year) {
		int eventYear = Integer.parseInt(event_year);
		GoogleSheetRead.init(eventYear);
    }
}


