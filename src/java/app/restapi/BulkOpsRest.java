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
	@Path("bulkReceiptGenerate")
	public Response bulkReceiptGenerate(InputStream incomingData) throws JSONException, AppException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();
		DebugHandler.fine("jObject: \n" + jObject);
		int event_id;
		try {
			event_id = jObject.getInt("event_id");
		} catch (JSONException je) {
			return Response.status(400).entity("event_id value not passed.").build();
		}
		EventInterface eIf = new EventImpl();
		EventObject eObj = eIf.getEvent(event_id);
		if ( eObj == null ) 
			return Response.status(400).entity("Event not found for the given event id").build();
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String year = df.format(eObj.getEventStartDate());
		DebugHandler.info("Year: " + year);
		BulkOpsInterface bOIf = new BulkOpsImpl();
		Integer result = bOIf.bulkReceiptGenerate(year);
		JSONObject jo = new JSONObject();
		jo.put("result", result);
		
		return Response.status(200).entity("Receipt Generated and Sent Mail Successfully.").build();
	};
};
