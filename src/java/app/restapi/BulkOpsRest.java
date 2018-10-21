/*
 * BulkOpsRest.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.restapi;

import core.util.DebugHandler;
import core.util.AppException;
import java.text.SimpleDateFormat;
import app.busobj.EventObject;
import app.businterface.EventInterface;
import app.busimpl.EventImpl;
import app.businterface.BulkOpsInterface;
import app.busimpl.BulkOpsImpl;
import app.util.App;

import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * The implementation of the Data APIS for BulkOps 
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("bulkops")
public class BulkOpsRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("bulkReceiptGenerate")
	public Response bulkReceiptGenerate(InputStream incomingData) throws JSONException, AppException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();
		DebugHandler.fine("jObject: \n" + jObject);
		JSONObject jo = new JSONObject();
		int event_id;
		try {
			event_id = jObject.getInt("event_id");
		} catch (JSONException je) {
			DebugHandler.severe("event_id value not passed.");
			jo.put("result", new Integer(1));
			return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
		}
		EventInterface eIf = new EventImpl();
		EventObject eObj = eIf.getEvent(event_id);
		if ( eObj == null ) {
			DebugHandler.severe("Event not found for the given event id");
			jo.put("result", new Integer(2));
			return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String year = df.format(eObj.getEventStartDate());
		DebugHandler.fine("Year: " + year);
		BulkOpsInterface bOIf = new BulkOpsImpl();
		Integer result = new Integer(0);
		try {
			result = bOIf.bulkReceiptGenerate(year);
		} catch (AppException ae) {
			DebugHandler.severe(ae.getMessage());
			result = 1;
		}
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("bulkUpdateRegistrants")
	public Response bulkUpdateRegistrants(InputStream incomingData) throws JSONException, AppException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();
		DebugHandler.fine("jObject: \n" + jObject);
		JSONObject jo = new JSONObject();
		int event_id;
		try {
			event_id = jObject.getInt("event_id");
		} catch (JSONException je) {
			DebugHandler.severe("event_id value not passed.");
			jo.put("result", new Integer(1));
			return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
		}
		EventInterface eIf = new EventImpl();
		EventObject eObj = eIf.getEvent(event_id);
		if ( eObj == null ) {
			DebugHandler.severe("Event not found for the given event id");
			jo.put("result", new Integer(2));
			return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String year = df.format(eObj.getEventStartDate());
		DebugHandler.fine("Year: " + year);
		BulkOpsInterface bOIf = new BulkOpsImpl();
		Integer result = new Integer(0);
		try {
			bOIf.bulkUpdateRegistrants(year);
		} catch (AppException ae) {
			DebugHandler.severe(ae.getMessage());
			result = 1;
		}
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("bulkUpdateParticipants")
	public Response bulkUpdateParticipants(InputStream incomingData) throws JSONException, AppException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();
		DebugHandler.fine("jObject: \n" + jObject);
		JSONObject jo = new JSONObject();
		int event_id;
		try {
			event_id = jObject.getInt("event_id");
		} catch (JSONException je) {
			DebugHandler.severe("event_id value not passed.");
			jo.put("result", new Integer(1));
			return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
		}
		EventInterface eIf = new EventImpl();
		EventObject eObj = eIf.getEvent(event_id);
		if ( eObj == null ) {
			DebugHandler.severe("Event not found for the given event id");
			jo.put("result", new Integer(2));
			return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String year = df.format(eObj.getEventStartDate());
		DebugHandler.fine("Year: " + year);
		BulkOpsInterface bOIf = new BulkOpsImpl();
		Integer result = new Integer(0);
		try {
			bOIf.bulkUpdateParticipants(year);
		} catch (AppException ae) {
			DebugHandler.severe(ae.getMessage());
			result = 1;
		}
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};
};
