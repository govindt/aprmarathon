/*
 *  @(#)MassEntry.java	1.4 04/05/28 
 *
 *  Project Name Project
 *
 *  Author: Govind Thirumalai
 *
 */

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


import java.lang.*;
import java.util.*;
import java.text.*;
import app.busimpl.*;
import app.businterface.*;
import app.busobj.*;
import app.busobj.EventObject;


import app.util.App;

import core.util.*;
import java.net.*;

public class MassEntry {
	private static SimpleDateFormat sDateFormat;
	
	public static String getCellValue(Cell cell) {
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
    public static void main(String argv[]) throws IOException, InvalidFormatException, ParseException, AppException { 
		
		App.getInstance();
		sDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		if ( argv.length != 3 ) {
			throw new AppException("Usage MassEntry SpreadsheetFile Event_Id Registrant_Id");
		}
		int event_id = 0;
		int registrant_id = 0;
		try {
			event_id = Integer.parseInt(argv[1]);
		}
		catch (NumberFormatException nfe) {
			throw new AppException("Invalid 2nd argument " + argv[1] + ". Enter a valid Event ID");
		}
		EventInterface eIf = new EventImpl();
		EventObject eObj = eIf.getEvent(event_id);
		if ( eObj == null ) {
			throw new AppException("Invalid Event ID " + argv[1] + ". Not Present in Database.");
		}
		DebugHandler.fine("Event: " + eObj.getEventName());
	
		try {
			registrant_id = Integer.parseInt(argv[2]);
		}
		catch (NumberFormatException nfe) {
			throw new AppException("Invalid 3rd argument " + argv[2] + ". Enter a valid Registrant ID");
		}
		RegistrantInterface rIf = new RegistrantImpl();
		RegistrantObject rObj = rIf.getRegistrant(registrant_id);
		if ( eObj == null ) {
			throw new AppException("Invalid Registrant ID " + argv[2] + ". Not Present in Database.");
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
		FileInputStream fis = new FileInputStream(argv[0]); // Finds the workbook instance for XLSX file 
		Workbook wb = WorkbookFactory.create(fis);
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
		for (int i = 2; i <= rowsCount; i++) { // First Row is Comment and then Headers
			ParticipantObject pObj = new ParticipantObject();
			ParticipantEventObject pEObj = new ParticipantEventObject();
			pObj.setParticipantGroup(reObj.getRegistrantEventId());
			pEObj.setParticipantGroup(reObj.getRegistrantEventId());
			pEObj.setParticipantType(reObj.getRegistrantType());
			Row row = sheet.getRow(i);
			int colCounts = row.getLastCellNum();
			for (int j = 0; j < colCounts; j++) {
				Cell cell = row.getCell(j);
				String buf = getCellValue(cell);
				if ( j == 0 ) 
					pObj.setParticipantFirstName(buf);
				else if ( j == 1 ) {
					eTObj.setEventTypeName(buf);
					eTObj.setEvent(event_id);
					
					ArrayList<EventTypeObject> eTObjArr = eTIf.getEventTypes(eTObj);
					DebugHandler.fine(eTObjArr);
					if ( eTObjArr == null || eTObjArr.size() != 1 ) {
						throw new AppException("Zero or More than one record found for " + buf);
					}
					pEObj.setParticipantEvent(event_id);
					pEObj.setParticipantEventType((eTObjArr.get(0)).getEventTypeId());
				} else if ( j == 2) {
					gObj.setGenderName(buf);
					ArrayList<GenderObject> gObjArr = gIf.getGenders(gObj);
					pObj.setParticipantGender((gObjArr.get(0)).getGenderId());
					if ( gObjArr.size() != 1 ) {
						throw new AppException("Zero or More than one record found for " + buf);
					}
				} else if ( j == 3) {
					Date date = sDateFormat.parse(buf); 
					pObj.setParticipantDateOfBirth(date);
				} else if ( j == 4) {
					tssObj.setTShirtSizeName(buf);
					ArrayList<TShirtSizeObject> tssObjArr = tssIf.getTShirtSizes(tssObj);
					pObj.setParticipantTShirtSize((tssObjArr.get(0)).getTShirtSizeId());
					if ( tssObjArr.size() != 1 ) {
						throw new AppException("Zero or More than one record found for " + buf);
					}
				} else if ( j == 5) {
					pObj.setParticipantCellPhone(buf);
					if ( Util.trim(buf).equals("") ) 
						throw new AppException("Email cannot be empty");
				} else if ( j == 5) {
					pObj.setParticipantCellPhone(buf);
					if ( Util.trim(buf).equals("") ) 
						throw new AppException("Cell Phome is empty for " + pObj.getParticipantFirstName());
				} else if ( j == 6) {
					pObj.setParticipantEmail(buf);
					if ( Util.trim(buf).equals("") ) 
						throw new AppException("Email is empty for " + pObj.getParticipantFirstName());
				}
			}
			pObjArr.add(pObj);
			pEObjArr.add(pEObj);
		}
		DebugHandler.info(pObjArr);
		DebugHandler.info(pEObjArr);
	}
}
