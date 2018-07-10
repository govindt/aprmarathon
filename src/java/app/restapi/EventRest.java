/*
 * EventRest.java
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
import app.busimpl.EventImpl;
import app.businterface.EventInterface;
import app.busobj.EventObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for Event table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("event")
public class EventRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getEvents")
	public Response getEvents(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		EventInterface eventIf = new EventImpl();
		EventObject eventObject = new EventObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(eventObject);
		ArrayList<EventObject> v = eventIf.getEvents(eventObject);
		int i = 0;
		while (i < v.size()) {
			eventObject = v.get(i);
			JSONObject jo = eventObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addEvent")
	public Response addEvent(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		EventInterface eventIf = new EventImpl();
		EventObject eventObject = new EventObject(jObject);
		DebugHandler.fine(eventObject);
		Integer result = eventIf.addEvent(eventObject);
		JSONObject jo = eventObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateEvent")
	public Response updateEvent(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		EventInterface eventIf = new EventImpl();
		EventObject eventObject = new EventObject(jObject);
		DebugHandler.fine(eventObject);
		Integer result = eventIf.updateEvent(eventObject);
		JSONObject jo = eventObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteEvent")
	public Response deleteEvent(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		EventInterface eventIf = new EventImpl();
		EventObject eventObject = new EventObject(jObject);
		DebugHandler.fine(eventObject);
		Integer result = eventIf.deleteEvent(eventObject);
		JSONObject jo = eventObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
