/*
 * BeneficiaryRest.java
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
import app.busimpl.BeneficiaryImpl;
import app.businterface.BeneficiaryInterface;
import app.busobj.BeneficiaryObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for Beneficiary table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("beneficiary")
public class BeneficiaryRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getBeneficiarys")
	public Response getBeneficiarys(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		BeneficiaryInterface beneficiaryIf = new BeneficiaryImpl();
		BeneficiaryObject beneficiaryObject = new BeneficiaryObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(beneficiaryObject);
		ArrayList<BeneficiaryObject> v = beneficiaryIf.getBeneficiarys(beneficiaryObject);
		int i = 0;
		while (i < v.size()) {
			beneficiaryObject = v.get(i);
			JSONObject jo = beneficiaryObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addBeneficiary")
	public Response addBeneficiary(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		BeneficiaryInterface beneficiaryIf = new BeneficiaryImpl();
		BeneficiaryObject beneficiaryObject = new BeneficiaryObject(jObject);
		DebugHandler.fine(beneficiaryObject);
		Integer result = beneficiaryIf.addBeneficiary(beneficiaryObject);
		JSONObject jo = beneficiaryObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateBeneficiary")
	public Response updateBeneficiary(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		BeneficiaryInterface beneficiaryIf = new BeneficiaryImpl();
		BeneficiaryObject beneficiaryObject = new BeneficiaryObject(jObject);
		DebugHandler.fine(beneficiaryObject);
		Integer result = beneficiaryIf.updateBeneficiary(beneficiaryObject);
		JSONObject jo = beneficiaryObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteBeneficiary")
	public Response deleteBeneficiary(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		BeneficiaryInterface beneficiaryIf = new BeneficiaryImpl();
		BeneficiaryObject beneficiaryObject = new BeneficiaryObject(jObject);
		DebugHandler.fine(beneficiaryObject);
		Integer result = beneficiaryIf.deleteBeneficiary(beneficiaryObject);
		JSONObject jo = beneficiaryObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
