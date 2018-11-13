/*
 * %W% %E%
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.IOException;
import java.io.File;
import app.busobj.SendMailObject;
import core.util.SendGMail;
import core.util.DebugHandler;
import app.util.AppConstants;
import app.util.App;
import app.util.ReceiptGenerate;
import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import app.businterface.SendMailInterface;
import app.busimpl.SendMailImpl;

public class ReceiptGenerateTest {
    public static void main(String args[]) throws core.util.AppException, IOException,InvalidFormatException {
		int emailType = SendMailObject.RECEIPT_EMAIL;
		if ( args.length > 0) {
			try { 
				emailType = Integer.parseInt(args[0]);
			} catch (NumberFormatException nfe) {
			}
		}
		App.getInstance();
		ReceiptGenerate r = new ReceiptGenerate();
		SendMailObject smObj = new SendMailObject();
		smObj.setSubject("Test Attachment");
		smObj.setBody("<b>Testing Attachment</b><br>Click <a href=\"www.aprmarathon.org\"> here.</a>");
		smObj.setTo("govindt@yahoo.com");
		smObj.setCc("govind@guks.com");
		smObj.setRegistrantId("73");
		smObj.setReceiptDate("23/08/2018");
		smObj.setReceiptYear("2018");
		smObj.setReceiptNo("73");
		smObj.setRegistrantName("Govind");
		smObj.setRegistrantLastName("Thirumalai");
		smObj.setReceiptAddress("V290 APR");
		smObj.setTransferType("Online");
		smObj.setTransferDate("23/02/2018");
		smObj.setTransferDetails("Online ID: BXWASPWD");
		smObj.setTowards("APR Charitable Trust - UST APR Marathon");
		smObj.setAmount("1000");
		smObj.setEmailType(emailType);
		String pdfFile = r.createReceipt(AppConstants.RECEIPT_TEMPLATE, smObj, false);
		File pdfFilePtr = new File(pdfFile);
		
		SendMailInterface sMIf = new SendMailImpl();
		Integer result = sMIf.mailReceiptRegistrants(smObj);
	}
}
