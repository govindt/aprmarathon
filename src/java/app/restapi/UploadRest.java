/*
 * UploqeRest.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.restapi;

import java.util.ArrayList;
import java.util.Enumeration;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.Row; 
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.DataFormatter;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.io.IOException;

import java.lang.*;
import java.util.*;
import java.text.*;
import core.util.DebugHandler;
import core.util.AppException;
import core.util.Util;
import core.util.Constants;
import core.util.SendGMail;
import app.util.App;
import core.util.AppException;
import app.util.AppConstants;
import app.busimpl.*;
import app.businterface.*;
import app.busobj.*;
import app.busobj.EventObject;

import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONArray;

/**
 * The implementation of the Data APIS for Site table
 * @version 1.0
 * @author 
 * @since 1.0
 */

@Path("upload")
public class UploadRest {
	/** The path to the folder where we want to store the uploaded files */
	private static final String UPLOAD_FOLDER = System.getProperty("java.io.tmpdir");
	private static SimpleDateFormat sDateFormat;
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("uploadFile")
	public Response uploadFile(	@FormDataParam("file") InputStream uploadedInputStream,
								@FormDataParam("file") FormDataContentDisposition fileDetail,
								@FormDataParam("registrantId") String registrantId,
								@FormDataParam("eventId") String eventId)throws AppException, JSONException {
		App.getInstance();
		DebugHandler.fine("Registrant ID : " + registrantId);
		DebugHandler.fine("Event ID : " + eventId);
		int registrant_id;
		int event_id;
		try {
			registrant_id = Integer.parseInt(registrantId);
		} catch (NumberFormatException nfe) {
			return Response.status(200).entity("Invalid registrant ID in form data").build();
		}
		try {
			event_id = Integer.parseInt(eventId);
		} catch (NumberFormatException nfe) {
			return Response.status(200).entity("Invalid event ID in form data").build();
		}
		// check if all form parameters are provided
		if (uploadedInputStream == null || fileDetail == null)
			return Response.status(200).entity("Invalid form data").build();
		// create our destination folder, if it not exists
		try {
			createFolderIfNotExists(UPLOAD_FOLDER);
		} catch (SecurityException se) {
			return Response.status(200)
					.entity("Can not create destination folder on server")
					.build();
		}
		String uploadedFileLocation = UPLOAD_FOLDER + File.separator + eventId + "_" + registrantId + "_" + fileDetail.getFileName();
		String buf = "Participant Operation Failed";
		try {
			saveToFile(uploadedInputStream, uploadedFileLocation);
		} catch (IOException e) {
			return Response.status(200).entity("Can not save file").build();
		}
		try {
			buf = processParticipantExcelFile(registrant_id, event_id, uploadedFileLocation);
		} catch (AppException ae) {
			return Response.status(200).entity(ae.getMessage()).build();
		}
		
		return Response.status(200).entity(buf).build();
	}
	/**
	 * Utility method to save InputStream data to target location/file
	 * 
	 * @param inStream
	 *            - InputStream to be saved
	 * @param target
	 *            - full path to destination file
	 */
	private void saveToFile(InputStream inStream, String target)
			throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
	/**
	 * Creates a folder to desired location if it not already exists
	 * 
	 * @param dirName
	 *            - full path to the folder
	 * @throws SecurityException
	 *             - in case you don't have permission to create the folder
	 */
	private void createFolderIfNotExists(String dirName)
			throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
	
	private String getCellValue(Cell cell) {
		if (cell == null ) 
			return "";
		switch (cell.getCellTypeEnum()) {
			case BOOLEAN:
				return cell.getBooleanCellValue() + "";
			case STRING:
				return cell.getRichStringCellValue().getString();
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
					return sDateFormat.format(date);
				}
				else {
					DataFormatter formatter = new DataFormatter();
					return formatter.formatCellValue(cell);
				}
			case FORMULA:
				return cell.getCellFormula() + "";
			case BLANK:
				return "";
			default:
				return "";	
		}
	}
	
	private String processParticipantExcelFile(int registrant_id, int event_id, String uploadedFileLocation) throws AppException {
		String retVal = "";
		sDateFormat = new SimpleDateFormat("DD/MM/YYYY");
		EventInterface eIf = new EventImpl();
		EventObject eObj = eIf.getEvent(event_id);
		if ( eObj == null ) {
			throw new AppException("Invalid Event ID " + event_id + ". Not Present in Database.");
		}
		DebugHandler.fine("Event: " + eObj.getEventName());
		RegistrantInterface rIf = new RegistrantImpl();
		RegistrantObject rObj = rIf.getRegistrant(registrant_id);
		if ( eObj == null ) {
			throw new AppException("Invalid Registrant ID " + registrant_id + ". Not Present in Database.");
		}
		DebugHandler.info("Registrant Name: " + rObj.getRegistrantName());
		RegistrantEventInterface reIf = new RegistrantEventImpl();
		RegistrantEventObject reObj = new RegistrantEventObject();
		reObj.setRegistrantId(registrant_id);
		reObj.setRegistrantEvent(event_id);
		ArrayList<RegistrantEventObject> reObjArr = reIf.getRegistrantEvents(reObj);
		if ( reObjArr == null || reObjArr.size() != 1) {
			throw new AppException("No Registrant Event or Multiple events for " + rObj.getRegistrantName() + ". Not Present in Database.");
		}
		reObj = reObjArr.get(0);
		FileInputStream fis;
		try {
			fis = new FileInputStream(uploadedFileLocation); // Finds the workbook instance for XLSX file 
		} catch (FileNotFoundException fnfe) {
			throw new AppException("Unable to find file: " + uploadedFileLocation);
		}
		
		Workbook wb;
		try {
			wb = WorkbookFactory.create(fis);
		} catch (IOException ie) {
			throw new AppException("Unable to open file: " + uploadedFileLocation);
		} catch (InvalidFormatException ife) {
			throw new AppException("Invalid format file " + uploadedFileLocation + ". Provide Valid XLSX file.");
		}
		Sheet sheet = wb.getSheetAt(0);
		Header header = sheet.getHeader();

		int rowsCount = sheet.getLastRowNum();
		DebugHandler.fine("Total Number of Rows: " + (rowsCount + 1));
		ArrayList<ParticipantObject> pObjArr = new ArrayList<ParticipantObject>();
		ArrayList<ParticipantEventObject> pEObjArr = new ArrayList<ParticipantEventObject>();
		
		EventTypeObject eTObj = new EventTypeObject();
		GenderObject gObj = new GenderObject();
		TShirtSizeObject tssObj = new TShirtSizeObject();
		ParticipantInterface pIf = new ParticipantImpl();
		ParticipantEventInterface pEIf = new ParticipantEventImpl();
		EventTypeInterface eTIf = new EventTypeImpl();
		GenderInterface gIf = new GenderImpl();
		TShirtSizeInterface tssIf = new TShirtSizeImpl();
		DebugHandler.info("Rows Count : " + rowsCount);
		for (int i = 2; i <= rowsCount; i++) { // First Row is Comment and then Headers
			ParticipantObject pObj = new ParticipantObject();
			ParticipantEventObject pEObj = new ParticipantEventObject();
			pObj.setParticipantGroup(reObj.getRegistrantEventId());
			pEObj.setParticipantGroup(reObj.getRegistrantEventId());
			pEObj.setParticipantType(reObj.getRegistrantType());
			Row row = sheet.getRow(i);
			if ( row == null )
				continue;
			String buf = "";
			int colCounts = row.getLastCellNum();
			for (int j = 0; j < colCounts; j++) {
				Cell cell = row.getCell(j);
				buf = getCellValue(cell);
				if ( ! buf.equals("") ) {
					if ( j == 0 ) {
						pObj.setParticipantFirstName(buf);
					}
					else if ( j == 1 ) {
						eTObj.setEventTypeName(buf);
						eTObj.setEvent(event_id);
						
						ArrayList<EventTypeObject> eTObjArr = eTIf.getEventTypes(eTObj);
						DebugHandler.fine(eTObjArr);
						if ( eTObjArr == null || eTObjArr.size() != 1 ) {
							throw new AppException("Zero or More than one record found for " + buf + " Row: " + i + "Column: " + (j + 1));
						}
						pEObj.setParticipantEvent(event_id);
						pEObj.setParticipantEventType((eTObjArr.get(0)).getEventTypeId());
					} else if ( j == 2) {
						gObj.setGenderName(buf);
						ArrayList<GenderObject> gObjArr = gIf.getGenders(gObj);
						pObj.setParticipantGender((gObjArr.get(0)).getGenderId());
						if ( gObjArr.size() != 1 ) {
							throw new AppException("Zero or More than one record found for " + buf + " Row: " + i + "Column: " + (j + 1));
						}
					} else if ( j == 3) {
						try {
							Date date = sDateFormat.parse(buf); 
							DebugHandler.info("DOB: " + buf + " Date: " + date);
							pObj.setParticipantDateOfBirth(date);
						} catch (ParseException pe) {
							throw new AppException("Wrong Date provide " + buf + " Row: " + i + "Column: " + (j + 1));
						}
					} else if ( j == 4) {
						tssObj.setTShirtSizeName(buf);
						ArrayList<TShirtSizeObject> tssObjArr = tssIf.getTShirtSizes(tssObj);
						pObj.setParticipantTShirtSize((tssObjArr.get(0)).getTShirtSizeId());
						if ( tssObjArr.size() != 1 ) {
							throw new AppException("Zero or More than one record found for " + buf + " Row: " + i + "Column: " + (j + 1));
						}
					} else if ( j == 5) {
						pObj.setParticipantCellPhone(buf);
						if ( Util.trim(buf).equals("") ) 
							throw new AppException("Cell Phone is empty for " + pObj.getParticipantFirstName()  + " Row: " + i + "Column: " + (j + 1));
					} else if ( j == 6) {
						pObj.setParticipantEmail(buf);
						if ( Util.trim(buf).equals("") ) 
							throw new AppException("Email is empty for " + pObj.getParticipantFirstName()  + " Row: " + i + "Column: " + (j + 1));
					}
				} else
					continue;
			}
			if ( ! buf.equals("") ) {
				pObjArr.add(pObj);
				pEObjArr.add(pEObj);
			}
		}
		DebugHandler.fine(pObjArr);
		DebugHandler.fine(pEObjArr);
		// Check if the participant already existed
		ArrayList<String> toAddParticipants = new ArrayList<String>();
		ArrayList<String> notToAddParticipants = new ArrayList<String>();
		ArrayList<String> toAddParticipantEvents = new ArrayList<String>();
		ArrayList<String> notToAddParticipantEvents = new ArrayList<String>();
		for ( int i = 0; i < pObjArr.size(); i++ ) {
			ParticipantObject pObj = pObjArr.get(i);
			ParticipantEventObject pEObj = pEObjArr.get(i);
			ParticipantObject checkPObj = new ParticipantObject();
			// Find if a record exists with the same gender, dob and cell
			checkPObj.setParticipantFirstName(pObj.getParticipantFirstName());
			checkPObj.setParticipantGender(pObj.getParticipantGender());
			checkPObj.setParticipantDateOfBirth(pObj.getParticipantDateOfBirth());
			checkPObj.setParticipantCellPhone(pObj.getParticipantCellPhone());
			ArrayList<ParticipantObject> existsPObjArr = pIf.getParticipants(checkPObj);
			if (existsPObjArr.size() == 0 ) {
				// Participants to Add
				toAddParticipants.add(pObj.getParticipantFirstName());
				toAddParticipantEvents.add(pObj.getParticipantFirstName());
				Integer participantResult = pIf.addParticipant(pObj);
				pEObj.setParticipantId(participantResult.intValue());
				Integer participantEventResult = pEIf.addParticipantEvent(pEObj);
			} else { // Participant Exists - Checking to see if additional event types needs to be added
				// If we already have him present add for a different event. Else error.
				notToAddParticipants.add(pObj.getParticipantFirstName());
				for ( int j = 0; j < existsPObjArr.size(); j++) {
					ParticipantObject checkPObjForEvent = existsPObjArr.get(j);
					
					ParticipantEventObject checkPEObj = new ParticipantEventObject();
					checkPEObj.setParticipantId(checkPObjForEvent.getParticipantId());
					checkPEObj.setParticipantEvent(event_id);
					checkPEObj.setParticipantEventType(pEObj.getParticipantEventType());
					ArrayList<ParticipantEventObject> existsPEObjArr = pEIf.getParticipantEvents(checkPEObj);
					if ( existsPEObjArr.size() == 0 ) {
						eTObj = eTIf.getEventType(pEObj.getParticipantEventType());
						if ( eTObj != null && eTObj.getEventTypeName() != null )
							toAddParticipantEvents.add(pObj.getParticipantFirstName() + ":" + eTObj.getEventTypeName());
						else
							toAddParticipantEvents.add(pObj.getParticipantFirstName());
						pEObj.setParticipantId(checkPObjForEvent.getParticipantId());
						Integer participantEventResult = pEIf.addParticipantEvent(pEObj);
					}
					else {
						eTObj = eTIf.getEventType(pEObj.getParticipantEventType());
						if ( eTObj != null && eTObj.getEventTypeName() != null )
							notToAddParticipantEvents.add(pObj.getParticipantFirstName() + ":" + eTObj.getEventTypeName());
						else
							notToAddParticipantEvents.add(pObj.getParticipantFirstName());
					}
				}		
			}
		}
		try {
			fis.close();
		} catch (IOException ioe) {
			throw new AppException("Unable to close " + uploadedFileLocation);
		}
		DebugHandler.fine("Added Participants List: " + toAddParticipants);
		DebugHandler.fine("Not Added Participants List: " + notToAddParticipants);
		DebugHandler.fine("Added Participant Events List: " + toAddParticipantEvents);
		DebugHandler.fine("Not Added Participant Events List: " + notToAddParticipantEvents);
		
		if ( toAddParticipants.size() != 0 )
			retVal += "Added Participants " + toAddParticipants + "\n";
		if ( notToAddParticipants.size() != 0 )
			retVal += "Not Added Participants " + notToAddParticipants  + "\n";
		if ( toAddParticipantEvents.size() != 0 )
			retVal += "Added Participant Events " + toAddParticipantEvents + "\n";
		if ( notToAddParticipantEvents.size() != 0 )
			retVal += "Not Added Participant Events " + notToAddParticipantEvents + "\n";
		
		try {
			SendGMail.sendMessage(rObj.getRegistrantEmail(), 
								rObj.getRegistrantAdditionalEmail(),
								null,
								AppConstants.EMAIL_FROM, 
								"Participant List Updated for " + eObj.getEventName(), 
								"Participants Added that were sent via Mass Entry Spreadsheet", null);
		} catch (MessagingException me) {
			DebugHandler.severe("Messaging Exception Sending to " + rObj.getRegistrantEmail());
			me.printStackTrace();
		} catch (GeneralSecurityException gse) {
			DebugHandler.severe("General Security Exception Sending to " + rObj.getRegistrantEmail());
			gse.printStackTrace();
		} catch (IOException ioe) {
			DebugHandler.severe("General Security Exception Sending to " + rObj.getRegistrantEmail());
			ioe.printStackTrace();
		}
		
		return retVal;
	}
};
