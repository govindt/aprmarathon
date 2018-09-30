/*
 * AuthenticationRest.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.restapi;

import core.util.AppException;
import app.util.App;
import core.util.DebugHandler;
import app.busimpl.UsersImpl;
import app.businterface.UsersInterface;
import app.busobj.UsersObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;

import java.io.InputStream;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for Authentication Rest 
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("auth")
public class AuthenticationRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("authenticate")
	public Response authenticate(InputStream incomingData) throws JSONException, AppException {
		App theApp = App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();
		String username, password;
		boolean authenticationStatus = false;
		
		try {
			username = jObject.getString("username");
		} catch (JSONException je) {
			throw new AppException("Username value not passed.");
		}
		try {
			password = jObject.getString("password");
		} catch (JSONException je) {
			throw new AppException("Password value not passed.");
		}
		try {	
			App.getInstance();
			UsersInterface uif = new UsersImpl();
			UsersObject usersObj = uif.authenticate(username, password);
			authenticationStatus = (usersObj != null);	
		} catch (AppException ae) {
			authenticationStatus = false;
			ae.printStackTrace();
			throw new AppException("Wrong username password passed");
		}
		JSONObject jo = new JSONObject();
		jo.put("authenticationStatus", authenticationStatus);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};
	

};
