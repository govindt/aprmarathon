/*
 * ParticipantRest.java
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
import app.busimpl.ParticipantImpl;
import app.businterface.ParticipantInterface;
import app.busobj.ParticipantObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for Participant table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("participant")
public class ParticipantRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getParticipants")
	public Response getParticipants(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ParticipantInterface participantIf = new ParticipantImpl();
		ParticipantObject participantObject = new ParticipantObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(participantObject);
		ArrayList<ParticipantObject> v = participantIf.getParticipants(participantObject);
		int i = 0;
		while (i < v.size()) {
			participantObject = v.get(i);
			JSONObject jo = participantObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addParticipant")
	public Response addParticipant(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ParticipantInterface participantIf = new ParticipantImpl();
		ParticipantObject participantObject = new ParticipantObject(jObject);
		DebugHandler.fine(participantObject);
		Integer result = participantIf.addParticipant(participantObject);
		JSONObject jo = participantObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateParticipant")
	public Response updateParticipant(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ParticipantInterface participantIf = new ParticipantImpl();
		ParticipantObject participantObject = new ParticipantObject(jObject);
		DebugHandler.fine(participantObject);
		Integer result = participantIf.updateParticipant(participantObject);
		JSONObject jo = participantObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteParticipant")
	public Response deleteParticipant(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ParticipantInterface participantIf = new ParticipantImpl();
		ParticipantObject participantObject = new ParticipantObject(jObject);
		DebugHandler.fine(participantObject);
		Integer result = participantIf.deleteParticipant(participantObject);
		JSONObject jo = participantObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
