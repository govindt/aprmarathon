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
	@Produces(MediaType.APPLICATION_JSON)
	@Path("exportToSheets")
	public Response exportToSheets(InputStream incomingData) throws JSONException, AppException {
		App theApp = App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();
		int event_id;
		try {
			event_id = jObject.getInt("event_id");
		} catch (JSONException je) {
			return Response.status(400).entity("event_id value not passed.").build();
		}
		GoogleSheetWrite grs = new GoogleSheetWrite(event_id + "");
		DebugHandler.info(grs);
		ExportToSheetsInterface eTSIf = new ExportToSheetsImpl();
		JSONObject jo = new JSONObject();
		try {
			eTSIf.updateRegistrants();
		} catch (AppException ae) {
			return Response.status(400).entity("Failed to write ParticipantInfo Info.").build();
		}
		try {
			eTSIf.updateParticipants();
		} catch (AppException ae) {
			return Response.status(400).entity("Failed to write ParticipantInfo Info.").build();
		}
		return Response.status(200).entity("Registrants and Participant Data Updated to Sheets Successfully.").build();
	};
	

};
