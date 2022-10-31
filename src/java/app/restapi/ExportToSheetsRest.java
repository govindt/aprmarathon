/*
 * ExportToSheetsRest.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.restapi;
import core.util.AppException;
import app.util.App;
import app.util.GoogleSheetWrite;
import core.util.DebugHandler;
import app.businterface.ExportToSheetsInterface;
import app.busimpl.ExportToSheetsImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;

import java.io.InputStream;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for SendMail 
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("exporttosheets")
public class ExportToSheetsRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("exportToSheets")
	public Response exportToSheets(InputStream incomingData) throws JSONException, AppException {
		App theApp = App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();
		JSONObject jo = new JSONObject();
		int event_id;
		try {
			event_id = jObject.getInt("event_id");
		} catch (JSONException je) {
			DebugHandler.severe("event_id value not passed.");
			jo.put("result", new Integer(1));
			return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
		}
		GoogleSheetWrite grs = new GoogleSheetWrite(event_id + "");
		DebugHandler.info(grs);
		ExportToSheetsInterface eTSIf = new ExportToSheetsImpl();
		
		try {
			eTSIf.updateRegistrants();
		} catch (AppException ae) {
			DebugHandler.severe("Failed to write Registrant Info");
			ae.printStackTrace();
			jo.put("result", new Integer(1));
			return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
		}
		try {
			eTSIf.updateParticipants();
		} catch (AppException ae) {
			DebugHandler.severe("Failed to write ParticipantInfo Info");
			jo.put("result", new Integer(2));
			return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
		}
		
		jo.put("result", new Integer(0));
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};
	

};
