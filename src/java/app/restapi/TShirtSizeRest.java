/*
 * TShirtSizeRest.java
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
import app.busimpl.TShirtSizeImpl;
import app.businterface.TShirtSizeInterface;
import app.busobj.TShirtSizeObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for TShirtSize table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("tshirtsize")
public class TShirtSizeRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getTShirtSizes")
	public Response getTShirtSizes(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		TShirtSizeInterface tshirtsizeIf = new TShirtSizeImpl();
		TShirtSizeObject tshirtsizeObject = new TShirtSizeObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(tshirtsizeObject);
		ArrayList<TShirtSizeObject> v = tshirtsizeIf.getTShirtSizes(tshirtsizeObject);
		int i = 0;
		while (i < v.size()) {
			tshirtsizeObject = v.get(i);
			JSONObject jo = tshirtsizeObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addTShirtSize")
	public Response addTShirtSize(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		TShirtSizeInterface tshirtsizeIf = new TShirtSizeImpl();
		TShirtSizeObject tshirtsizeObject = new TShirtSizeObject(jObject);
		DebugHandler.fine(tshirtsizeObject);
		Integer result = tshirtsizeIf.addTShirtSize(tshirtsizeObject);
		JSONObject jo = tshirtsizeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateTShirtSize")
	public Response updateTShirtSize(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		TShirtSizeInterface tshirtsizeIf = new TShirtSizeImpl();
		TShirtSizeObject tshirtsizeObject = new TShirtSizeObject(jObject);
		DebugHandler.fine(tshirtsizeObject);
		Integer result = tshirtsizeIf.updateTShirtSize(tshirtsizeObject);
		JSONObject jo = tshirtsizeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteTShirtSize")
	public Response deleteTShirtSize(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		TShirtSizeInterface tshirtsizeIf = new TShirtSizeImpl();
		TShirtSizeObject tshirtsizeObject = new TShirtSizeObject(jObject);
		DebugHandler.fine(tshirtsizeObject);
		Integer result = tshirtsizeIf.deleteTShirtSize(tshirtsizeObject);
		JSONObject jo = tshirtsizeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
