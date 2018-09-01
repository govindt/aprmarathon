/*
 * MenuRest.java
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
import app.busimpl.MenuImpl;
import app.businterface.MenuInterface;
import app.busobj.MenuObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for Menu table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("menu")
public class MenuRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getMenus")
	public Response getMenus(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		MenuInterface menuIf = new MenuImpl();
		MenuObject menuObject = new MenuObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(menuObject);
		ArrayList<MenuObject> v = menuIf.getMenus(menuObject);
		int i = 0;
		while (i < v.size()) {
			menuObject = v.get(i);
			JSONObject jo = menuObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addMenu")
	public Response addMenu(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		MenuInterface menuIf = new MenuImpl();
		MenuObject menuObject = new MenuObject(jObject);
		DebugHandler.fine(menuObject);
		Integer result = menuIf.addMenu(menuObject);
		JSONObject jo = menuObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateMenu")
	public Response updateMenu(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		MenuInterface menuIf = new MenuImpl();
		MenuObject menuObject = new MenuObject(jObject);
		DebugHandler.fine(menuObject);
		Integer result = menuIf.updateMenu(menuObject);
		JSONObject jo = menuObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteMenu")
	public Response deleteMenu(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		MenuInterface menuIf = new MenuImpl();
		MenuObject menuObject = new MenuObject(jObject);
		DebugHandler.fine(menuObject);
		Integer result = menuIf.deleteMenu(menuObject);
		JSONObject jo = menuObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
