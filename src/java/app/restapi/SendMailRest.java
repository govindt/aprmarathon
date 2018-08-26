/*
 * TShirtSizeRest.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.restapi;

import java.util.ArrayList;
import java.util.Enumeration;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import core.util.Util;
import core.util.DebugHandler;
import core.util.AppException;
import core.util.SendMail;
import core.busobj.SendMailObject;
import app.util.App;
import app.util.AppConstants;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;
import javax.mail.MessagingException;

/**
 * The implementation of the Data APIS for TShirtSize table
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
	public Response sendMail(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		SendMailObject sendMailObject = new SendMailObject(jObject);
		DebugHandler.fine(sendMailObject);
		String[] to = new String[1];
		to[0] = "govind@guks.com";
		SendMail.SMTP_HOST = AppConstants.SMTP_HOST;
		SendMail.gmail_username = "aprct.treasurer@gmail.com";
		SendMail.gmail_password = "APRVillasTowers";
		SendMail.debug = "true";
		try {
			SendMail.postMail(to, "Test", "Testing", AppConstants.EMAIL_FROM, null);
		}catch (MessagingException me) {
			DebugHandler.severe("Unable to send message to " + to[0]);
			me.printStackTrace();
		}
		JSONObject jo = sendMailObject.toJSON();
		jo.put("result", new Integer(0));
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};
};
