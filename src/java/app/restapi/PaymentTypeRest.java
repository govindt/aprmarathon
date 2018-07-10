/*
 * PaymentTypeRest.java
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
import app.busimpl.PaymentTypeImpl;
import app.businterface.PaymentTypeInterface;
import app.busobj.PaymentTypeObject;
import app.util.App;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for PaymentType table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("paymenttype")
public class PaymentTypeRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getPaymentTypes")
	public Response getPaymentTypes(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		PaymentTypeInterface paymenttypeIf = new PaymentTypeImpl();
		PaymentTypeObject paymenttypeObject = new PaymentTypeObject(jObject);
		JSONArray joArr = new JSONArray();

		DebugHandler.fine(paymenttypeObject);
		ArrayList<PaymentTypeObject> v = paymenttypeIf.getPaymentTypes(paymenttypeObject);
		int i = 0;
		while (i < v.size()) {
			paymenttypeObject = v.get(i);
			JSONObject jo = paymenttypeObject.toJSON();
			joArr.put(jo);
			i++;
		}
		return Response.status(200).entity(joArr.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("addPaymentType")
	public Response addPaymentType(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		PaymentTypeInterface paymenttypeIf = new PaymentTypeImpl();
		PaymentTypeObject paymenttypeObject = new PaymentTypeObject(jObject);
		DebugHandler.fine(paymenttypeObject);
		Integer result = paymenttypeIf.addPaymentType(paymenttypeObject);
		JSONObject jo = paymenttypeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updatePaymentType")
	public Response updatePaymentType(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		PaymentTypeInterface paymenttypeIf = new PaymentTypeImpl();
		PaymentTypeObject paymenttypeObject = new PaymentTypeObject(jObject);
		DebugHandler.fine(paymenttypeObject);
		Integer result = paymenttypeIf.updatePaymentType(paymenttypeObject);
		JSONObject jo = paymenttypeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("deletePaymentType")
	public Response deletePaymentType(InputStream incomingData) throws AppException, JSONException {
		App.getInstance();
		JsonConverter jc = new JsonConverter(incomingData);
		JSONObject jObject = jc.getJsonObject();

		PaymentTypeInterface paymenttypeIf = new PaymentTypeImpl();
		PaymentTypeObject paymenttypeObject = new PaymentTypeObject(jObject);
		DebugHandler.fine(paymenttypeObject);
		Integer result = paymenttypeIf.deletePaymentType(paymenttypeObject);
		JSONObject jo = paymenttypeObject.toJSON();
		jo.put("result", result);
		return Response.status(200).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
	};

};
