/*
 * RegistrantEventRest.java
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
import app.busimpl.RegistrantEventImpl;
import app.businterface.RegistrantEventInterface;
import app.busobj.RegistrantEventObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for RegistrantEvent table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("registrantevent")
public class RegistrantEventRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getRegistrantEvents")
	public Response getRegistrantEvents(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantEventInterface registranteventIf = new RegistrantEventImpl();
		RegistrantEventObject registranteventObject = new RegistrantEventObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(registranteventObject);
		ArrayList<RegistrantEventObject> v = registranteventIf.getRegistrantEvents(registranteventObject);
		int i = 0;
		while (i < v.size()) {
			registranteventObject = v.get(i);
			JSONObject jo = registranteventObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addRegistrantEvent")
	public Response addRegistrantEvent(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantEventInterface registranteventIf = new RegistrantEventImpl();
		RegistrantEventObject registranteventObject = new RegistrantEventObject(jObject);
		DebugHandler.fine(registranteventObject);
		Integer result = registranteventIf.addRegistrantEvent(registranteventObject);
		JSONObject jo = registranteventObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateRegistrantEvent")
	public Response updateRegistrantEvent(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantEventInterface registranteventIf = new RegistrantEventImpl();
		RegistrantEventObject registranteventObject = new RegistrantEventObject(jObject);
		DebugHandler.fine(registranteventObject);
		Integer result = registranteventIf.updateRegistrantEvent(registranteventObject);
		JSONObject jo = registranteventObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteRegistrantEvent")
	public Response deleteRegistrantEvent(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantEventInterface registranteventIf = new RegistrantEventImpl();
		RegistrantEventObject registranteventObject = new RegistrantEventObject(jObject);
		DebugHandler.fine(registranteventObject);
		Integer result = registranteventIf.deleteRegistrantEvent(registranteventObject);
		JSONObject jo = registranteventObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
