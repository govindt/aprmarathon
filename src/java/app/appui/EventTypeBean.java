/*
 *
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */
package app.appui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.http.*;
import core.ui.*;
import core.appui.*;
import core.util.*;
import app.util.*;
import app.busobj.*;
import app.businterface.*;
import app.busimpl.*;

import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.File;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;

public class EventTypeBean implements SpreadSheetInterface {
    public int eventTypeId = 0;
    public int event = 0;
    EventTypeObject selectedEventTypeObj = new EventTypeObject();
    EventTypeInterface eventTypeIf = new EventTypeImpl();

    public EventTypeBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	try {
	    eventTypeId = Integer.parseInt(valuepairs.get(AppConstants.EVENT_TYPE_ID_STR));
	} catch (NumberFormatException nfe) {
	    eventTypeId = 0;
	}
	try {
	    event = Integer.parseInt(valuepairs.get(AppConstants.EVENT_STR));
	} catch (NumberFormatException nfe) {
	    event = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null ||
	     Boolean.valueOf(saveProfile).booleanValue() == false ) {
	    // This is to display the page
	    if ( eventTypeId != 0 ) // Display the selected eventtype
		selectedEventTypeObj = eventTypeIf.getEventType(eventTypeId);
	}
	else {
	    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
	    if ( inputFileName == null ) {
		if ( eventTypeId != 0 ) {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_TYPE_NAME_STR));
		    selectedEventTypeObj.setEventTypeName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_STR));
		    selectedEventTypeObj.setEvent(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_TYPE_DESCRIPTION_STR));
		    selectedEventTypeObj.setEventTypeDescription(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_TYPE_START_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    selectedEventTypeObj.setEventTypeStartDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_TYPE_END_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    selectedEventTypeObj.setEventTypeEndDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_TYPE_VENUE_STR));
		    selectedEventTypeObj.setEventTypeVenue(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.ONLINE_REGISTRATION_ONLY_STR));
		    selectedEventTypeObj.setOnlineRegistrationOnly(buf);
		    DebugHandler.debug("Modifying EventType Object " + selectedEventTypeObj);
		    eventTypeIf.updateEventType(selectedEventTypeObj);
		}
		else {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    EventTypeObject eventTypeObj = new EventTypeObject();
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_TYPE_NAME_STR));
		    eventTypeObj.setEventTypeName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_STR));
		    eventTypeObj.setEvent(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_TYPE_DESCRIPTION_STR));
		    eventTypeObj.setEventTypeDescription(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_TYPE_START_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    eventTypeObj.setEventTypeStartDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_TYPE_END_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    eventTypeObj.setEventTypeEndDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_TYPE_VENUE_STR));
		    eventTypeObj.setEventTypeVenue(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.ONLINE_REGISTRATION_ONLY_STR));
		    eventTypeObj.setOnlineRegistrationOnly(buf);
		    DebugHandler.debug("Adding EventType Object " + eventTypeObj);
		    eventTypeIf.addEventType(eventTypeObj);
		}
	    }
	    else {
		String temp = System.getProperty("java.io.tmpdir");
		inputFileName = temp + File.separatorChar + inputFileName;
		readFromFile(inputFileName, null);
	    }
	}
    }

    public String getEventTypeInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	SelectElement se = null;
	InputElement ie = null;
	TextareaElement txt = null;
	EventTypeObject eventTypeObj = eventTypeIf.getEventType(eventTypeId);

	if ( eventTypeObj == null )
	    eventTypeObj = new EventTypeObject();
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.CURRENT_EVENTTYPE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	ArrayList<String> nameArrayList = new ArrayList<String>();
	ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	EventTypeObject[] eventTypeArr = eventTypeIf.getAllEventTypes();
	nameArrayList.add(AppConstants.NEW_EVENTTYPE);
	valueArrayList.add(new Integer(0));
	for (int iterator = 0; iterator < eventTypeArr.length; iterator++) {
		EventTypeObject eventTypeObject = eventTypeArr[iterator];
		if ( eventTypeObject == null )
			break;
		nameArrayList.add(eventTypeObject.getEventTypeName());
		valueArrayList.add(new Integer(eventTypeObject.getEventTypeId()));
	}
	se = new SelectElement(AppConstants.EVENT_TYPE_ID_STR, nameArrayList, valueArrayList, String.valueOf(eventTypeId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_TYPE_NAME_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventTypeId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_TYPE_NAME_STR, selectedEventTypeObj.getEventTypeName()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_TYPE_NAME_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	EventInterface eventIf = new EventImpl();
	EventObject[] eventRefArr = eventIf.getAllEvents();
	for (int iterator = 0; iterator < eventRefArr.length; iterator++) {
	    EventObject eventObject = eventRefArr[iterator];
	    if (eventObject == null)
		break;
	    nameArrayList.add(String.valueOf(eventObject.getEventName()));
	    valueArrayList.add(new Integer(eventObject.getEventId()));
	}
	if ( eventTypeId != 0 )
		se = new SelectElement(AppConstants.EVENT_STR, nameArrayList, valueArrayList, String.valueOf(selectedEventTypeObj.getEvent()), 0);
	else
		se = new SelectElement(AppConstants.EVENT_STR, nameArrayList, valueArrayList, String.valueOf(event), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_TYPE_DESCRIPTION_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventTypeId != 0 )
	    txt = new TextareaElement(AppConstants.EVENT_TYPE_DESCRIPTION_STR, 10, 80, TextareaElement.SOFT, Util.trim(selectedEventTypeObj.getEventTypeDescription()));
	else
	    txt = new TextareaElement(AppConstants.EVENT_TYPE_DESCRIPTION_STR, 10, 80, TextareaElement.SOFT, Constants.EMPTY);
	td = new TableDataElement(txt);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_TYPE_START_DATE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventTypeId != 0 ) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		String formattedDate = dateFormatter.format(selectedEventTypeObj.getEventTypeStartDate());
		td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_TYPE_START_DATE_STR, formattedDate));
	}	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_TYPE_START_DATE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_TYPE_END_DATE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventTypeId != 0 ) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		String formattedDate = dateFormatter.format(selectedEventTypeObj.getEventTypeEndDate());
		td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_TYPE_END_DATE_STR, formattedDate));
	}	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_TYPE_END_DATE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_TYPE_VENUE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventTypeId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_TYPE_VENUE_STR, selectedEventTypeObj.getEventTypeVenue()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_TYPE_VENUE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.ONLINE_REGISTRATION_ONLY_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventTypeId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.ONLINE_REGISTRATION_ONLY_STR, selectedEventTypeObj.getOnlineRegistrationOnly()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.ONLINE_REGISTRATION_ONLY_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);


	tr = new TableRowElement();
	be = new BoldElement(Constants.UPLOAD_FILE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);

	ie = new InputElement(InputElement.FILE, Constants.UPLOAD_FILE_NAME_STR,"");
	td = new TableDataElement(ie);
	tr.add(td);
	te.add(tr);

	return te.getHTMLTag() + new BreakElement().getHTMLTag() +  new BreakElement().getHTMLTag() + UtilBean.getSubmitButton() + UtilBean.getDownloadButton();
    }

    public void writeToFile(String outputFileName, Object obj) throws AppException {
	DebugHandler.fine("writeToFile(" + outputFileName + "," + obj + ")");
	XSSFWorkbook wb = new XSSFWorkbook();
	XSSFFont font01Bold = wb.createFont();
	font01Bold.setFontHeightInPoints((short)12);
	font01Bold.setFontName("Times New Roman");
	font01Bold.setBold(true);

	XSSFFont font01Normal = wb.createFont();
	font01Normal.setFontHeightInPoints((short)12);
	font01Normal.setFontName("Times New Roman");
	font01Normal.setBold(false);

	// Create style
	CellStyle cellstyleTblHdr = wb.createCellStyle();
	cellstyleTblHdr.setFont(font01Bold);
	cellstyleTblHdr.setAlignment(HorizontalAlignment.CENTER);
	cellstyleTblHdr.setWrapText(true);
	cellstyleTblHdr.setBorderBottom(BorderStyle.MEDIUM);
	cellstyleTblHdr.setBorderLeft(BorderStyle.MEDIUM);
	cellstyleTblHdr.setBorderRight(BorderStyle.MEDIUM);
	cellstyleTblHdr.setBorderTop(BorderStyle.MEDIUM);
	cellstyleTblHdr.setVerticalAlignment(VerticalAlignment.CENTER);
	cellstyleTblHdr.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
	cellstyleTblHdr.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	CellStyle cellstyleTblLeft = wb.createCellStyle();
	cellstyleTblLeft.setFont(font01Normal);
	cellstyleTblLeft.setAlignment(HorizontalAlignment.LEFT);
	cellstyleTblLeft.setWrapText(true);
	cellstyleTblLeft.setBorderBottom(BorderStyle.THIN);
	cellstyleTblLeft.setBorderLeft(BorderStyle.THIN);
	cellstyleTblLeft.setBorderRight(BorderStyle.THIN);
	cellstyleTblLeft.setVerticalAlignment(VerticalAlignment.TOP);

	XSSFSheet sheet = wb.createSheet();
	FileOutputStream fileOut = null;
	int rowNum = 0;
	int col = 0;
	XSSFRow row = null;
	XSSFCell cell = null;
	try {
	    fileOut = new FileOutputStream(outputFileName);
	} catch (FileNotFoundException fnf) {
	    throw new AppException("Unable to find file " + outputFileName);
	}
	row = sheet.createRow((short)rowNum);
	sheet.setColumnWidth((short)col, (short) (0));
	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_TYPE_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_TYPE_NAME_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_TYPE_DESCRIPTION_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_TYPE_START_DATE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_TYPE_END_DATE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_TYPE_VENUE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.ONLINE_REGISTRATION_ONLY_LABEL);

	EventTypeInterface eventtypeIf = new EventTypeImpl();
	EventTypeObject[] eventtypeArr = eventtypeIf.getAllEventTypes();
	if ( eventtypeArr != null && eventtypeArr.length > 0 ) {
	    for (int iterator = 0; iterator < eventtypeArr.length; iterator++) {
		EventTypeObject eventtypeObj = eventtypeArr[iterator];
		if ( eventtypeObj == null )
		    break;
		rowNum++;
		col = 0;
		row = sheet.createRow((short)rowNum);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventtypeObj.getEventTypeId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue("INFO");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventtypeObj.getEventTypeName());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventtypeObj.getEvent());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventtypeObj.getEventTypeDescription());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventtypeObj.getEventTypeStartDate());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventtypeObj.getEventTypeEndDate());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventtypeObj.getEventTypeVenue());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventtypeObj.getOnlineRegistrationOnly());
	    }
	}
	try {
	    wb.write(fileOut);
	    fileOut.close();
	} catch (IOException ioe) {
	    throw new AppException("Exception closing file" + outputFileName);
	}
    }

    public void readFromFile(String inputFileName, Object obj) throws AppException {
	DebugHandler.fine("readFromFile(" + inputFileName + obj + ")");
	InputStream fs = null;
	XSSFWorkbook wb = null;
	try {
	    fs = new FileInputStream(inputFileName);
	} catch (IOException ioe) {
	    throw new AppException("IOException while opening file " + inputFileName);
	}
	try {
	    wb = new XSSFWorkbook(fs);
	} catch (IOException ioe) {
	    throw new AppException("IOException while getting workbook.");
	}
	XSSFSheet sheet = wb.getSheetAt(0);
	FileInputStream fileIn = null;
	try {
	    fileIn = new FileInputStream(inputFileName);
	} catch (FileNotFoundException fnf) {
	    throw new AppException("Unable to find file " + inputFileName);
	}
	int rowNum = 0;
	int col = 0;
	XSSFRow row = null;
	XSSFCell cell = null;
	String dbOp = null;
	EventTypeInterface eventtypeIf = new EventTypeImpl();
	EventTypeObject eventtypeObject = new EventTypeObject();

	rowNum = 0;
	while ( true ) {
	    row = sheet.getRow(++rowNum);
	    if ( row == null )
		break;
	    eventtypeObject = new EventTypeObject();
	    cell = row.getCell((short)1);
	    if ( cell != null )
		dbOp = Util.trim(cell.getStringCellValue());
	    else
		dbOp = null;
	    DebugHandler.fine("DbOp = |" + dbOp + "|");
	    if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    eventtypeObject.setEventTypeId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		eventtypeObject = eventtypeIf.getEventType(eventtypeObject.getEventTypeId());
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setEventTypeName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    eventtypeObject.setEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    eventtypeObject.setEvent(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setEventTypeDescription(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setEventTypeStartDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setEventTypeEndDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setEventTypeVenue(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setOnlineRegistrationOnly(Util.trim(cell.getStringCellValue()));
		DebugHandler.fine("Updating EventType " + eventtypeObject);
		eventtypeIf.updateEventType(eventtypeObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setEventTypeName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    eventtypeObject.setEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    eventtypeObject.setEvent(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setEventTypeDescription(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setEventTypeStartDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setEventTypeEndDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setEventTypeVenue(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventtypeObject.setOnlineRegistrationOnly(Util.trim(cell.getStringCellValue()));
		DebugHandler.fine("Adding EventType " + eventtypeObject);
		eventtypeIf.addEventType(eventtypeObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    eventtypeObject.setEventTypeId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		eventtypeObject = eventtypeIf.getEventType(eventtypeObject.getEventTypeId());
		eventtypeIf.deleteEventType(eventtypeObject);
	    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
		throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
	    }
	}
    }
}
