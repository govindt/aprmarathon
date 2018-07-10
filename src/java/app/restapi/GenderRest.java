/*
 * GenderRest.java
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
import app.busimpl.GenderImpl;
import app.businterface.GenderInterface;
import app.busobj.GenderObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for Gender table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("gender")
public class GenderRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getGenders")
	public Response getGenders(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		GenderInterface genderIf = new GenderImpl();
		GenderObject genderObject = new GenderObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(genderObject);
		ArrayList<GenderObject> v = genderIf.getGenders(genderObject);
		int i = 0;
		while (i < v.size()) {
			genderObject = v.get(i);
			JSONObject jo = genderObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addGender")
	public Response addGender(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		GenderInterface genderIf = new GenderImpl();
		GenderObject genderObject = new GenderObject(jObject);
		DebugHandler.fine(genderObject);
		Integer result = genderIf.addGender(genderObject);
		JSONObject jo = genderObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateGender")
	public Response updateGender(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		GenderInterface genderIf = new GenderImpl();
		GenderObject genderObject = new GenderObject(jObject);
		DebugHandler.fine(genderObject);
		Integer result = genderIf.updateGender(genderObject);
		JSONObject jo = genderObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteGender")
	public Response deleteGender(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		GenderInterface genderIf = new GenderImpl();
		GenderObject genderObject = new GenderObject(jObject);
		DebugHandler.fine(genderObject);
		Integer result = genderIf.deleteGender(genderObject);
		JSONObject jo = genderObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
