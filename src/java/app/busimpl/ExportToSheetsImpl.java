/*
 * ExportToSheetImpl.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.busimpl;

import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
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
import core.util.DebugHandler;
import app.util.App;
import app.util.GoogleSheetWrite;
import app.util.AppConstants;
import app.busobj.*;
import app.businterface.*;

/**
 * The implementation which downloads from database and updates
 * google sheets
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public class ExportToSheetsImpl implements ExportToSheetsInterface  {
	public void updateRegistrants() throws AppException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		Sheets service = GoogleSheetWrite.getSheetsService();
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
		int event_id = Integer.parseInt(GoogleSheetWrite.eventsId);
		
		rEObj.setRegistrantEvent(event_id);
		ArrayList<RegistrantEventObject> rEObjArr = rEIf.getRegistrantEvents(rEObj);
		List<List<Object>> rListOfList = new ArrayList<List<Object>>();
		ValueRange vR = new ValueRange();
		vR.setRange(GoogleSheetWrite.registrantsRange);
		
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
		registrantListHeader.add(AppConstants.REGISTRANT_EVENT_ID_LABEL);
		registrantListHeader.add(AppConstants.REGISTRANT_PAYMENT_ID_LABEL);
		registrantListHeader.add(AppConstants.DB_OPERATION_LABEL);
		
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
				if ( rPObjArr != null && rPObjArr.size() == 1 ) {
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
					registrantList.add(rEObj.getRegistrantEventId());
					registrantList.add(rPObj.getRegistrantPaymentId());
					registrantList.add(Constants.INFO_STR);
					
				} else {
					throw new AppException("Found none or more than one payment for registrant : " + rObj.toString());
				}
				rListOfList.add(registrantList);
			}
			DebugHandler.fine(rListOfList);
			vR.setValues(rListOfList);
			data.add(vR);
			DebugHandler.fine(data);
		}
		try {
			service.spreadsheets().values().clear(GoogleSheetWrite.spreadsheetId, 
				GoogleSheetWrite.registrantsRange, new ClearValuesRequest()).execute();
		} catch (IOException ioe) {
			throw new AppException("IOException during Clearing Contents. " + ioe.getMessage());
		}
		BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
		  .setValueInputOption("USER_ENTERED")
		  .setData(data);
		try {
			BatchUpdateValuesResponse batchResult = service.spreadsheets().values()
			  .batchUpdate(GoogleSheetWrite.spreadsheetId, batchBody)
			  .execute();
		} catch (IOException ioe) {
			throw new AppException("IOException during batchUpdate. " + ioe.getMessage());
		}
	}
	
	public void updateParticipants() throws AppException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		Sheets service = GoogleSheetWrite.getSheetsService();
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
		RegistrationSourceInterface rSIf = new RegistrationSourceImpl();
		RegistrantEventInterface rEIf = new RegistrantEventImpl();
		
		int event_id = Integer.parseInt(GoogleSheetWrite.eventsId);
		
		pEObj.setParticipantEvent(event_id);
		ArrayList<ParticipantEventObject> pEObjArr = pEIf.getParticipantEvents(pEObj);
		List<List<Object>> rListOfList = new ArrayList<List<Object>>();
		ValueRange vR = new ValueRange();
		vR.setRange(GoogleSheetWrite.participantsRange);
		
		List<Object> participantListHeader = new ArrayList<Object>();
		
		participantListHeader.add(AppConstants.PARTICIPANT_EVENT_ID_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_TYPE_LABEL);
		participantListHeader.add(AppConstants.REGISTRANT_SOURCE_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_EVENT_TYPE_LABEL);
		participantListHeader.add(AppConstants.PARTICIPANT_BIB_NO_LABEL);
		//participantListHeader.add(AppConstants.PARTICIPANT_GROUP_LABEL);
		participantListHeader.add(AppConstants.REGISTRANT_EMAIL_LABEL);
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
		participantListHeader.add(AppConstants.PARTICIPANT_ID_LABEL);
		participantListHeader.add(AppConstants.DB_OPERATION_LABEL);
		rListOfList.add(participantListHeader);
		
		if ( pEObjArr != null ) {
			for ( int i = 0; i < pEObjArr.size(); i++) {
				pEObj = pEObjArr.get(i);
				ParticipantObject pObj = pIf.getParticipant(pEObj.getParticipantId());
				List<Object> participantList = new ArrayList<Object>();
				participantList.add(pEObj.getParticipantEventId());
				RegistrationTypeObject rTObj = rTIf.getRegistrationType(pEObj.getParticipantType());
				participantList.add(rTObj.getRegistrationTypeName());
				RegistrantEventObject rEObj = rEIf.getRegistrantEvent(pEObj.getParticipantGroup());
				RegistrationSourceObject rSObj = rSIf.getRegistrationSource(rEObj.getRegistrantSource());
				participantList.add(rSObj.getRegistrationSourceName());
				EventTypeObject eTObj = eTIf.getEventType(pEObj.getParticipantEventType());
				participantList.add(eTObj.getEventTypeName());
				participantList.add(pEObj.getParticipantBibNo());
				RegistrantObject rObj = rIf.getRegistrant(rEObj.getRegistrantId());
				//participantList.add(rObj.getRegistrantName());
				participantList.add(rObj.getRegistrantEmail());
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
				participantList.add(pObj.getParticipantId());
				participantList.add(Constants.INFO_STR);
				rListOfList.add(participantList);
			}
			vR.setValues(rListOfList);
			data.add(vR);
		}
		try {
			service.spreadsheets().values().clear(GoogleSheetWrite.spreadsheetId, 
					GoogleSheetWrite.participantsRange, new ClearValuesRequest()).execute();
		} catch (IOException ioe) {
			throw new AppException("IOException during Clearing Contents. " + ioe.getMessage());
		}
		BatchUpdateValuesRequest batchBody = new BatchUpdateValuesRequest()
		  .setValueInputOption("USER_ENTERED")
		  .setData(data);
		try {
			BatchUpdateValuesResponse batchResult = service.spreadsheets().values()
			  .batchUpdate(GoogleSheetWrite.spreadsheetId, batchBody)
			  .execute();
		} catch (IOException ioe) {
			throw new AppException("IOException during batchUpdate. " + ioe.getMessage());
		}
	}
}
