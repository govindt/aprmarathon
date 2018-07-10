package app.restapi;

import java.util.Vector;
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
import app.busimpl.RoleImpl;
import app.businterface.RoleInterface;
import app.busobj.RoleObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;


@Path("/role")
public class AprMarathonRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
     	@Path("/getRoles")
	public Response getRoles(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
                JsonConverter jc = new JsonConverter(incomingData);
                JSONObject jObject = jc.getJsonObject();
		
		RoleInterface rif = new RoleImpl();
		RoleObject rObj = new RoleObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.info(rObj);
		Vector<RoleObject> v = rif.getRoles(rObj);
		Enumeration<RoleObject> en = v.elements();
		while (en.hasMoreElements()) {
			rObj = en.nextElement();
			JSONObject jo = rObj.toJSON();
			joArr.put(jo);
		}
                return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
        }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
     	@Path("/addRole")
	public Response addRole(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
                JsonConverter jc = new JsonConverter(incomingData);
                JSONObject jObject = jc.getJsonObject();
		
		RoleInterface rif = new RoleImpl();
		RoleObject rObj = new RoleObject(jObject);
		DebugHandler.info(rObj);
		Integer result = rif.addRole(rObj);
		JSONObject jo = rObj.toJSON();
		jo.put("result", result);
                return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
        }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
     	@Path("/updateRole")
	public Response updateRole(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
                JsonConverter jc = new JsonConverter(incomingData);
                JSONObject jObject = jc.getJsonObject();
		
		RoleInterface rif = new RoleImpl();
		RoleObject rObj = new RoleObject(jObject);
		DebugHandler.info(rObj);
		Integer result = rif.updateRole(rObj);
		JSONObject jo = rObj.toJSON();
		jo.put("result", result);
                return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
        }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
     	@Path("/deleteRole")
	public Response deleteRole(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
                JsonConverter jc = new JsonConverter(incomingData);
                JSONObject jObject = jc.getJsonObject();
		
		RoleInterface rif = new RoleImpl();
		RoleObject rObj = new RoleObject(jObject);
		DebugHandler.info(rObj);
		Integer result = rif.deleteRole(rObj);
		JSONObject jo = rObj.toJSON();
		jo.put("result", result);
                return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
        }

}
