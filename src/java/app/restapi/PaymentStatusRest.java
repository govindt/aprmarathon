/*
 * PaymentStatusRest.java
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
import app.busimpl.PaymentStatusImpl;
import app.businterface.PaymentStatusInterface;
import app.busobj.PaymentStatusObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for PaymentStatus table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("paymentstatus")
public class PaymentStatusRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getPaymentStatus")
	public Response getPaymentStatus(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		PaymentStatusInterface paymentstatusIf = new PaymentStatusImpl();
		PaymentStatusObject paymentstatusObject = new PaymentStatusObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(paymentstatusObject);
		ArrayList<PaymentStatusObject> v = paymentstatusIf.getPaymentStatus(paymentstatusObject);
		int i = 0;
		while (i < v.size()) {
			paymentstatusObject = v.get(i);
			JSONObject jo = paymentstatusObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addPaymentStatus")
	public Response addPaymentStatus(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		PaymentStatusInterface paymentstatusIf = new PaymentStatusImpl();
		PaymentStatusObject paymentstatusObject = new PaymentStatusObject(jObject);
		DebugHandler.fine(paymentstatusObject);
		Integer result = paymentstatusIf.addPaymentStatus(paymentstatusObject);
		JSONObject jo = paymentstatusObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updatePaymentStatus")
	public Response updatePaymentStatus(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		PaymentStatusInterface paymentstatusIf = new PaymentStatusImpl();
		PaymentStatusObject paymentstatusObject = new PaymentStatusObject(jObject);
		DebugHandler.fine(paymentstatusObject);
		Integer result = paymentstatusIf.updatePaymentStatus(paymentstatusObject);
		JSONObject jo = paymentstatusObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deletePaymentStatus")
	public Response deletePaymentStatus(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		PaymentStatusInterface paymentstatusIf = new PaymentStatusImpl();
		PaymentStatusObject paymentstatusObject = new PaymentStatusObject(jObject);
		DebugHandler.fine(paymentstatusObject);
		Integer result = paymentstatusIf.deletePaymentStatus(paymentstatusObject);
		JSONObject jo = paymentstatusObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
