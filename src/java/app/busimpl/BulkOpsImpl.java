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
import app.businterface.SendMailInterface;
import app.businterface.RegistrantPaymentInterface;
import app.businterface.PaymentTypeInterface;
import app.businterface.PaymentStatusInterface;
import app.busimpl.SendMailImpl;
import app.busimpl.RegistrantPaymentImpl;
import app.busobj.SendMailObject;
import app.busobj.RegistrantSheetObject;
import app.busobj.RegistrantPaymentObject;
import app.busobj.PaymentTypeObject;
import app.busobj.PaymentStatusObject;

import app.businterface.BulkOpsInterface;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The implementation which downloads from database and updates
 * google sheets
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public class BulkOpsImpl implements BulkOpsInterface  {
	ArrayList<RegistrantSheetObject> rSObjAL = new ArrayList<RegistrantSheetObject>();
	String contents = "";
	ReceiptGenerate rg = new ReceiptGenerate();
	
	public void init(String year) throws AppException {
		GoogleSheetRead gsw = new GoogleSheetRead(year);
		try {
			rSObjAL = GoogleSheetRead.getRegistrantList();
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
						String pdfFile = rg.createReceipt(AppConstants.RECEIPT_TEMPLATE, smObj);
						DebugHandler.info("Created Receipt File: " + pdfFile);
					} catch (InvalidFormatException ife) {
						throw new AppException("Caught exception while creating file. " + ife.getMessage());
					} catch (IOException ioe) {
						throw new AppException("IO Exception creating Receipt");
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
}
