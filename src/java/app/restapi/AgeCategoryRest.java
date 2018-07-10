/*
 * AgeCategoryRest.java
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
import app.busimpl.AgeCategoryImpl;
import app.businterface.AgeCategoryInterface;
import app.busobj.AgeCategoryObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for AgeCategory table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("agecategory")
public class AgeCategoryRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getAgeCategorys")
	public Response getAgeCategorys(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		AgeCategoryInterface agecategoryIf = new AgeCategoryImpl();
		AgeCategoryObject agecategoryObject = new AgeCategoryObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(agecategoryObject);
		ArrayList<AgeCategoryObject> v = agecategoryIf.getAgeCategorys(agecategoryObject);
		int i = 0;
		while (i < v.size()) {
			agecategoryObject = v.get(i);
			JSONObject jo = agecategoryObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addAgeCategory")
	public Response addAgeCategory(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		AgeCategoryInterface agecategoryIf = new AgeCategoryImpl();
		AgeCategoryObject agecategoryObject = new AgeCategoryObject(jObject);
		DebugHandler.fine(agecategoryObject);
		Integer result = agecategoryIf.addAgeCategory(agecategoryObject);
		JSONObject jo = agecategoryObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateAgeCategory")
	public Response updateAgeCategory(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		AgeCategoryInterface agecategoryIf = new AgeCategoryImpl();
		AgeCategoryObject agecategoryObject = new AgeCategoryObject(jObject);
		DebugHandler.fine(agecategoryObject);
		Integer result = agecategoryIf.updateAgeCategory(agecategoryObject);
		JSONObject jo = agecategoryObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteAgeCategory")
	public Response deleteAgeCategory(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		AgeCategoryInterface agecategoryIf = new AgeCategoryImpl();
		AgeCategoryObject agecategoryObject = new AgeCategoryObject(jObject);
		DebugHandler.fine(agecategoryObject);
		Integer result = agecategoryIf.deleteAgeCategory(agecategoryObject);
		JSONObject jo = agecategoryObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
