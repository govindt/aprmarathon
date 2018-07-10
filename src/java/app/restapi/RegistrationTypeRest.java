/*
 * RegistrationTypeRest.java
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
import app.busimpl.RegistrationTypeImpl;
import app.businterface.RegistrationTypeInterface;
import app.busobj.RegistrationTypeObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for RegistrationType table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("registrationtype")
public class RegistrationTypeRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getRegistrationTypes")
	public Response getRegistrationTypes(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationTypeInterface registrationtypeIf = new RegistrationTypeImpl();
		RegistrationTypeObject registrationtypeObject = new RegistrationTypeObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(registrationtypeObject);
		ArrayList<RegistrationTypeObject> v = registrationtypeIf.getRegistrationTypes(registrationtypeObject);
		int i = 0;
		while (i < v.size()) {
			registrationtypeObject = v.get(i);
			JSONObject jo = registrationtypeObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addRegistrationType")
	public Response addRegistrationType(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationTypeInterface registrationtypeIf = new RegistrationTypeImpl();
		RegistrationTypeObject registrationtypeObject = new RegistrationTypeObject(jObject);
		DebugHandler.fine(registrationtypeObject);
		Integer result = registrationtypeIf.addRegistrationType(registrationtypeObject);
		JSONObject jo = registrationtypeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateRegistrationType")
	public Response updateRegistrationType(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationTypeInterface registrationtypeIf = new RegistrationTypeImpl();
		RegistrationTypeObject registrationtypeObject = new RegistrationTypeObject(jObject);
		DebugHandler.fine(registrationtypeObject);
		Integer result = registrationtypeIf.updateRegistrationType(registrationtypeObject);
		JSONObject jo = registrationtypeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteRegistrationType")
	public Response deleteRegistrationType(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationTypeInterface registrationtypeIf = new RegistrationTypeImpl();
		RegistrationTypeObject registrationtypeObject = new RegistrationTypeObject(jObject);
		DebugHandler.fine(registrationtypeObject);
		Integer result = registrationtypeIf.deleteRegistrationType(registrationtypeObject);
		JSONObject jo = registrationtypeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
