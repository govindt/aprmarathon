/*
 * RegistrationClassRest.java
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
import app.busimpl.RegistrationClassImpl;
import app.businterface.RegistrationClassInterface;
import app.busobj.RegistrationClassObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for RegistrationClass table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("registrationclass")
public class RegistrationClassRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getRegistrationClass")
	public Response getRegistrationClass(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationClassInterface registrationclassIf = new RegistrationClassImpl();
		RegistrationClassObject registrationclassObject = new RegistrationClassObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(registrationclassObject);
		ArrayList<RegistrationClassObject> v = registrationclassIf.getRegistrationClass(registrationclassObject);
		int i = 0;
		while (i < v.size()) {
			registrationclassObject = v.get(i);
			JSONObject jo = registrationclassObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addRegistrationClass")
	public Response addRegistrationClass(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationClassInterface registrationclassIf = new RegistrationClassImpl();
		RegistrationClassObject registrationclassObject = new RegistrationClassObject(jObject);
		DebugHandler.fine(registrationclassObject);
		Integer result = registrationclassIf.addRegistrationClass(registrationclassObject);
		JSONObject jo = registrationclassObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateRegistrationClass")
	public Response updateRegistrationClass(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationClassInterface registrationclassIf = new RegistrationClassImpl();
		RegistrationClassObject registrationclassObject = new RegistrationClassObject(jObject);
		DebugHandler.fine(registrationclassObject);
		Integer result = registrationclassIf.updateRegistrationClass(registrationclassObject);
		JSONObject jo = registrationclassObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteRegistrationClass")
	public Response deleteRegistrationClass(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrationClassInterface registrationclassIf = new RegistrationClassImpl();
		RegistrationClassObject registrationclassObject = new RegistrationClassObject(jObject);
		DebugHandler.fine(registrationclassObject);
		Integer result = registrationclassIf.deleteRegistrationClass(registrationclassObject);
		JSONObject jo = registrationclassObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
