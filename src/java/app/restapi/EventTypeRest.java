/*
 * EventTypeRest.java
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
import app.busimpl.EventTypeImpl;
import app.businterface.EventTypeInterface;
import app.busobj.EventTypeObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for EventType table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("eventtype")
public class EventTypeRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getEventTypes")
	public Response getEventTypes(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		EventTypeInterface eventtypeIf = new EventTypeImpl();
		EventTypeObject eventtypeObject = new EventTypeObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(eventtypeObject);
		ArrayList<EventTypeObject> v = eventtypeIf.getEventTypes(eventtypeObject);
		int i = 0;
		while (i < v.size()) {
			eventtypeObject = v.get(i);
			JSONObject jo = eventtypeObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addEventType")
	public Response addEventType(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		EventTypeInterface eventtypeIf = new EventTypeImpl();
		EventTypeObject eventtypeObject = new EventTypeObject(jObject);
		DebugHandler.fine(eventtypeObject);
		Integer result = eventtypeIf.addEventType(eventtypeObject);
		JSONObject jo = eventtypeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateEventType")
	public Response updateEventType(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		EventTypeInterface eventtypeIf = new EventTypeImpl();
		EventTypeObject eventtypeObject = new EventTypeObject(jObject);
		DebugHandler.fine(eventtypeObject);
		Integer result = eventtypeIf.updateEventType(eventtypeObject);
		JSONObject jo = eventtypeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteEventType")
	public Response deleteEventType(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		EventTypeInterface eventtypeIf = new EventTypeImpl();
		EventTypeObject eventtypeObject = new EventTypeObject(jObject);
		DebugHandler.fine(eventtypeObject);
		Integer result = eventtypeIf.deleteEventType(eventtypeObject);
		JSONObject jo = eventtypeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
