package app.restapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import app.busimpl.*;
import app.businterface.*;
import app.busobj.*;
import core.util.AppException;
import java.util.Vector;
import java.util.Enumeration;


import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

@Path("/")
public class AprMarathonRest {
	@POST
	@Path("/getData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
       	
	public Response performQuery(InputStream incomingData) throws AppException {
                JsonConverter jc = new JsonConverter(incomingData);
                JSONObject jObject = jc.getJsonObject();
		
		RoleInterface rif = new RoleImpl();
		RoleObject rObj = new RoleObject();
		JSONArray joArr = new JSONArray();

		try {
			rObj.setRoleId(Integer.parseInt(jObject.getString("role_id")));
			rObj.setRoleName(jObject.getString("role_name"));
			rObj.setIsValid(jObject.getString("_is_valid"));
			Vector<RoleObject> v = rif.getRoles(rObj);
			Enumeration<RoleObject> en = v.elements();
			while (en.hasMoreElements()) {
				rObj = en.nextElement();
				JSONObject jo = new JSONObject();
				jo.put("role_id", rObj.getRoleId());
				jo.put("role_name", rObj.getRoleName());
				jo.put("is_valid", rObj.getIsValid());
				joArr.put(jo);
			}
		} catch (JSONException jsonEx) {
			throw new AppException("Error Parsing JSON Stream");
		}
                return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
        }

}
