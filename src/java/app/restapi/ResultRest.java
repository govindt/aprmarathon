/*
 * ResultRest.java
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
import app.busimpl.ResultImpl;
import app.businterface.ResultInterface;
import app.busobj.ResultObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for Result table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("result")
public class ResultRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getResults")
	public Response getResults(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ResultInterface resultIf = new ResultImpl();
		ResultObject resultObject = new ResultObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(resultObject);
		ArrayList<ResultObject> v = resultIf.getResults(resultObject);
		int i = 0;
		while (i < v.size()) {
			resultObject = v.get(i);
			JSONObject jo = resultObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addResult")
	public Response addResult(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ResultInterface resultIf = new ResultImpl();
		ResultObject resultObject = new ResultObject(jObject);
		DebugHandler.fine(resultObject);
		Integer result = resultIf.addResult(resultObject);
		JSONObject jo = resultObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateResult")
	public Response updateResult(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ResultInterface resultIf = new ResultImpl();
		ResultObject resultObject = new ResultObject(jObject);
		DebugHandler.fine(resultObject);
		Integer result = resultIf.updateResult(resultObject);
		JSONObject jo = resultObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteResult")
	public Response deleteResult(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		ResultInterface resultIf = new ResultImpl();
		ResultObject resultObject = new ResultObject(jObject);
		DebugHandler.fine(resultObject);
		Integer result = resultIf.deleteResult(resultObject);
		JSONObject jo = resultObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
