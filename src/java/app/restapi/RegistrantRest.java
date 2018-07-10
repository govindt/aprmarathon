/*
 * RegistrantRest.java
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
import app.busimpl.RegistrantImpl;
import app.businterface.RegistrantInterface;
import app.busobj.RegistrantObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for Registrant table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("registrant")
public class RegistrantRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getRegistrants")
	public Response getRegistrants(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantInterface registrantIf = new RegistrantImpl();
		RegistrantObject registrantObject = new RegistrantObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(registrantObject);
		ArrayList<RegistrantObject> v = registrantIf.getRegistrants(registrantObject);
		int i = 0;
		while (i < v.size()) {
			registrantObject = v.get(i);
			JSONObject jo = registrantObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addRegistrant")
	public Response addRegistrant(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantInterface registrantIf = new RegistrantImpl();
		RegistrantObject registrantObject = new RegistrantObject(jObject);
		DebugHandler.fine(registrantObject);
		Integer result = registrantIf.addRegistrant(registrantObject);
		JSONObject jo = registrantObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateRegistrant")
	public Response updateRegistrant(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantInterface registrantIf = new RegistrantImpl();
		RegistrantObject registrantObject = new RegistrantObject(jObject);
		DebugHandler.fine(registrantObject);
		Integer result = registrantIf.updateRegistrant(registrantObject);
		JSONObject jo = registrantObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteRegistrant")
	public Response deleteRegistrant(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantInterface registrantIf = new RegistrantImpl();
		RegistrantObject registrantObject = new RegistrantObject(jObject);
		DebugHandler.fine(registrantObject);
		Integer result = registrantIf.deleteRegistrant(registrantObject);
		JSONObject jo = registrantObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
