/*
 * SendMailRest.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.restapi;

import java.util.ArrayList;
import java.util.Enumeration;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import core.util.Util;
import core.util.DebugHandler;
import core.util.AppException;
import core.util.SendGMail;
import app.busobj.SendMailObject;
import app.util.App;
import app.util.AppConstants;
import app.util.ReceiptGenerate;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;
import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import app.businterface.SendMailInterface;
import app.busimpl.SendMailImpl;


/**
 * The implementation of the Data APIS for SendMail 
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("sendmail")
public class SendMailRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("sendMail")
	public Response sendMail(InputStream incomingData) throws JSONException, AppException, IOException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();
		DebugHandler.fine("jObject: \n" + jObject);
		SendMailObject sendMailObject = new SendMailObject(jObject);
		DebugHandler.fine(sendMailObject);
		JSONObject jo = sendMailObject.toJSON();
		SendMailInterface sMIf = new SendMailImpl();
		Integer result = sMIf.mailReceiptRegistrants(sendMailObject);
		jo.put("result", result);
		/*try {
			if ( sendMailObject.getEmailType() == SendMailObject.RECEIPT_EMAIL) {
				ReceiptGenerate r = new ReceiptGenerate();
				try {
					String pdfFile = r.createReceipt(AppConstants.RECEIPT_TEMPLATE, sendMailObject);
					File pdfFilePtr = new File(pdfFile);
					SendGMail.sendMessage(sendMailObject.getTo(), AppConstants.EMAIL_FROM, sendMailObject.getSubject(), sendMailObject.getBody(), pdfFilePtr);
				} catch (InvalidFormatException ife) {
					ife.printStackTrace();
					jo = sendMailObject.toJSON();
					jo.put("result", new Integer(1));
				}
			}
			else 
			{
				SendGMail.sendMessage(sendMailObject.getTo(), AppConstants.EMAIL_FROM, sendMailObject.getSubject(), sendMailObject.getBody(), null);
			}
			jo = sendMailObject.toJSON();
			jo.put("result", new Integer(0));
		} catch (MessagingException me) {
			me.printStackTrace();
			jo = sendMailObject.toJSON();
			jo.put("result", new Integer(1));
		} catch (GeneralSecurityException gse) {
			gse.printStackTrace();
			jo = sendMailObject.toJSON();
			jo.put("result", new Integer(1));
		}*/
		
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};
};
