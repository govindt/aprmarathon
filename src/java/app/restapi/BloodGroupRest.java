/*
 * BloodGroupRest.java
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
import app.busimpl.BloodGroupImpl;
import app.businterface.BloodGroupInterface;
import app.busobj.BloodGroupObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for BloodGroup table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("bloodgroup")
public class BloodGroupRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getBloodGroups")
	public Response getBloodGroups(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		BloodGroupInterface bloodgroupIf = new BloodGroupImpl();
		BloodGroupObject bloodgroupObject = new BloodGroupObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(bloodgroupObject);
		ArrayList<BloodGroupObject> v = bloodgroupIf.getBloodGroups(bloodgroupObject);
		int i = 0;
		while (i < v.size()) {
			bloodgroupObject = v.get(i);
			JSONObject jo = bloodgroupObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addBloodGroup")
	public Response addBloodGroup(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		BloodGroupInterface bloodgroupIf = new BloodGroupImpl();
		BloodGroupObject bloodgroupObject = new BloodGroupObject(jObject);
		DebugHandler.fine(bloodgroupObject);
		Integer result = bloodgroupIf.addBloodGroup(bloodgroupObject);
		JSONObject jo = bloodgroupObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateBloodGroup")
	public Response updateBloodGroup(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		BloodGroupInterface bloodgroupIf = new BloodGroupImpl();
		BloodGroupObject bloodgroupObject = new BloodGroupObject(jObject);
		DebugHandler.fine(bloodgroupObject);
		Integer result = bloodgroupIf.updateBloodGroup(bloodgroupObject);
		JSONObject jo = bloodgroupObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteBloodGroup")
	public Response deleteBloodGroup(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		BloodGroupInterface bloodgroupIf = new BloodGroupImpl();
		BloodGroupObject bloodgroupObject = new BloodGroupObject(jObject);
		DebugHandler.fine(bloodgroupObject);
		Integer result = bloodgroupIf.deleteBloodGroup(bloodgroupObject);
		JSONObject jo = bloodgroupObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
