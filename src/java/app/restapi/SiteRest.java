/*
 * SiteRest.java
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
import app.busimpl.SiteImpl;
import app.businterface.SiteInterface;
import app.busobj.SiteObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for Site table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("site")
public class SiteRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getSites")
	public Response getSites(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		SiteInterface siteIf = new SiteImpl();
		SiteObject siteObject = new SiteObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(siteObject);
		ArrayList<SiteObject> v = siteIf.getSites(siteObject);
		int i = 0;
		while (i < v.size()) {
			siteObject = v.get(i);
			JSONObject jo = siteObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addSite")
	public Response addSite(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		SiteInterface siteIf = new SiteImpl();
		SiteObject siteObject = new SiteObject(jObject);
		DebugHandler.fine(siteObject);
		Integer result = siteIf.addSite(siteObject);
		JSONObject jo = siteObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateSite")
	public Response updateSite(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		SiteInterface siteIf = new SiteImpl();
		SiteObject siteObject = new SiteObject(jObject);
		DebugHandler.fine(siteObject);
		Integer result = siteIf.updateSite(siteObject);
		JSONObject jo = siteObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteSite")
	public Response deleteSite(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		SiteInterface siteIf = new SiteImpl();
		SiteObject siteObject = new SiteObject(jObject);
		DebugHandler.fine(siteObject);
		Integer result = siteIf.deleteSite(siteObject);
		JSONObject jo = siteObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
