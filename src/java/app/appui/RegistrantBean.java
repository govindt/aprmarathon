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

public class RegistrantBean implements SpreadSheetInterface {
    public int registrantId = 0;
    RegistrantObject selectedRegistrantObj = new RegistrantObject();
    RegistrantInterface registrantIf = new RegistrantImpl();

    public RegistrantBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	try {
	    registrantId = Integer.parseInt(valuepairs.get(AppConstants.REGISTRANT_ID_STR));
	} catch (NumberFormatException nfe) {
	    registrantId = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null ||
	     Boolean.valueOf(saveProfile).booleanValue() == false ) {
	    // This is to display the page
	    if ( registrantId != 0 ) // Display the selected registrant
		selectedRegistrantObj = registrantIf.getRegistrant(registrantId);
	}
	else {
	    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
	    if ( inputFileName == null ) {
		if ( registrantId != 0 ) {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_NAME_STR));
		    selectedRegistrantObj.setRegistrantName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_MIDDLE_NAME_STR));
		    selectedRegistrantObj.setRegistrantMiddleName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_LAST_NAME_STR));
		    selectedRegistrantObj.setRegistrantLastName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_EMAIL_STR));
		    selectedRegistrantObj.setRegistrantEmail(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_ADDITIONAL_EMAIL_STR));
		    selectedRegistrantObj.setRegistrantAdditionalEmail(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_PHONE_STR));
		    selectedRegistrantObj.setRegistrantPhone(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_ADDRESS_STR));
		    selectedRegistrantObj.setRegistrantAddress(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_CITY_STR));
		    selectedRegistrantObj.setRegistrantCity(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_STATE_STR));
		    selectedRegistrantObj.setRegistrantState(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_PINCODE_STR));
		    selectedRegistrantObj.setRegistrantPincode(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_PAN_STR));
		    selectedRegistrantObj.setRegistrantPan(buf);
		    DebugHandler.debug("Modifying Registrant Object " + selectedRegistrantObj);
		    registrantIf.updateRegistrant(selectedRegistrantObj);
		}
		else {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    RegistrantObject registrantObj = new RegistrantObject();
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_NAME_STR));
		    registrantObj.setRegistrantName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_MIDDLE_NAME_STR));
		    registrantObj.setRegistrantMiddleName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_LAST_NAME_STR));
		    registrantObj.setRegistrantLastName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_EMAIL_STR));
		    registrantObj.setRegistrantEmail(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_ADDITIONAL_EMAIL_STR));
		    registrantObj.setRegistrantAdditionalEmail(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_PHONE_STR));
		    registrantObj.setRegistrantPhone(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_ADDRESS_STR));
		    registrantObj.setRegistrantAddress(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_CITY_STR));
		    registrantObj.setRegistrantCity(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_STATE_STR));
		    registrantObj.setRegistrantState(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_PINCODE_STR));
		    registrantObj.setRegistrantPincode(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_PAN_STR));
		    registrantObj.setRegistrantPan(buf);
		    DebugHandler.debug("Adding Registrant Object " + registrantObj);
		    registrantIf.addRegistrant(registrantObj);
		}
	    }
	    else {
		String temp = System.getProperty("java.io.tmpdir");
		inputFileName = temp + File.separatorChar + inputFileName;
		readFromFile(inputFileName, null);
	    }
	}
    }

    public String getRegistrantInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	SelectElement se = null;
	InputElement ie = null;
	TextareaElement txt = null;
	RegistrantObject registrantObj = registrantIf.getRegistrant(registrantId);

	if ( registrantObj == null )
	    registrantObj = new RegistrantObject();
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.CURRENT_REGISTRANT_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	ArrayList<String> nameArrayList = new ArrayList<String>();
	ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	RegistrantObject[] registrantArr = registrantIf.getAllRegistrants();
	nameArrayList.add(AppConstants.NEW_REGISTRANT);
	valueArrayList.add(new Integer(0));
	for (int iterator = 0; iterator < registrantArr.length; iterator++) {
	    RegistrantObject registrantObject = registrantArr[iterator];
	    if ( registrantObject == null )
		break;
	    nameArrayList.add(registrantObject.getRegistrantName());
	    valueArrayList.add(new Integer(registrantObject.getRegistrantId()));
	}
	se = new SelectElement(AppConstants.REGISTRANT_ID_STR, nameArrayList, valueArrayList, String.valueOf(registrantId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_NAME_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_NAME_STR, selectedRegistrantObj.getRegistrantName()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_NAME_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_MIDDLE_NAME_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_MIDDLE_NAME_STR, selectedRegistrantObj.getRegistrantMiddleName()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_MIDDLE_NAME_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_LAST_NAME_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_LAST_NAME_STR, selectedRegistrantObj.getRegistrantLastName()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_LAST_NAME_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_EMAIL_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_EMAIL_STR, selectedRegistrantObj.getRegistrantEmail()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_EMAIL_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_ADDITIONAL_EMAIL_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_ADDITIONAL_EMAIL_STR, selectedRegistrantObj.getRegistrantAdditionalEmail()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_ADDITIONAL_EMAIL_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_PHONE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_PHONE_STR, selectedRegistrantObj.getRegistrantPhone()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_PHONE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_ADDRESS_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_ADDRESS_STR, selectedRegistrantObj.getRegistrantAddress()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_ADDRESS_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_CITY_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_CITY_STR, selectedRegistrantObj.getRegistrantCity()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_CITY_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_STATE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_STATE_STR, selectedRegistrantObj.getRegistrantState()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_STATE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_PINCODE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_PINCODE_STR, selectedRegistrantObj.getRegistrantPincode()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_PINCODE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_PAN_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_PAN_STR, selectedRegistrantObj.getRegistrantPan()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.REGISTRANT_PAN_STR, Constants.EMPTY));
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
	cell.setCellValue(AppConstants.REGISTRANT_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_NAME_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_MIDDLE_NAME_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_LAST_NAME_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_EMAIL_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_ADDITIONAL_EMAIL_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_PHONE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_ADDRESS_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_CITY_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_STATE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_PINCODE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_PAN_LABEL);

	RegistrantInterface registrantIf = new RegistrantImpl();
	RegistrantObject[] registrantArr = registrantIf.getAllRegistrants();
	if ( registrantArr != null && registrantArr.length > 0 ) {
	    for (int iterator = 0; iterator < registrantArr.length; iterator++) {
		RegistrantObject registrantObj = registrantArr[iterator];
		if ( registrantObj == null )
		    break;
		rowNum++;
		col = 0;
		row = sheet.createRow((short)rowNum);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue("INFO");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantName());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantMiddleName());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantLastName());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantEmail());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantAdditionalEmail());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantPhone());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantAddress());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantCity());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantState());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantPincode());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantObj.getRegistrantPan());
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
	RegistrantInterface registrantIf = new RegistrantImpl();
	RegistrantObject registrantObject = new RegistrantObject();

	rowNum = 0;
	while ( true ) {
	    row = sheet.getRow(++rowNum);
	    if ( row == null )
		break;
	    registrantObject = new RegistrantObject();
	    cell = row.getCell((short)1);
	    if ( cell != null )
		dbOp = Util.trim(cell.getStringCellValue());
	    else
		dbOp = null;
	    DebugHandler.fine("DbOp = |" + dbOp + "|");
	    if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    registrantObject.setRegistrantId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		registrantObject = registrantIf.getRegistrant(registrantObject.getRegistrantId());
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantMiddleName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantLastName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantEmail(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantAdditionalEmail(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantPhone(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantAddress(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantCity(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantState(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantPincode(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantPan(Util.trim(cell.getStringCellValue()));
		DebugHandler.fine("Updating Registrant " + registrantObject);
		registrantIf.updateRegistrant(registrantObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantMiddleName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantLastName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantEmail(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantAdditionalEmail(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantPhone(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantAddress(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantCity(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantState(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantPincode(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantObject.setRegistrantPan(Util.trim(cell.getStringCellValue()));
		DebugHandler.fine("Adding Registrant " + registrantObject);
		registrantIf.addRegistrant(registrantObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    registrantObject.setRegistrantId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		registrantObject = registrantIf.getRegistrant(registrantObject.getRegistrantId());
		registrantIf.deleteRegistrant(registrantObject);
	    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
		throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
	    }
	}
    }
}
