/*
 *
 *
 * APR Marathon Registration App Project - MANUAL EDIT
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

public class RegistrantEventBean implements SpreadSheetInterface {
    public int registrantEventId = 0;
    public int registrantId = 0;
    public int registrantEvent = 0;
    public int registrantType = 0;
    public int registrantSource = 0;
    public int registrantClass = 0;
    public int registrantBeneficiary = 0;
    RegistrantEventObject selectedRegistrantEventObj = new RegistrantEventObject();
    RegistrantEventInterface registrantEventIf = new RegistrantEventImpl();

    public RegistrantEventBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	try {
	    registrantEventId = Integer.parseInt(valuepairs.get(AppConstants.REGISTRANT_EVENT_ID_STR));
	} catch (NumberFormatException nfe) {
	    registrantEventId = 0;
	}
	try {
	    registrantId = Integer.parseInt(valuepairs.get(AppConstants.REGISTRANT_ID_STR));
	} catch (NumberFormatException nfe) {
	    registrantId = 0;
	}
	try {
	    registrantEvent = Integer.parseInt(valuepairs.get(AppConstants.REGISTRANT_EVENT_STR));
	} catch (NumberFormatException nfe) {
	    registrantEvent = 0;
	}
	try {
	    registrantType = Integer.parseInt(valuepairs.get(AppConstants.REGISTRANT_TYPE_STR));
	} catch (NumberFormatException nfe) {
	    registrantType = 0;
	}
	try {
	    registrantSource = Integer.parseInt(valuepairs.get(AppConstants.REGISTRANT_SOURCE_STR));
	} catch (NumberFormatException nfe) {
	    registrantSource = 0;
	}
	try {
	    registrantClass = Integer.parseInt(valuepairs.get(AppConstants.REGISTRANT_CLASS_STR));
	} catch (NumberFormatException nfe) {
	    registrantClass = 0;
	}
	try {
	    registrantBeneficiary = Integer.parseInt(valuepairs.get(AppConstants.REGISTRANT_BENEFICIARY_STR));
	} catch (NumberFormatException nfe) {
	    registrantBeneficiary = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null ||
	     Boolean.valueOf(saveProfile).booleanValue() == false ) {
	    // This is to display the page
	    if ( registrantEventId != 0 ) // Display the selected registrantevent
		selectedRegistrantEventObj = registrantEventIf.getRegistrantEvent(registrantEventId);
	}
	else {
	    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
	    if ( inputFileName == null ) {
		if ( registrantEventId != 0 ) {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_ID_STR));
		    selectedRegistrantEventObj.setRegistrantId(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_EVENT_STR));
		    selectedRegistrantEventObj.setRegistrantEvent(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_TYPE_STR));
		    selectedRegistrantEventObj.setRegistrantType(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_SOURCE_STR));
		    selectedRegistrantEventObj.setRegistrantSource(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_CLASS_STR));
		    selectedRegistrantEventObj.setRegistrantClass(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_BENEFICIARY_STR));
		    selectedRegistrantEventObj.setRegistrantBeneficiary(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_EMERGENCY_CONTACT_STR));
		    selectedRegistrantEventObj.setRegistrantEmergencyContact(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_EMERGENCY_PHONE_STR));
		    selectedRegistrantEventObj.setRegistrantEmergencyPhone(buf);
		    DebugHandler.debug("Modifying RegistrantEvent Object " + selectedRegistrantEventObj);
		    registrantEventIf.updateRegistrantEvent(selectedRegistrantEventObj);
		}
		else {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    RegistrantEventObject registrantEventObj = new RegistrantEventObject();
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_ID_STR));
		    registrantEventObj.setRegistrantId(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_EVENT_STR));
		    registrantEventObj.setRegistrantEvent(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_TYPE_STR));
		    registrantEventObj.setRegistrantType(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_SOURCE_STR));
		    registrantEventObj.setRegistrantSource(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_CLASS_STR));
		    registrantEventObj.setRegistrantClass(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_BENEFICIARY_STR));
		    registrantEventObj.setRegistrantBeneficiary(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_EMERGENCY_CONTACT_STR));
		    registrantEventObj.setRegistrantEmergencyContact(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_EMERGENCY_PHONE_STR));
		    registrantEventObj.setRegistrantEmergencyPhone(buf);
		    DebugHandler.debug("Adding RegistrantEvent Object " + registrantEventObj);
		    registrantEventIf.addRegistrantEvent(registrantEventObj);
		}
	    }
	    else {
		String temp = System.getProperty("java.io.tmpdir");
		inputFileName = temp + File.separatorChar + inputFileName;
		readFromFile(inputFileName, null);
	    }
	}
    }

    public String getRegistrantEventInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	SelectElement se = null;
	InputElement ie = null;
	TextareaElement txt = null;
	RegistrantEventObject registrantEventObj = registrantEventIf.getRegistrantEvent(registrantEventId);

	if ( registrantEventObj == null )
	    registrantEventObj = new RegistrantEventObject();
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.CURRENT_REGISTRANTEVENT_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	ArrayList<String> nameArrayList = new ArrayList<String>();
	ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	RegistrantEventObject[] registrantEventArr = registrantEventIf.getAllRegistrantEvents();
	nameArrayList.add(AppConstants.NEW_REGISTRANTEVENT);
	valueArrayList.add(new Integer(0));
	for (int iterator = 0; iterator < registrantEventArr.length; iterator++) {
	    RegistrantEventObject registrantEventObject = registrantEventArr[iterator];
	    if ( registrantEventObject == null )
		break;
	    nameArrayList.add(String.valueOf(registrantEventObject.getRegistrantId()));
	    valueArrayList.add(new Integer(registrantEventObject.getRegistrantEventId()));
	}
	se = new SelectElement(AppConstants.REGISTRANT_EVENT_ID_STR, nameArrayList, valueArrayList, String.valueOf(registrantEventId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_ID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	RegistrantInterface registrantIf = new RegistrantImpl();
	RegistrantObject[] registrantRefArr = registrantIf.getAllRegistrants();
	for (int iterator = 0; iterator < registrantRefArr.length; iterator++) {
	    RegistrantObject registrantObject = registrantRefArr[iterator];
	    if (registrantObject == null)
		break;
	    nameArrayList.add(String.valueOf(registrantObject.getRegistrantName()));
	    valueArrayList.add(new Integer(registrantObject.getRegistrantId()));
	}
	if ( registrantEventId != 0 )
		se = new SelectElement(AppConstants.REGISTRANT_ID_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrantEventObj.getRegistrantId()), 0);
	else
		se = new SelectElement(AppConstants.REGISTRANT_ID_STR, nameArrayList, valueArrayList, String.valueOf(registrantId), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_EVENT_LABEL);
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
	if ( registrantEventId != 0 )
		se = new SelectElement(AppConstants.REGISTRANT_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrantEventObj.getRegistrantEvent()), 0);
	else
		se = new SelectElement(AppConstants.REGISTRANT_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(registrantEvent), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_TYPE_LABEL);
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
	if ( registrantEventId != 0 )
		se = new SelectElement(AppConstants.REGISTRANT_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrantEventObj.getRegistrantType()), 0);
	else
		se = new SelectElement(AppConstants.REGISTRANT_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(registrantType), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_SOURCE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	RegistrationSourceInterface registration_sourceIf = new RegistrationSourceImpl();
	RegistrationSourceObject[] registration_sourceRefArr = registration_sourceIf.getAllRegistrationSources();
	for (int iterator = 0; iterator < registration_sourceRefArr.length; iterator++) {
	    RegistrationSourceObject registration_sourceObject = registration_sourceRefArr[iterator];
	    if (registration_sourceObject == null)
		break;
	    nameArrayList.add(String.valueOf(registration_sourceObject.getRegistrationSourceName()));
	    valueArrayList.add(new Integer(registration_sourceObject.getRegistrationSourceId()));
	}
	if ( registrantEventId != 0 )
		se = new SelectElement(AppConstants.REGISTRANT_SOURCE_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrantEventObj.getRegistrantSource()), 0);
	else
		se = new SelectElement(AppConstants.REGISTRANT_SOURCE_STR, nameArrayList, valueArrayList, String.valueOf(registrantSource), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_CLASS_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	RegistrationClassInterface registration_classIf = new RegistrationClassImpl();
	RegistrationClassObject[] registration_classRefArr = registration_classIf.getAllRegistrationClass();
	for (int iterator = 0; iterator < registration_classRefArr.length; iterator++) {
	    RegistrationClassObject registration_classObject = registration_classRefArr[iterator];
	    if (registration_classObject == null)
		break;
	    nameArrayList.add(String.valueOf(registration_classObject.getRegistrationClassName()));
	    valueArrayList.add(new Integer(registration_classObject.getRegistrationClassId()));
	}
	if ( registrantEventId != 0 )
		se = new SelectElement(AppConstants.REGISTRANT_CLASS_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrantEventObj.getRegistrantClass()), 0);
	else
		se = new SelectElement(AppConstants.REGISTRANT_CLASS_STR, nameArrayList, valueArrayList, String.valueOf(registrantClass), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_BENEFICIARY_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	BeneficiaryInterface beneficiaryIf = new BeneficiaryImpl();
	BeneficiaryObject[] beneficiaryRefArr = beneficiaryIf.getAllBeneficiarys();
	for (int iterator = 0; iterator < beneficiaryRefArr.length; iterator++) {
	    BeneficiaryObject beneficiaryObject = beneficiaryRefArr[iterator];
	    if (beneficiaryObject == null)
		break;
	    nameArrayList.add(String.valueOf(beneficiaryObject.getBeneficiaryName()));
	    valueArrayList.add(new Integer(beneficiaryObject.getBeneficiaryId()));
	}
	if ( registrantEventId != 0 )
		se = new SelectElement(AppConstants.REGISTRANT_BENEFICIARY_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrantEventObj.getRegistrantBeneficiary()), 0);
	else
		se = new SelectElement(AppConstants.REGISTRANT_BENEFICIARY_STR, nameArrayList, valueArrayList, String.valueOf(registrantBeneficiary), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_EMERGENCY_CONTACT_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantEventId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_EMERGENCY_CONTACT_STR, selectedRegistrantEventObj.getRegistrantEmergencyContact()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_EMERGENCY_CONTACT_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_EMERGENCY_PHONE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantEventId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_EMERGENCY_PHONE_STR, selectedRegistrantEventObj.getRegistrantEmergencyPhone()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_EMERGENCY_PHONE_STR, Constants.EMPTY));
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
	cell.setCellValue(AppConstants.REGISTRANT_EVENT_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_EVENT_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_TYPE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_SOURCE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_CLASS_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_BENEFICIARY_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_EMERGENCY_CONTACT_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_EMERGENCY_PHONE_LABEL);

	RegistrantEventInterface registranteventIf = new RegistrantEventImpl();
	RegistrantEventObject[] registranteventArr = registranteventIf.getAllRegistrantEvents();
	if ( registranteventArr != null && registranteventArr.length > 0 ) {
	    for (int iterator = 0; iterator < registranteventArr.length; iterator++) {
		RegistrantEventObject registranteventObj = registranteventArr[iterator];
		if ( registranteventObj == null )
		    break;
		rowNum++;
		col = 0;
		row = sheet.createRow((short)rowNum);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registranteventObj.getRegistrantEventId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue("INFO");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registranteventObj.getRegistrantId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registranteventObj.getRegistrantEvent());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registranteventObj.getRegistrantType());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registranteventObj.getRegistrantSource());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registranteventObj.getRegistrantClass());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registranteventObj.getRegistrantBeneficiary());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registranteventObj.getRegistrantEmergencyContact());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registranteventObj.getRegistrantEmergencyPhone());
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
	RegistrantEventInterface registranteventIf = new RegistrantEventImpl();
	RegistrantEventObject registranteventObject = new RegistrantEventObject();

	rowNum = 0;
	while ( true ) {
	    row = sheet.getRow(++rowNum);
	    if ( row == null )
		break;
	    registranteventObject = new RegistrantEventObject();
	    cell = row.getCell((short)1);
	    if ( cell != null )
		dbOp = Util.trim(cell.getStringCellValue());
	    else
		dbOp = null;
	    DebugHandler.fine("DbOp = |" + dbOp + "|");
	    if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    registranteventObject.setRegistrantEventId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		registranteventObject = registranteventIf.getRegistrantEvent(registranteventObject.getRegistrantEventId());
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantId(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantEvent(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantType((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantType(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantSource((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantSource(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantClass((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantClass(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantBeneficiary((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantBeneficiary(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registranteventObject.setRegistrantEmergencyContact(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registranteventObject.setRegistrantEmergencyPhone(Util.trim(cell.getStringCellValue()));
		DebugHandler.fine("Updating RegistrantEvent " + registranteventObject);
		registranteventIf.updateRegistrantEvent(registranteventObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantId(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantEvent(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantType((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantType(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantSource((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantSource(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantClass((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantClass(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registranteventObject.setRegistrantBeneficiary((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registranteventObject.setRegistrantBeneficiary(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registranteventObject.setRegistrantEmergencyContact(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registranteventObject.setRegistrantEmergencyPhone(Util.trim(cell.getStringCellValue()));
		DebugHandler.fine("Adding RegistrantEvent " + registranteventObject);
		registranteventIf.addRegistrantEvent(registranteventObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    registranteventObject.setRegistrantEventId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		registranteventObject = registranteventIf.getRegistrantEvent(registranteventObject.getRegistrantEventId());
		registranteventIf.deleteRegistrantEvent(registranteventObject);
	    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
		throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
	    }
	}
    }
}
