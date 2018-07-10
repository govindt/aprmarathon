/*
 * MedalRest.java
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
import app.busimpl.MedalImpl;
import app.businterface.MedalInterface;
import app.busobj.MedalObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for Medal table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("medal")
public class MedalRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getMedals")
	public Response getMedals(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		MedalInterface medalIf = new MedalImpl();
		MedalObject medalObject = new MedalObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(medalObject);
		ArrayList<MedalObject> v = medalIf.getMedals(medalObject);
		int i = 0;
		while (i < v.size()) {
			medalObject = v.get(i);
			JSONObject jo = medalObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addMedal")
	public Response addMedal(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		MedalInterface medalIf = new MedalImpl();
		MedalObject medalObject = new MedalObject(jObject);
		DebugHandler.fine(medalObject);
		Integer result = medalIf.addMedal(medalObject);
		JSONObject jo = medalObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateMedal")
	public Response updateMedal(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		MedalInterface medalIf = new MedalImpl();
		MedalObject medalObject = new MedalObject(jObject);
		DebugHandler.fine(medalObject);
		Integer result = medalIf.updateMedal(medalObject);
		JSONObject jo = medalObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteMedal")
	public Response deleteMedal(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		MedalInterface medalIf = new MedalImpl();
		MedalObject medalObject = new MedalObject(jObject);
		DebugHandler.fine(medalObject);
		Integer result = medalIf.deleteMedal(medalObject);
		JSONObject jo = medalObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
