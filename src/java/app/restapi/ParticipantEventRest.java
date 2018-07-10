/*
 * ParticipantEventRest.java
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
import app.busimpl.ParticipantEventImpl;
import app.businterface.ParticipantEventInterface;
import app.busobj.ParticipantEventObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for ParticipantEvent table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("participantevent")
public class ParticipantEventRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getParticipantEvents")
	public Response getParticipantEvents(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ParticipantEventInterface participanteventIf = new ParticipantEventImpl();
		ParticipantEventObject participanteventObject = new ParticipantEventObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(participanteventObject);
		ArrayList<ParticipantEventObject> v = participanteventIf.getParticipantEvents(participanteventObject);
		int i = 0;
		while (i < v.size()) {
			participanteventObject = v.get(i);
			JSONObject jo = participanteventObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addParticipantEvent")
	public Response addParticipantEvent(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ParticipantEventInterface participanteventIf = new ParticipantEventImpl();
		ParticipantEventObject participanteventObject = new ParticipantEventObject(jObject);
		DebugHandler.fine(participanteventObject);
		Integer result = participanteventIf.addParticipantEvent(participanteventObject);
		JSONObject jo = participanteventObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateParticipantEvent")
	public Response updateParticipantEvent(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ParticipantEventInterface participanteventIf = new ParticipantEventImpl();
		ParticipantEventObject participanteventObject = new ParticipantEventObject(jObject);
		DebugHandler.fine(participanteventObject);
		Integer result = participanteventIf.updateParticipantEvent(participanteventObject);
		JSONObject jo = participanteventObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteParticipantEvent")
	public Response deleteParticipantEvent(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ParticipantEventInterface participanteventIf = new ParticipantEventImpl();
		ParticipantEventObject participanteventObject = new ParticipantEventObject(jObject);
		DebugHandler.fine(participanteventObject);
		Integer result = participanteventIf.deleteParticipantEvent(participanteventObject);
		JSONObject jo = participanteventObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
