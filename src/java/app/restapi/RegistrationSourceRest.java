/*
 * RegistrationSourceRest.java
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
import app.busimpl.RegistrationSourceImpl;
import app.businterface.RegistrationSourceInterface;
import app.busobj.RegistrationSourceObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for RegistrationSource table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("registrationsource")
public class RegistrationSourceRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getRegistrationSources")
	public Response getRegistrationSources(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationSourceInterface registrationsourceIf = new RegistrationSourceImpl();
		RegistrationSourceObject registrationsourceObject = new RegistrationSourceObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(registrationsourceObject);
		ArrayList<RegistrationSourceObject> v = registrationsourceIf.getRegistrationSources(registrationsourceObject);
		int i = 0;
		while (i < v.size()) {
			registrationsourceObject = v.get(i);
			JSONObject jo = registrationsourceObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addRegistrationSource")
	public Response addRegistrationSource(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationSourceInterface registrationsourceIf = new RegistrationSourceImpl();
		RegistrationSourceObject registrationsourceObject = new RegistrationSourceObject(jObject);
		DebugHandler.fine(registrationsourceObject);
		Integer result = registrationsourceIf.addRegistrationSource(registrationsourceObject);
		JSONObject jo = registrationsourceObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateRegistrationSource")
	public Response updateRegistrationSource(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationSourceInterface registrationsourceIf = new RegistrationSourceImpl();
		RegistrationSourceObject registrationsourceObject = new RegistrationSourceObject(jObject);
		DebugHandler.fine(registrationsourceObject);
		Integer result = registrationsourceIf.updateRegistrationSource(registrationsourceObject);
		JSONObject jo = registrationsourceObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteRegistrationSource")
	public Response deleteRegistrationSource(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationSourceInterface registrationsourceIf = new RegistrationSourceImpl();
		RegistrationSourceObject registrationsourceObject = new RegistrationSourceObject(jObject);
		DebugHandler.fine(registrationsourceObject);
		Integer result = registrationsourceIf.deleteRegistrationSource(registrationsourceObject);
		JSONObject jo = registrationsourceObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
