/*
 * ExportToSheetsRest.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.restapi;
import core.util.AppException;
import app.util.App;
import core.util.GoogleSheetWrite;
import core.util.DebugHandler;
import app.businterface.ExportToSheetsInterface;
import app.busimpl.ExportToSheetsImpl;

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
 * The implementation of the Data APIS for SendMail 
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("exporttosheets")
public class ExportToSheetsRest {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("exportToSheets")
	public Response exportToSheets(InputStream incomingData) throws JSONException, AppException {
		App theApp = App.getInstance();
		GoogleSheetWrite grs = new GoogleSheetWrite();
		DebugHandler.info(grs);
		ExportToSheetsInterface eTSIf = new ExportToSheetsImpl();
		eTSIf.updateRegistrants();
		eTSIf.updateParticipants();
		return Response.status(200).entity("Exporting to Google Sheets Succeeded.").build();
	};
	

};