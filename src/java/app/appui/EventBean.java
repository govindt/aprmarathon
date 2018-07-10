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

public class EventBean implements SpreadSheetInterface {
    public int eventId = 0;
    EventObject selectedEventObj = new EventObject();
    EventInterface eventIf = new EventImpl();

    public EventBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	try {
	    eventId = Integer.parseInt(valuepairs.get(AppConstants.EVENT_ID_STR));
	} catch (NumberFormatException nfe) {
	    eventId = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null ||
	     Boolean.valueOf(saveProfile).booleanValue() == false ) {
	    // This is to display the page
	    if ( eventId != 0 ) // Display the selected event
		selectedEventObj = eventIf.getEvent(eventId);
	}
	else {
	    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
	    if ( inputFileName == null ) {
		if ( eventId != 0 ) {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_NAME_STR));
		    selectedEventObj.setEventName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_START_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    selectedEventObj.setEventStartDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_END_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    selectedEventObj.setEventEndDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_DESCRIPTION_STR));
		    selectedEventObj.setEventDescription(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_REGISTATION_CLOSE_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    selectedEventObj.setEventRegistationCloseDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_CHANGES_CLOSE_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    selectedEventObj.setEventChangesCloseDate(date);
		    DebugHandler.debug("Modifying Event Object " + selectedEventObj);
		    eventIf.updateEvent(selectedEventObj);
		}
		else {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    EventObject eventObj = new EventObject();
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_NAME_STR));
		    eventObj.setEventName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_START_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    eventObj.setEventStartDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_END_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    eventObj.setEventEndDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_DESCRIPTION_STR));
		    eventObj.setEventDescription(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_REGISTATION_CLOSE_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    eventObj.setEventRegistationCloseDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.EVENT_CHANGES_CLOSE_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    eventObj.setEventChangesCloseDate(date);
		    DebugHandler.debug("Adding Event Object " + eventObj);
		    eventIf.addEvent(eventObj);
		}
	    }
	    else {
		String temp = System.getProperty("java.io.tmpdir");
		inputFileName = temp + File.separatorChar + inputFileName;
		readFromFile(inputFileName, null);
	    }
	}
    }

    public String getEventInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	SelectElement se = null;
	InputElement ie = null;
	TextareaElement txt = null;
	EventObject eventObj = eventIf.getEvent(eventId);

	if ( eventObj == null )
	    eventObj = new EventObject();
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.CURRENT_EVENT_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	ArrayList<String> nameArrayList = new ArrayList<String>();
	ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	EventObject[] eventArr = eventIf.getAllEvents();
	nameArrayList.add(AppConstants.NEW_EVENT);
	valueArrayList.add(new Integer(0));
	for (int iterator = 0; iterator < eventArr.length; iterator++) {
	    EventObject eventObject = eventArr[iterator];
	    if ( eventObject == null )
		break;
	    nameArrayList.add(eventObject.getEventName());
	    valueArrayList.add(new Integer(eventObject.getEventId()));
	}
	se = new SelectElement(AppConstants.EVENT_ID_STR, nameArrayList, valueArrayList, String.valueOf(eventId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_NAME_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_NAME_STR, selectedEventObj.getEventName()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_NAME_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_START_DATE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventId != 0 ) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		String formattedDate = dateFormatter.format(selectedEventObj.getEventStartDate());
		td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_START_DATE_STR, formattedDate));
	}	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_START_DATE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_END_DATE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventId != 0 ) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		String formattedDate = dateFormatter.format(selectedEventObj.getEventEndDate());
		td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_END_DATE_STR, formattedDate));
	}	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_END_DATE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_DESCRIPTION_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventId != 0 )
	    txt = new TextareaElement(AppConstants.EVENT_DESCRIPTION_STR, 10, 80, TextareaElement.SOFT, Util.trim(selectedEventObj.getEventDescription()));
	else
	    txt = new TextareaElement(AppConstants.EVENT_DESCRIPTION_STR, 10, 80, TextareaElement.SOFT, Constants.EMPTY);
	td = new TableDataElement(txt);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_REGISTATION_CLOSE_DATE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventId != 0 ) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		String formattedDate = dateFormatter.format(selectedEventObj.getEventRegistationCloseDate());
		td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_REGISTATION_CLOSE_DATE_STR, formattedDate));
	}	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_REGISTATION_CLOSE_DATE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EVENT_CHANGES_CLOSE_DATE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( eventId != 0 ) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		String formattedDate = dateFormatter.format(selectedEventObj.getEventChangesCloseDate());
		td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_CHANGES_CLOSE_DATE_STR, formattedDate));
	}	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.EVENT_CHANGES_CLOSE_DATE_STR, Constants.EMPTY));
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
	cell.setCellValue(AppConstants.EVENT_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_NAME_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_START_DATE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_END_DATE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_DESCRIPTION_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_REGISTATION_CLOSE_DATE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.EVENT_CHANGES_CLOSE_DATE_LABEL);

	EventInterface eventIf = new EventImpl();
	EventObject[] eventArr = eventIf.getAllEvents();
	if ( eventArr != null && eventArr.length > 0 ) {
	    for (int iterator = 0; iterator < eventArr.length; iterator++) {
		EventObject eventObj = eventArr[iterator];
		if ( eventObj == null )
		    break;
		rowNum++;
		col = 0;
		row = sheet.createRow((short)rowNum);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventObj.getEventId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue("INFO");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventObj.getEventName());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventObj.getEventStartDate());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventObj.getEventEndDate());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventObj.getEventDescription());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventObj.getEventRegistationCloseDate());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(eventObj.getEventChangesCloseDate());
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
	EventInterface eventIf = new EventImpl();
	EventObject eventObject = new EventObject();

	rowNum = 0;
	while ( true ) {
	    row = sheet.getRow(++rowNum);
	    if ( row == null )
		break;
	    eventObject = new EventObject();
	    cell = row.getCell((short)1);
	    if ( cell != null )
		dbOp = Util.trim(cell.getStringCellValue());
	    else
		dbOp = null;
	    DebugHandler.fine("DbOp = |" + dbOp + "|");
	    if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    eventObject.setEventId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		eventObject = eventIf.getEvent(eventObject.getEventId());
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventStartDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventEndDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventDescription(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventRegistationCloseDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventChangesCloseDate(cell.getDateCellValue());
		DebugHandler.fine("Updating Event " + eventObject);
		eventIf.updateEvent(eventObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventStartDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventEndDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventDescription(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventRegistationCloseDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    eventObject.setEventChangesCloseDate(cell.getDateCellValue());
		DebugHandler.fine("Adding Event " + eventObject);
		eventIf.addEvent(eventObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    eventObject.setEventId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		eventObject = eventIf.getEvent(eventObject.getEventId());
		eventIf.deleteEvent(eventObject);
	    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
		throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
	    }
	}
    }
}
