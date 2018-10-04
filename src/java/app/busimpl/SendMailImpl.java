/*
 * SendMailImpl.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.busimpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
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
import core.util.SendGMail;
import app.util.App;
import app.util.AppConstants;
import app.util.ReceiptGenerate;
import app.busobj.SendMailObject;
import app.businterface.SendMailInterface;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import javax.mail.MessagingException;
import java.security.GeneralSecurityException;

/**
 * The interface which downloads from database and updates
 * google sheets
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public class SendMailImpl implements SendMailInterface {
    
    /**
     *
     * Impl that emails Receipt Registrant Info to the underlying datasource.
     *
     * @throws AppException if the underlying operation fails
     */
    
    public Integer mailReceiptRegistrants(SendMailObject sendMailObject) throws AppException {
		if ( sendMailObject.getEmailType() == SendMailObject.RECEIPT_EMAIL) {
			ReceiptGenerate r = new ReceiptGenerate();
			try {
				String pdfFile = r.createReceipt(AppConstants.RECEIPT_TEMPLATE, sendMailObject);
				File pdfFilePtr = new File(pdfFile);
				SendGMail.sendMessage(sendMailObject.getTo(), AppConstants.EMAIL_FROM, sendMailObject.getSubject(), sendMailObject.getBody(), pdfFilePtr);
			} catch (InvalidFormatException ife) {
				throw new AppException("Invalid Format Exception during mail sending");
			} catch (IOException ioe) {
				throw new AppException("IOException during mail sending.");
			} catch (MessagingException me) {
				throw new AppException("MessagingException during mail sending.");
			} catch (GeneralSecurityException gse) {
				throw new AppException("GeneralSecurityException during mail sending.");
			}
		}
		else 
		{
			try {
				SendGMail.sendMessage(sendMailObject.getTo(), AppConstants.EMAIL_FROM, sendMailObject.getSubject(), sendMailObject.getBody(), null);
			} catch (MessagingException me) {
				throw new AppException("MessagingException during mail sending.");
			} catch (GeneralSecurityException gse) {
				throw new AppException("GeneralSecurityException during mail sending.");
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw new AppException("IOException during mail sending.");
			}
		}
		return new Integer(0);
    }
}
