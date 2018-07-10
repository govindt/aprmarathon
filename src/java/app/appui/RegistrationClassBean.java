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

public class RegistrationClassBean implements SpreadSheetInterface {
    public int registrationClassId = 0;
    public int registrationType = 0;
    public int registrationEvent = 0;
    RegistrationClassObject selectedRegistrationClassObj = new RegistrationClassObject();
    RegistrationClassInterface registrationClassIf = new RegistrationClassImpl();

    public RegistrationClassBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	try {
	    registrationClassId = Integer.parseInt(valuepairs.get(AppConstants.REGISTRATION_CLASS_ID_STR));
	} catch (NumberFormatException nfe) {
	    registrationClassId = 0;
	}
	try {
	    registrationType = Integer.parseInt(valuepairs.get(AppConstants.REGISTRATION_TYPE_STR));
	} catch (NumberFormatException nfe) {
	    registrationType = 0;
	}
	try {
	    registrationEvent = Integer.parseInt(valuepairs.get(AppConstants.REGISTRATION_EVENT_STR));
	} catch (NumberFormatException nfe) {
	    registrationEvent = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null ||
	     Boolean.valueOf(saveProfile).booleanValue() == false ) {
	    // This is to display the page
	    if ( registrationClassId != 0 ) // Display the selected registrationclass
		selectedRegistrationClassObj = registrationClassIf.getRegistrationClas(registrationClassId);
	}
	else {
	    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
	    if ( inputFileName == null ) {
		if ( registrationClassId != 0 ) {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRATION_CLASS_NAME_STR));
		    selectedRegistrationClassObj.setRegistrationClassName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRATION_TYPE_STR));
		    selectedRegistrationClassObj.setRegistrationType(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRATION_EVENT_STR));
		    selectedRegistrationClassObj.setRegistrationEvent(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRATION_CLASS_VALUE_STR));
		    selectedRegistrationClassObj.setRegistrationClassValue(Double.parseDouble(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRATION_FREE_TICKETS_STR));
		    selectedRegistrationClassObj.setRegistrationFreeTickets(Integer.parseInt(buf));
		    DebugHandler.debug("Modifying RegistrationClass Object " + selectedRegistrationClassObj);
		    registrationClassIf.updateRegistrationClass(selectedRegistrationClassObj);
		}
		else {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    RegistrationClassObject registrationClassObj = new RegistrationClassObject();
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRATION_CLASS_NAME_STR));
		    registrationClassObj.setRegistrationClassName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRATION_TYPE_STR));
		    registrationClassObj.setRegistrationType(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRATION_EVENT_STR));
		    registrationClassObj.setRegistrationEvent(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRATION_CLASS_VALUE_STR));
		    registrationClassObj.setRegistrationClassValue(Double.parseDouble(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRATION_FREE_TICKETS_STR));
		    registrationClassObj.setRegistrationFreeTickets(Integer.parseInt(buf));
		    DebugHandler.debug("Adding RegistrationClass Object " + registrationClassObj);
		    registrationClassIf.addRegistrationClass(registrationClassObj);
		}
	    }
	    else {
		String temp = System.getProperty("java.io.tmpdir");
		inputFileName = temp + File.separatorChar + inputFileName;
		readFromFile(inputFileName, null);
	    }
	}
    }

    public String getRegistrationClassInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	SelectElement se = null;
	InputElement ie = null;
	TextareaElement txt = null;
	RegistrationClassObject registrationClassObj = registrationClassIf.getRegistrationClas(registrationClassId);

	if ( registrationClassObj == null )
	    registrationClassObj = new RegistrationClassObject();
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.CURRENT_REGISTRATIONCLASS_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	ArrayList<String> nameArrayList = new ArrayList<String>();
	ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	RegistrationClassObject[] registrationClassArr = registrationClassIf.getAllRegistrationClass();
	nameArrayList.add(AppConstants.NEW_REGISTRATIONCLASS);
	valueArrayList.add(new Integer(0));
	for (int iterator = 0; iterator < registrationClassArr.length; iterator++) {
	    RegistrationClassObject registrationClassObject = registrationClassArr[iterator];
	    if ( registrationClassObject == null )
		break;
	    nameArrayList.add(registrationClassObject.getRegistrationClassName());
	    valueArrayList.add(new Integer(registrationClassObject.getRegistrationClassId()));
	}
	se = new SelectElement(AppConstants.REGISTRATION_CLASS_ID_STR, nameArrayList, valueArrayList, String.valueOf(registrationClassId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRATION_CLASS_NAME_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrationClassId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRATION_CLASS_NAME_STR, selectedRegistrationClassObj.getRegistrationClassName()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRATION_CLASS_NAME_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRATION_TYPE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	RegistrationTypeInterface registrationtypeIf = new RegistrationTypeImpl();
	RegistrationTypeObject[] registrationtypeRefArr = registrationtypeIf.getAllRegistrationTypes();
	for (int iterator = 0; iterator < registrationtypeRefArr.length; iterator++) {
	    RegistrationTypeObject registrationtypeObject = registrationtypeRefArr[iterator];
	    if (registrationtypeObject == null)
		break;
	    nameArrayList.add(String.valueOf(registrationtypeObject.getRegistrationTypeName()));
	    valueArrayList.add(new Integer(registrationtypeObject.getRegistrationTypeId()));
	}
	if ( registrationClassId != 0 )
		se = new SelectElement(AppConstants.REGISTRATION_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrationClassObj.getRegistrationType()), 0);
	else
		se = new SelectElement(AppConstants.REGISTRATION_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(registrationType), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRATION_EVENT_LABEL);
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
	if ( registrationClassId != 0 )
		se = new SelectElement(AppConstants.REGISTRATION_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrationClassObj.getRegistrationEvent()), 0);
	else
		se = new SelectElement(AppConstants.REGISTRATION_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(registrationEvent), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRATION_CLASS_VALUE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrationClassId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRATION_CLASS_VALUE_STR, String.valueOf(selectedRegistrationClassObj.getRegistrationClassValue())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRATION_CLASS_VALUE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRATION_FREE_TICKETS_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrationClassId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRATION_FREE_TICKETS_STR, String.valueOf(selectedRegistrationClassObj.getRegistrationFreeTickets())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRATION_FREE_TICKETS_STR, Constants.EMPTY));
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
	cell.setCellValue(AppConstants.REGISTRATION_CLASS_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRATION_CLASS_NAME_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRATION_TYPE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRATION_EVENT_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRATION_CLASS_VALUE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRATION_FREE_TICKETS_LABEL);

	RegistrationClassInterface registrationclassIf = new RegistrationClassImpl();
	RegistrationClassObject[] registrationclassArr = registrationclassIf.getAllRegistrationClass();
	if ( registrationclassArr != null && registrationclassArr.length > 0 ) {
	    for (int iterator = 0; iterator < registrationclassArr.length; iterator++) {
		RegistrationClassObject registrationclassObj = registrationclassArr[iterator];
		if ( registrationclassObj == null )
		    break;
		rowNum++;
		col = 0;
		row = sheet.createRow((short)rowNum);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrationclassObj.getRegistrationClassId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue("INFO");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrationclassObj.getRegistrationClassName());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrationclassObj.getRegistrationType());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrationclassObj.getRegistrationEvent());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrationclassObj.getRegistrationClassValue());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrationclassObj.getRegistrationFreeTickets());
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
	RegistrationClassInterface registrationclassIf = new RegistrationClassImpl();
	RegistrationClassObject registrationclassObject = new RegistrationClassObject();

	rowNum = 0;
	while ( true ) {
	    row = sheet.getRow(++rowNum);
	    if ( row == null )
		break;
	    registrationclassObject = new RegistrationClassObject();
	    cell = row.getCell((short)1);
	    if ( cell != null )
		dbOp = Util.trim(cell.getStringCellValue());
	    else
		dbOp = null;
	    DebugHandler.fine("DbOp = |" + dbOp + "|");
	    if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    registrationclassObject.setRegistrationClassId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		registrationclassObject = registrationclassIf.getRegistrationClas(registrationclassObject.getRegistrationClassId());
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrationclassObject.setRegistrationClassName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrationclassObject.setRegistrationType((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrationclassObject.setRegistrationType(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrationclassObject.setRegistrationEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrationclassObject.setRegistrationEvent(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrationclassObject.setRegistrationClassValue(cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrationclassObject.setRegistrationClassValue(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrationclassObject.setRegistrationFreeTickets((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrationclassObject.setRegistrationFreeTickets(0);
		}
		DebugHandler.fine("Updating RegistrationClass " + registrationclassObject);
		registrationclassIf.updateRegistrationClass(registrationclassObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrationclassObject.setRegistrationClassName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrationclassObject.setRegistrationType((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrationclassObject.setRegistrationType(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrationclassObject.setRegistrationEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrationclassObject.setRegistrationEvent(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrationclassObject.setRegistrationClassValue((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrationclassObject.setRegistrationClassValue(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrationclassObject.setRegistrationFreeTickets((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrationclassObject.setRegistrationFreeTickets(0);
		}
		DebugHandler.fine("Adding RegistrationClass " + registrationclassObject);
		registrationclassIf.addRegistrationClass(registrationclassObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    registrationclassObject.setRegistrationClassId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		registrationclassObject = registrationclassIf.getRegistrationClas(registrationclassObject.getRegistrationClassId());
		registrationclassIf.deleteRegistrationClass(registrationclassObject);
	    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
		throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
	    }
	}
    }
}
