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
import app.businterface.PaymentStatusInterface;
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
import app.busobj.RegistrantPaymentObject;
import app.busobj.PaymentTypeObject;
import app.busobj.PaymentStatusObject;
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
	String contents = "";
	ReceiptGenerate rg = new ReceiptGenerate();
	
	public void init(String year) throws AppException {
		GoogleSheetRead gsw = new GoogleSheetRead(year);
		try {
			rSObjAL = GoogleSheetRead.getRegistrantList();
			pSObjAL = GoogleSheetRead.getParticipantList();
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
						DebugHandler.info("Created Receipt File: " + docFile);
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
					DebugHandler.info("Result: " + result);
				}
			
			}
		}
		return new Integer(0);
	}
	
	public Integer bulkUpdateRegistrants(String year) throws AppException {
		Integer result = new Integer(0);
		init(year);
		if ( rSObjAL == null ) 
			return new Integer(1);
		for (RegistrantSheetObject rSObj : rSObjAL ) {
			String dbOperation = rSObj.getRegistrantDbOperation();
			if ( dbOperation != null ) {
				if ( dbOperation.equals(Constants.UPDATE_STR) ) {
					RegistrantPaymentInterface rPIf = new RegistrantPaymentImpl();
					RegistrantPaymentObject rPObj = rPIf.getRegistrantPayment(rSObj.getRegistrantPaymentId());
					PaymentTypeInterface pTIf = new PaymentTypeImpl();
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
					result = rPIf.updateRegistrantPayment(rPObj);
					DebugHandler.fine("Result: " + result);
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
				}
			}
		}
		return result;
	}
}
