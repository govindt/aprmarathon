/*
 * RegistrantPaymentRest.java
 *
 * APR Marathon Registration App Project - MANUAL EDIT
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
import app.busimpl.RegistrantPaymentImpl;
import app.businterface.RegistrantPaymentInterface;
import app.busobj.RegistrantPaymentObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for RegistrantPayment table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("registrantpayment")
public class RegistrantPaymentRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getRegistrantPayments")
	public Response getRegistrantPayments(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantPaymentInterface registrantpaymentIf = new RegistrantPaymentImpl();
		RegistrantPaymentObject registrantpaymentObject = new RegistrantPaymentObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(registrantpaymentObject);
		ArrayList<RegistrantPaymentObject> v = registrantpaymentIf.getRegistrantPayments(registrantpaymentObject);
		int i = 0;
		while (i < v.size()) {
			registrantpaymentObject = v.get(i);
			JSONObject jo = registrantpaymentObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addRegistrantPayment")
	public Response addRegistrantPayment(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantPaymentInterface registrantpaymentIf = new RegistrantPaymentImpl();
		RegistrantPaymentObject registrantpaymentObject = new RegistrantPaymentObject(jObject);
		DebugHandler.fine(registrantpaymentObject);
		RegistrantPaymentObject checkRegistrantPaymentObject = new RegistrantPaymentObject();
		checkRegistrantPaymentObject.setRegistrant(registrantpaymentObject.getRegistrant());
		checkRegistrantPaymentObject.setRegistrantEvent(registrantpaymentObject.getRegistrantEvent());
		ArrayList<RegistrantPaymentObject> existsRPObjArr = registrantpaymentIf.getRegistrantPayments(checkRegistrantPaymentObject);
		Integer result = new Integer(0);
		if (existsRPObjArr.size() == 0 ) { 
			result = registrantpaymentIf.addRegistrantPayment(registrantpaymentObject);
		} else {
			RegistrantPaymentObject foundRPObj = existsRPObjArr.get(0);
			DebugHandler.info("NOT ADDING. Found an entry already with same registrant id and event id.." + foundRPObj);
			result = new Integer(foundRPObj.getRegistrantPaymentId());
			registrantpaymentObject = foundRPObj;
		}
		JSONObject jo = registrantpaymentObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updateRegistrantPayment")
	public Response updateRegistrantPayment(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantPaymentInterface registrantpaymentIf = new RegistrantPaymentImpl();
		RegistrantPaymentObject registrantpaymentObject = new RegistrantPaymentObject(jObject);
		DebugHandler.fine(registrantpaymentObject);
		Integer result = registrantpaymentIf.updateRegistrantPayment(registrantpaymentObject);
		JSONObject jo = registrantpaymentObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deleteRegistrantPayment")
	public Response deleteRegistrantPayment(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		RegistrantPaymentInterface registrantpaymentIf = new RegistrantPaymentImpl();
		RegistrantPaymentObject registrantpaymentObject = new RegistrantPaymentObject(jObject);
		DebugHandler.fine(registrantpaymentObject);
		Integer result = registrantpaymentIf.deleteRegistrantPayment(registrantpaymentObject);
		JSONObject jo = registrantpaymentObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
