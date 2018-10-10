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
import app.businterface.SendMailInterface;
import app.busimpl.SendMailImpl;
import app.busobj.SendMailObject;
import app.busobj.RegistrantSheetObject;
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
	
	public Integer bulkReceiptGenerate(String year) throws AppException {
		InputStream buf = null;
		String contents = "";
		ArrayList<RegistrantSheetObject> rSObjAL = new ArrayList<RegistrantSheetObject>();
		
		GoogleSheetRead gsw = new GoogleSheetRead(year);
		try {
			rSObjAL = GoogleSheetRead.getRegistrantList();
		} catch (IOException ioe) {
			throw new AppException("IO Exception getting Registrant List");
		}	
		
		for (RegistrantSheetObject rSObj : rSObjAL ) {
			String paymentStatus = rSObj.getRegistrantPaymentStatusName();
			if ( paymentStatus != null ) {
				ReceiptGenerate rg = new ReceiptGenerate();
				
				buf = rg.getClass().getResourceAsStream(AppConstants.RECEIPT_MAIL_BODY_TEMPLATE);
				contents = SendMail.getContents(buf);
					
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
}
