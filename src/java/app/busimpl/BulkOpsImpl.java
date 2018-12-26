/*
 * BulkOpsImpl.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.busimpl;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import app.util.App;
import app.util.AppConstants;
import app.util.ReceiptGenerate;
import app.util.GoogleSheetRead;

import core.util.AppException;
import core.util.DebugHandler;
import core.util.SendMail;
import core.util.Constants;
import core.util.SendGMail;

import app.businterface.SendMailInterface;
import app.businterface.RegistrantPaymentInterface;
import app.businterface.PaymentTypeInterface;
import app.businterface.RegistrantInterface;
import app.businterface.PaymentStatusInterface;
import app.businterface.RegistrantEventInterface;
import app.businterface.BeneficiaryInterface;
import app.businterface.RegistrationTypeInterface;
import app.businterface.RegistrationSourceInterface;
import app.businterface.RegistrationClassInterface;
import app.businterface.ParticipantEventInterface;
import app.businterface.ParticipantInterface;
import app.businterface.RegistrationTypeInterface;
import app.businterface.EventTypeInterface;
import app.businterface.GenderInterface;
import app.businterface.TShirtSizeInterface;
import app.businterface.BloodGroupInterface;

import app.busobj.SendMailObject;
import app.busobj.RegistrantSheetObject;
import app.busobj.ParticipantSheetObject;
import app.busobj.ResultsSheetObject;
import app.busobj.RegistrantPaymentObject;
import app.busobj.RegistrantObject;
import app.busobj.PaymentTypeObject;
import app.busobj.PaymentStatusObject;
import app.busobj.RegistrantEventObject;
import app.busobj.BeneficiaryObject;
import app.busobj.RegistrationTypeObject;
import app.busobj.RegistrationSourceObject;
import app.busobj.RegistrationClassObject;
import app.busobj.ParticipantObject;
import app.busobj.ParticipantEventObject;
import app.busobj.RegistrationTypeObject;
import app.busobj.EventTypeObject;
import app.busobj.GenderObject;
import app.busobj.TShirtSizeObject;
import app.busobj.BloodGroupObject;



import app.businterface.BulkOpsInterface;

import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import javax.mail.MessagingException;
import java.security.GeneralSecurityException;

/**
 * The implementation which downloads from database and updates
 * google sheets
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public class BulkOpsImpl implements BulkOpsInterface  {
	ArrayList<RegistrantSheetObject> rSObjAL = new ArrayList<RegistrantSheetObject>();
	ArrayList<ParticipantSheetObject> pSObjAL = new ArrayList<ParticipantSheetObject>();
	ArrayList<ResultsSheetObject> rObjAL = new ArrayList<ResultsSheetObject>();
	String contents = "";
	ReceiptGenerate rg = new ReceiptGenerate();
	
	public void init(String year) throws AppException {
		GoogleSheetRead gsw = new GoogleSheetRead(year);
		try {
			rSObjAL = GoogleSheetRead.getRegistrantList();
			pSObjAL = GoogleSheetRead.getParticipantList();
			rObjAL =  GoogleSheetRead.getResultsList();
		} catch (IOException ioe) {
			throw new AppException("IO Exception getting Registrant List");
		}
		InputStream buf = rg.getClass().getResourceAsStream(AppConstants.RECEIPT_MAIL_BODY_TEMPLATE);
		contents = SendMail.getContents(buf);
	}
	
	public Integer bulkReceiptGenerate(String year) throws AppException {
		init(year);
		for (RegistrantSheetObject rSObj : rSObjAL ) {
			String paymentStatus = rSObj.getRegistrantPaymentStatusName();
			if ( paymentStatus != null ) {
				SendMailObject smObj = new SendMailObject(	rSObj, 
															AppConstants.RECEIPT_MAIL_SUBJECT,
															contents,
															AppConstants.RECEIPT_NO_PREFIX);
				if ( paymentStatus.equals("Manual Receipt") ) {
					try {
						String docFile = rg.createReceipt(AppConstants.RECEIPT_TEMPLATE, smObj, true);
						DebugHandler.fine("Created Receipt File: " + docFile);
						File docFilePtr = new File(docFile);
						SendGMail.sendMessage("aprct.treasurer@gmail.com", AppConstants.EMAIL_FROM, 
							smObj.getSubject(), smObj.getBody(), docFilePtr);
					} catch (InvalidFormatException ife) {
						throw new AppException("Caught exception while creating file. " + ife.getMessage());
					} catch (IOException ioe) {
						throw new AppException("IO Exception creating Receipt");
					} catch (MessagingException me) {
						throw new AppException("Messaging Exception creating Receipt");
					} catch (GeneralSecurityException gse) {
						throw new AppException("General Security Exception creating Receipt");
					}
				}
				else if ( paymentStatus.equals("Send Email Receipt") ) {
					SendMailInterface sMIf = new SendMailImpl();
					Integer result = sMIf.mailReceiptRegistrants(smObj);
					DebugHandler.fine("Result: " + result);
				}
			
			}
		}
		return new Integer(0);
	}
	
	public Integer bulkUpdateRegistrants(String year, int event_id) throws AppException {
		Integer result = new Integer(0);
		init(year);
		if ( rSObjAL == null ) 
			return new Integer(1);
		for (RegistrantSheetObject rSObj : rSObjAL ) {
			String dbOperation = rSObj.getRegistrantDbOperation();
			if ( dbOperation != null ) {
				if ( dbOperation.equals(Constants.UPDATE_STR) ) {
					RegistrantPaymentInterface rPIf = new RegistrantPaymentImpl();
					PaymentTypeInterface pTIf = new PaymentTypeImpl();
					RegistrantInterface rIf = new RegistrantImpl();
					RegistrantEventInterface rEIf = new RegistrantEventImpl();
					BeneficiaryInterface bIf = new BeneficiaryImpl();
					RegistrationTypeInterface rTIf = new RegistrationTypeImpl();
					RegistrationSourceInterface rSIf = new RegistrationSourceImpl();
					RegistrationClassInterface rCIf = new RegistrationClassImpl();
					
					RegistrantPaymentObject rPObj = rPIf.getRegistrantPayment(rSObj.getRegistrantPaymentId());
					RegistrantObject rObj = rIf.getRegistrant(rSObj.getRegistrantId());
					RegistrantEventObject rEObj = rEIf.getRegistrantEvent(rSObj.getRegistrantEventId());
					BeneficiaryObject bObj = new BeneficiaryObject();
					RegistrationTypeObject rTObj = new RegistrationTypeObject();
					RegistrationSourceObject rSoObj = new RegistrationSourceObject();
					RegistrationClassObject rCObj = new RegistrationClassObject();
					
					PaymentTypeObject pTObj = new PaymentTypeObject();
					pTObj.setPaymentTypeName(rSObj.getRegistrantPaymentTypeName());
					ArrayList<PaymentTypeObject> pTObjAL = pTIf.getPaymentTypes(pTObj);
					pTObj = pTObjAL.get(0);
					DebugHandler.fine(pTObj);
					rPObj.setPaymentType(pTObj.getPaymentTypeId());
					PaymentStatusInterface pSIf = new PaymentStatusImpl();
					PaymentStatusObject pSObj = new PaymentStatusObject();
					pSObj.setPaymentStatusName(rSObj.getRegistrantPaymentStatusName());
					ArrayList<PaymentStatusObject> pSObjAL = pSIf.getPaymentStatus(pSObj);
					pSObj = pSObjAL.get(0);
					DebugHandler.fine(pSObj);
					// Updates for Registrant Payments
					rPObj.setPaymentStatus(pSObj.getPaymentStatusId());
					rPObj.setPaymentAmount(rSObj.getRegistrantPaymentAmount());
					rPObj.setPaymentAdditionalAmount(rSObj.getRegistrantAdditionalAmount());
					rPObj.setPaymentDate(rSObj.getRegistrantPaymentDate());
					rPObj.setReceiptDate(rSObj.getRegistrantReceiptDate());
					rPObj.setPaymentDetails(rSObj.getRegistrantPaymentDetails());
					rPObj.setPaymentTowards(rSObj.getRegistrantPaymentTowards());
					rPObj.setPaymentReferenceId(rSObj.getRegistrantPaymentReferenceId());
					rPObj.setPaymentTax(rSObj.getRegistrantPaymentTax());
					rPObj.setPaymentFee(rSObj.getRegistrantPaymentFee());
					DebugHandler.fine(rPObj);
					
					// Updates for Registrant 
					rObj.setRegistrantName(rSObj.getRegistrantName());
					rObj.setRegistrantMiddleName(rSObj.getRegistrantMiddleName());
					rObj.setRegistrantEmail(rSObj.getRegistrantEmail());
					rObj.setRegistrantAdditionalEmail(rSObj.getRegistrantAdditionalEmail());
					rObj.setRegistrantPhone(rSObj.getRegistrantPhoneNumber());
					rObj.setRegistrantAddress(rSObj.getRegistrantAddress());
					rObj.setRegistrantCity(rSObj.getRegistrantCity());
					rObj.setRegistrantState(rSObj.getRegistrantState());
					rObj.setRegistrantPincode(rSObj.getRegistrantPincode());
					rObj.setRegistrantPan(rSObj.getRegistrantPan());
					DebugHandler.fine(rObj);
					
					// Updates for Registrant Event
					
					rCObj.setRegistrationClassName(rSObj.getRegistrantClassName());
					ArrayList<RegistrationClassObject> rCObjAL = rCIf.getRegistrationClass(rCObj);
					rCObj = rCObjAL.get(0);
					DebugHandler.fine(rCObj);
					rEObj.setRegistrantClass(rCObj.getRegistrationClassId());
					
					rSoObj.setRegistrationSourceName(rSObj.getRegistrantSourceName());
					ArrayList<RegistrationSourceObject> rSoObjAL = rSIf.getRegistrationSources(rSoObj);
					rSoObj = rSoObjAL.get(0);
					DebugHandler.fine(rSoObj);
					rEObj.setRegistrantSource(rSoObj.getRegistrationSourceId());
					
					rTObj.setRegistrationTypeName(rSObj.getRegistrantTypeName());
					ArrayList<RegistrationTypeObject> rTObjAL = rTIf.getRegistrationTypes(rTObj);
					rTObj = rTObjAL.get(0);
					DebugHandler.fine(rTObj);
					rEObj.setRegistrantType(rTObj.getRegistrationTypeId());
					
					bObj.setBeneficiaryName(rSObj.getRegistrantBeneficiaryName());
					ArrayList<BeneficiaryObject> bObjAL = bIf.getBeneficiarys(bObj);
					bObj = bObjAL.get(0);
					DebugHandler.fine(bObj);
					rEObj.setRegistrantBeneficiary(bObj.getBeneficiaryId());
					
					rEObj.setRegistrantEmergencyContact(rSObj.getRegistrantEmergencyContact());
					rEObj.setRegistrantEmergencyPhone(rSObj.getRegistrantEmergencyPhone());
					DebugHandler.fine(rEObj);
					result = rIf.updateRegistrant(rObj);
					result = rPIf.updateRegistrantPayment(rPObj);
					result = rEIf.updateRegistrantEvent(rEObj);
					
					DebugHandler.fine("Result: " + result);
				} else if ( dbOperation.equals(Constants.INSERT_STR) ) {
					RegistrantPaymentInterface rPIf = new RegistrantPaymentImpl();
					PaymentTypeInterface pTIf = new PaymentTypeImpl();
					RegistrantInterface rIf = new RegistrantImpl();
					RegistrantEventInterface rEIf = new RegistrantEventImpl();
					BeneficiaryInterface bIf = new BeneficiaryImpl();
					RegistrationTypeInterface rTIf = new RegistrationTypeImpl();
					RegistrationSourceInterface rSIf = new RegistrationSourceImpl();
					RegistrationClassInterface rCIf = new RegistrationClassImpl();
					
					RegistrantPaymentObject rPObj = new RegistrantPaymentObject();
					RegistrantObject rObj = new RegistrantObject();
					RegistrantEventObject rEObj = new RegistrantEventObject();
					BeneficiaryObject bObj = new BeneficiaryObject();
					RegistrationTypeObject rTObj = new RegistrationTypeObject();
					RegistrationSourceObject rSoObj = new RegistrationSourceObject();
					RegistrationClassObject rCObj = new RegistrationClassObject();
					
					PaymentTypeObject pTObj = new PaymentTypeObject();
					pTObj.setPaymentTypeName(rSObj.getRegistrantPaymentTypeName());
					ArrayList<PaymentTypeObject> pTObjAL = pTIf.getPaymentTypes(pTObj);
					pTObj = pTObjAL.get(0);
					DebugHandler.fine(pTObj);
					rPObj.setPaymentType(pTObj.getPaymentTypeId());
					PaymentStatusInterface pSIf = new PaymentStatusImpl();
					PaymentStatusObject pSObj = new PaymentStatusObject();
					pSObj.setPaymentStatusName(rSObj.getRegistrantPaymentStatusName());
					ArrayList<PaymentStatusObject> pSObjAL = pSIf.getPaymentStatus(pSObj);
					pSObj = pSObjAL.get(0);
					DebugHandler.fine(pSObj);
					// Insert for Registrant Payments
					rPObj.setRegistrantEvent(event_id);
					rPObj.setPaymentStatus(pSObj.getPaymentStatusId());
					rPObj.setPaymentAmount(rSObj.getRegistrantPaymentAmount());
					rPObj.setPaymentAdditionalAmount(rSObj.getRegistrantAdditionalAmount());
					rPObj.setPaymentDate(rSObj.getRegistrantPaymentDate());
					rPObj.setReceiptDate(rSObj.getRegistrantReceiptDate());
					rPObj.setPaymentDetails(rSObj.getRegistrantPaymentDetails());
					rPObj.setPaymentTowards(rSObj.getRegistrantPaymentTowards());
					rPObj.setPaymentReferenceId(rSObj.getRegistrantPaymentReferenceId());
					rPObj.setPaymentTax(rSObj.getRegistrantPaymentTax());
					rPObj.setPaymentFee(rSObj.getRegistrantPaymentFee());
					
					// Insert for Registrant 
					rObj.setRegistrantName(rSObj.getRegistrantName());
					rObj.setRegistrantMiddleName(rSObj.getRegistrantMiddleName());
					rObj.setRegistrantEmail(rSObj.getRegistrantEmail());
					rObj.setRegistrantAdditionalEmail(rSObj.getRegistrantAdditionalEmail());
					rObj.setRegistrantPhone(rSObj.getRegistrantPhoneNumber());
					rObj.setRegistrantAddress(rSObj.getRegistrantAddress());
					rObj.setRegistrantCity(rSObj.getRegistrantCity());
					rObj.setRegistrantState(rSObj.getRegistrantState());
					rObj.setRegistrantPincode(rSObj.getRegistrantPincode());
					rObj.setRegistrantPan(rSObj.getRegistrantPan());
					DebugHandler.fine(rObj);
					
					// Insert for Registrant Event
					rEObj.setRegistrantEvent(event_id);
					rCObj.setRegistrationClassName(rSObj.getRegistrantClassName());
					ArrayList<RegistrationClassObject> rCObjAL = rCIf.getRegistrationClass(rCObj);
					rCObj = rCObjAL.get(0);
					DebugHandler.fine(rCObj);
					rEObj.setRegistrantClass(rCObj.getRegistrationClassId());
					
					rSoObj.setRegistrationSourceName(rSObj.getRegistrantSourceName());
					ArrayList<RegistrationSourceObject> rSoObjAL = rSIf.getRegistrationSources(rSoObj);
					rSoObj = rSoObjAL.get(0);
					DebugHandler.fine(rSoObj);
					rEObj.setRegistrantSource(rSoObj.getRegistrationSourceId());
					
					rTObj.setRegistrationTypeName(rSObj.getRegistrantTypeName());
					ArrayList<RegistrationTypeObject> rTObjAL = rTIf.getRegistrationTypes(rTObj);
					rTObj = rTObjAL.get(0);
					DebugHandler.fine(rTObj);
					rEObj.setRegistrantType(rTObj.getRegistrationTypeId());
					
					bObj.setBeneficiaryName(rSObj.getRegistrantBeneficiaryName());
					ArrayList<BeneficiaryObject> bObjAL = bIf.getBeneficiarys(bObj);
					bObj = bObjAL.get(0);
					DebugHandler.fine(bObj);
					rEObj.setRegistrantBeneficiary(bObj.getBeneficiaryId());
					
					rEObj.setRegistrantEmergencyContact(rSObj.getRegistrantEmergencyContact());
					rEObj.setRegistrantEmergencyPhone(rSObj.getRegistrantEmergencyPhone());
					
					result = rIf.addRegistrant(rObj);
					rPObj.setRegistrant(result.intValue());
					rEObj.setRegistrantId(result.intValue());
					DebugHandler.fine(rPObj);
					result = rPIf.addRegistrantPayment(rPObj);
					DebugHandler.fine(rEObj);
					result = rEIf.addRegistrantEvent(rEObj);				
					DebugHandler.fine("Result: " + result);
				}
				else if ( dbOperation.equals(Constants.DELETE_STR) ) {
					RegistrantPaymentInterface rPIf = new RegistrantPaymentImpl();
					RegistrantInterface rIf = new RegistrantImpl();
					RegistrantEventInterface rEIf = new RegistrantEventImpl();
					ParticipantEventInterface pEIf = new ParticipantEventImpl();
					ParticipantInterface pIf = new ParticipantImpl();
					RegistrantPaymentObject rPObj = rPIf.getRegistrantPayment(rSObj.getRegistrantPaymentId());
					if ( rPObj != null ) {
						DebugHandler.info("Deleteing Registrant Payment " + rPObj.getRegistrantPaymentId());
						rPIf.deleteRegistrantPayment(rPObj);
					}
					
					ParticipantEventObject pEObj = new ParticipantEventObject();
					pEObj.setParticipantGroup(rSObj.getRegistrantEventId());
					ArrayList<ParticipantEventObject> pEObjAL = pEIf.getParticipantEvents(pEObj);
					for ( int i = 0; i < pEObjAL.size(); i++ ) {
						pEObj = pEObjAL.get(i);
						pEIf.deleteParticipantEvent(pEObj);
						DebugHandler.info("Deleting Participant Event " + pEObj.getParticipantEventId());
					}
					ParticipantObject pObj = new ParticipantObject();
					pObj.setParticipantGroup(rSObj.getRegistrantEventId());
					ArrayList<ParticipantObject> pObjAL = pIf.getParticipants(pObj);
					for ( int i = 0; i < pObjAL.size(); i++ ) {
						pObj = pObjAL.get(i);
						pIf.deleteParticipant(pObj);
						DebugHandler.info("Deleting Participant " + pObj.getParticipantId());
					}
					
					RegistrantEventObject rEObj = rEIf.getRegistrantEvent(rSObj.getRegistrantEventId());
					if ( rEObj != null ) {
						rEIf.deleteRegistrantEvent(rEObj);
						DebugHandler.info("Deleting Registrant Event " + rEObj.getRegistrantEventId());
					}
					
					RegistrantObject rObj = rIf.getRegistrant(rSObj.getRegistrantId());
					if ( rObj != null ) {
						rIf.deleteRegistrant(rObj);
						DebugHandler.info("Deleting Registrant " + rObj.getRegistrantId());
					}
				}
			}
		}
		return result;
	}
	
	public Integer bulkUpdateParticipants(String year) throws AppException {
		Integer result = new Integer(0);
		init(year);
		if ( pSObjAL == null ) 
			return new Integer(1);
		for (ParticipantSheetObject pSObj : pSObjAL ) {
			String dbOperation = pSObj.getParticipantDbOperation();
			if ( dbOperation != null ) {
				if ( dbOperation.equals(Constants.UPDATE_STR) ) {
					ParticipantEventInterface pEIf = new ParticipantEventImpl();
					RegistrationTypeInterface rTIf = new RegistrationTypeImpl();
					ParticipantInterface pIf = new ParticipantImpl();
					EventTypeInterface eTIf = new EventTypeImpl();
					GenderInterface gIf = new GenderImpl();
					TShirtSizeInterface tSIf = new TShirtSizeImpl();
					BloodGroupInterface bGIf = new BloodGroupImpl();
					
					RegistrationTypeObject rTObj = new RegistrationTypeObject();
					EventTypeObject eTObj = new EventTypeObject();
					GenderObject gObj = new GenderObject();
					TShirtSizeObject tSObj = new TShirtSizeObject();
					BloodGroupObject bGObj = new BloodGroupObject();
					
					ParticipantEventObject pEObj = pEIf.getParticipantEvent(pSObj.getParticipantEventId());
					ParticipantObject pObj = pIf.getParticipant(pSObj.getParticipantId());
					
					rTObj.setRegistrationTypeName(pSObj.getParticipantType());
					ArrayList<RegistrationTypeObject> rTObjAL = rTIf.getRegistrationTypes(rTObj);
					rTObj = rTObjAL.get(0);
					DebugHandler.fine(rTObj);
					pEObj.setParticipantType(rTObj.getRegistrationTypeId());
					
					eTObj.setEventTypeName(pSObj.getParticipantEventType());
					ArrayList<EventTypeObject> eTObjAL = eTIf.getEventTypes(eTObj);
					eTObj = eTObjAL.get(0);
					DebugHandler.fine(eTObj);
					pEObj.setParticipantEventType(eTObj.getEventTypeId());
					
					pEObj.setParticipantBibNo(pSObj.getParticipantBibNo());
					pEObj.setParticipantEventNetTime(pSObj.getParticipantEventNetTime());
					pEObj.setParticipantEventGunTime(pSObj.getParticipantEventGunTime());
					pObj.setParticipantFirstName(pSObj.getParticipantFirstName());
					pObj.setParticipantMiddleName(pSObj.getParticipantMiddleName());
					pObj.setParticipantLastName(pSObj.getParticipantLastName());
					
					gObj.setGenderName(pSObj.getParticipantGender());
					ArrayList<GenderObject> gObjAL = gIf.getGenders(gObj);
					gObj = gObjAL.get(0);
					DebugHandler.fine(gObj);
					
					pObj.setParticipantGender(gObj.getGenderId());
					pObj.setParticipantDateOfBirth(pSObj.getParticipantDateOfBirth());
					
					tSObj.setTShirtSizeName(pSObj.getParticipantTShirtSize());
					ArrayList<TShirtSizeObject> tSObjAL = tSIf.getTShirtSizes(tSObj);
					tSObj = tSObjAL.get(0);
					DebugHandler.fine(tSObj);
					pObj.setParticipantTShirtSize(tSObj.getTShirtSizeId());
					
					bGObj.setBloodGroupName(pSObj.getParticipantBloodGroup());
					ArrayList<BloodGroupObject> bGObjAL = bGIf.getBloodGroups(bGObj);
					bGObj = bGObjAL.get(0);
					DebugHandler.fine(bGObj);
					pObj.setParticipantBloodGroup(bGObj.getBloodGroupId());
					
					pObj.setParticipantCellPhone(pSObj.getParticipantCellPhone());
					pObj.setParticipantEmail(pSObj.getParticipantEmail());
					result = pIf.updateParticipant(pObj);
					result = pEIf.updateParticipantEvent(pEObj);
				} else if ( dbOperation.equals(Constants.DELETE_STR) ) {
					ParticipantEventInterface pEIf = new ParticipantEventImpl();
					ParticipantInterface pIf = new ParticipantImpl();
					
					ParticipantEventObject pEObj = pEIf.getParticipantEvent(pSObj.getParticipantEventId());
					
					if ( pEObj != null ) {
						pEIf.deleteParticipantEvent(pEObj);
						DebugHandler.info("Deleting Participant Event " + pEObj.getParticipantEventId());
					}
					// Now get any other event the participant has registered before deleting
					// the participants
					pEObj = new ParticipantEventObject();
					pEObj.setParticipantId(pSObj.getParticipantId());
					ArrayList<ParticipantEventObject> pEObjAL = pEIf.getParticipantEvents(pEObj);
				
					if ( pEObjAL.size() == 0 ) { // No other Event exists
						ParticipantObject pObj = pIf.getParticipant(pSObj.getParticipantId());
						if ( pObj != null ) {
							pIf.deleteParticipant(pObj);
							DebugHandler.info("Deleting Participant " + pObj.getParticipantId());
						}
					}
				}
			}
		}
		return result;
	}
	
	public Integer bulkUpdateResults(String year) throws AppException {
		Integer result = new Integer(0);
		init(year);
		if ( rObjAL == null ) 
			return new Integer(1);
		for (ResultsSheetObject rObj : rObjAL ) {
			String dbOperation = rObj.getResultsDbOperation();
			if ( dbOperation != null ) {
				if ( dbOperation.equals(Constants.UPDATE_STR) ) {
					String bibNo = rObj.getResultsBibNo();
					ParticipantEventInterface pEIf = new ParticipantEventImpl();
					ParticipantInterface pIf = new ParticipantImpl();
					EventTypeInterface eTIf = new EventTypeImpl();
					GenderInterface gIf = new GenderImpl();
					EventTypeObject eTObj = new EventTypeObject();
					GenderObject gObj = new GenderObject();
					if (bibNo != null && ! bibNo.equals("")) {
						ParticipantEventObject pEObj = new ParticipantEventObject();
						pEObj.setParticipantBibNo(rObj.getResultsBibNo());
						ArrayList<ParticipantEventObject> pEObjAL = pEIf.getParticipantEvents(pEObj);
						DebugHandler.fine(pEObjAL);
						if ( pEObjAL != null && pEObjAL.size() > 0) {
							if ( pEObjAL.size() > 1 ) {
								DebugHandler.severe("Bib No: " + bibNo + " has more than one Participant Event");
							}
							pEObj = pEObjAL.get(0);
							eTObj.setEventTypeName(rObj.getResultsEventType());
							ArrayList<EventTypeObject> eTObjAL = eTIf.getEventTypes(eTObj);
							eTObj = eTObjAL.get(0);
							DebugHandler.fine(eTObj);
							pEObj.setParticipantEventType(eTObj.getEventTypeId());
							pEObj.setParticipantEventGunTime(rObj.getResultsGunTime());
							pEObj.setParticipantEventNetTime(rObj.getResultsNetTime());
							pEObj.setParticipantEventCategoryRank(rObj.getResultsCategoryRank());
							result = pEIf.updateParticipantEvent(pEObj);
						} else {
							DebugHandler.severe("Bib No: " + bibNo + " does not have the corresponding Participant Event Entry. Must be added as spot entry in Timing System.");
						}
					} else {
						DebugHandler.severe("No Bib number entry in timing system.");
					}
				}
			}
		}
		return result;
	}
}
