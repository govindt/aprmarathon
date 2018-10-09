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
import core.util.SendMail;
import app.busobj.CellObject;
import app.busobj.RegistrantSheetObject;
import app.util.App;
import app.util.AppConstants;
import app.util.GoogleSheetRead;
import app.util.ReceiptGenerate;
import app.businterface.SendMailInterface;
import app.busimpl.SendMailImpl;
import app.busobj.SendMailObject;
import java.io.InputStream;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class RegistrantDataFromSheetsCLI {
	
    public static void main(String[] args) throws IOException, AppException {
		App theApp = App.getInstance();
		if ( args.length != 1 ) {
			DebugHandler.severe("Usage RegistrantDataFromSheetsCLI event_year");
		}
		GoogleSheetRead gsw = new GoogleSheetRead(args[0]);
		ArrayList<RegistrantSheetObject> rSObjAL = GoogleSheetRead.getRegistrantList();
		for (RegistrantSheetObject rSObj : rSObjAL ) {
			String paymentStatus = rSObj.getRegistrantPaymentStatusName();
			if ( paymentStatus != null ) {
				ReceiptGenerate rg = new ReceiptGenerate();
				InputStream buf = rg.getClass().getResourceAsStream(AppConstants.RECEIPT_MAIL_BODY_TEMPLATE);
				String contents = SendMail.getContents(buf);
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
					}
				}
				else if ( paymentStatus.equals("Send Email Receipt") ) {
					SendMailInterface sMIf = new SendMailImpl();
					Integer result = sMIf.mailReceiptRegistrants(smObj);
					DebugHandler.info("Result: " + result);
				}
			
			}
			
		}
		
    }
}
