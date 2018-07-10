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

public class RegistrantPaymentBean implements SpreadSheetInterface {
    public int registrantPaymentId = 0;
    public int registrantEvent = 0;
    public int registrant = 0;
    public int paymentType = 0;
    public int paymentStatus = 0;
    RegistrantPaymentObject selectedRegistrantPaymentObj = new RegistrantPaymentObject();
    RegistrantPaymentInterface registrantPaymentIf = new RegistrantPaymentImpl();

    public RegistrantPaymentBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	try {
	    registrantPaymentId = Integer.parseInt(valuepairs.get(AppConstants.REGISTRANT_PAYMENT_ID_STR));
	} catch (NumberFormatException nfe) {
	    registrantPaymentId = 0;
	}
	try {
	    registrantEvent = Integer.parseInt(valuepairs.get(AppConstants.REGISTRANT_EVENT_STR));
	} catch (NumberFormatException nfe) {
	    registrantEvent = 0;
	}
	try {
	    registrant = Integer.parseInt(valuepairs.get(AppConstants.REGISTRANT_STR));
	} catch (NumberFormatException nfe) {
	    registrant = 0;
	}
	try {
	    paymentType = Integer.parseInt(valuepairs.get(AppConstants.PAYMENT_TYPE_STR));
	} catch (NumberFormatException nfe) {
	    paymentType = 0;
	}
	try {
	    paymentStatus = Integer.parseInt(valuepairs.get(AppConstants.PAYMENT_STATUS_STR));
	} catch (NumberFormatException nfe) {
	    paymentStatus = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null ||
	     Boolean.valueOf(saveProfile).booleanValue() == false ) {
	    // This is to display the page
	    if ( registrantPaymentId != 0 ) // Display the selected registrantpayment
		selectedRegistrantPaymentObj = registrantPaymentIf.getRegistrantPayment(registrantPaymentId);
	}
	else {
	    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
	    if ( inputFileName == null ) {
		if ( registrantPaymentId != 0 ) {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_EVENT_STR));
		    selectedRegistrantPaymentObj.setRegistrantEvent(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_STR));
		    selectedRegistrantPaymentObj.setRegistrant(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_TYPE_STR));
		    selectedRegistrantPaymentObj.setPaymentType(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_STATUS_STR));
		    selectedRegistrantPaymentObj.setPaymentStatus(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_AMOUNT_STR));
		    selectedRegistrantPaymentObj.setPaymentAmount(Double.parseDouble(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_ADDITIONAL_AMOUNT_STR));
		    selectedRegistrantPaymentObj.setPaymentAdditionalAmount(Double.parseDouble(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    selectedRegistrantPaymentObj.setPaymentDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.RECEIPT_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    selectedRegistrantPaymentObj.setReceiptDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_DETAILS_STR));
		    selectedRegistrantPaymentObj.setPaymentDetails(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_TOWARDS_STR));
		    selectedRegistrantPaymentObj.setPaymentTowards(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_REFERENCE_ID_STR));
		    selectedRegistrantPaymentObj.setPaymentReferenceId(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_TAX_STR));
		    selectedRegistrantPaymentObj.setPaymentTax(Double.parseDouble(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_FEE_STR));
		    selectedRegistrantPaymentObj.setPaymentFee(Double.parseDouble(buf));
		    DebugHandler.debug("Modifying RegistrantPayment Object " + selectedRegistrantPaymentObj);
		    registrantPaymentIf.updateRegistrantPayment(selectedRegistrantPaymentObj);
		}
		else {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    RegistrantPaymentObject registrantPaymentObj = new RegistrantPaymentObject();
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_EVENT_STR));
		    registrantPaymentObj.setRegistrantEvent(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.REGISTRANT_STR));
		    registrantPaymentObj.setRegistrant(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_TYPE_STR));
		    registrantPaymentObj.setPaymentType(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_STATUS_STR));
		    registrantPaymentObj.setPaymentStatus(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_AMOUNT_STR));
		    registrantPaymentObj.setPaymentAmount(Double.parseDouble(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_ADDITIONAL_AMOUNT_STR));
		    registrantPaymentObj.setPaymentAdditionalAmount(Double.parseDouble(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    registrantPaymentObj.setPaymentDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.RECEIPT_DATE_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    registrantPaymentObj.setReceiptDate(date);
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_DETAILS_STR));
		    registrantPaymentObj.setPaymentDetails(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_TOWARDS_STR));
		    registrantPaymentObj.setPaymentTowards(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_REFERENCE_ID_STR));
		    registrantPaymentObj.setPaymentReferenceId(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_TAX_STR));
		    registrantPaymentObj.setPaymentTax(Double.parseDouble(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PAYMENT_FEE_STR));
		    registrantPaymentObj.setPaymentFee(Double.parseDouble(buf));
		    DebugHandler.debug("Adding RegistrantPayment Object " + registrantPaymentObj);
		    registrantPaymentIf.addRegistrantPayment(registrantPaymentObj);
		}
	    }
	    else {
		String temp = System.getProperty("java.io.tmpdir");
		inputFileName = temp + File.separatorChar + inputFileName;
		readFromFile(inputFileName, null);
	    }
	}
    }

    public String getRegistrantPaymentInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	SelectElement se = null;
	InputElement ie = null;
	TextareaElement txt = null;
	RegistrantPaymentObject registrantPaymentObj = registrantPaymentIf.getRegistrantPayment(registrantPaymentId);

	if ( registrantPaymentObj == null )
	    registrantPaymentObj = new RegistrantPaymentObject();
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.CURRENT_REGISTRANTPAYMENT_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	ArrayList<String> nameArrayList = new ArrayList<String>();
	ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	RegistrantPaymentObject[] registrantPaymentArr = registrantPaymentIf.getAllRegistrantPayments();
	nameArrayList.add(AppConstants.NEW_REGISTRANTPAYMENT);
	valueArrayList.add(new Integer(0));
	for (int iterator = 0; iterator < registrantPaymentArr.length; iterator++) {
	    RegistrantPaymentObject registrantPaymentObject = registrantPaymentArr[iterator];
	    if ( registrantPaymentObject == null )
		break;
	    nameArrayList.add(String.valueOf(registrantPaymentObject.getRegistrantEvent()));
	    valueArrayList.add(new Integer(registrantPaymentObject.getRegistrantPaymentId()));
	}
	se = new SelectElement(AppConstants.REGISTRANT_PAYMENT_ID_STR, nameArrayList, valueArrayList, String.valueOf(registrantPaymentId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
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
	if ( registrantPaymentId != 0 )
		se = new SelectElement(AppConstants.REGISTRANT_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrantPaymentObj.getRegistrantEvent()), 0);
	else
		se = new SelectElement(AppConstants.REGISTRANT_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(registrantEvent), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.REGISTRANT_LABEL);
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
	if ( registrantPaymentId != 0 )
		se = new SelectElement(AppConstants.REGISTRANT_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrantPaymentObj.getRegistrant()), 0);
	else
		se = new SelectElement(AppConstants.REGISTRANT_STR, nameArrayList, valueArrayList, String.valueOf(registrant), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PAYMENT_TYPE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	PaymentTypeInterface payment_typeIf = new PaymentTypeImpl();
	PaymentTypeObject[] payment_typeRefArr = payment_typeIf.getAllPaymentTypes();
	for (int iterator = 0; iterator < payment_typeRefArr.length; iterator++) {
	    PaymentTypeObject payment_typeObject = payment_typeRefArr[iterator];
	    if (payment_typeObject == null)
		break;
	    nameArrayList.add(String.valueOf(payment_typeObject.getPaymentTypeName()));
	    valueArrayList.add(new Integer(payment_typeObject.getPaymentTypeId()));
	}
	if ( registrantPaymentId != 0 )
		se = new SelectElement(AppConstants.PAYMENT_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrantPaymentObj.getPaymentType()), 0);
	else
		se = new SelectElement(AppConstants.PAYMENT_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(paymentType), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PAYMENT_STATUS_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	PaymentStatusInterface payment_statusIf = new PaymentStatusImpl();
	PaymentStatusObject[] payment_statusRefArr = payment_statusIf.getAllPaymentStatus();
	for (int iterator = 0; iterator < payment_statusRefArr.length; iterator++) {
	    PaymentStatusObject payment_statusObject = payment_statusRefArr[iterator];
	    if (payment_statusObject == null)
		break;
	    nameArrayList.add(String.valueOf(payment_statusObject.getPaymentStatusName()));
	    valueArrayList.add(new Integer(payment_statusObject.getPaymentStatusId()));
	}
	if ( registrantPaymentId != 0 )
		se = new SelectElement(AppConstants.PAYMENT_STATUS_STR, nameArrayList, valueArrayList, String.valueOf(selectedRegistrantPaymentObj.getPaymentStatus()), 0);
	else
		se = new SelectElement(AppConstants.PAYMENT_STATUS_STR, nameArrayList, valueArrayList, String.valueOf(paymentStatus), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PAYMENT_AMOUNT_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantPaymentId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_AMOUNT_STR, String.valueOf(selectedRegistrantPaymentObj.getPaymentAmount())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_AMOUNT_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PAYMENT_ADDITIONAL_AMOUNT_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantPaymentId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_ADDITIONAL_AMOUNT_STR, String.valueOf(selectedRegistrantPaymentObj.getPaymentAdditionalAmount())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_ADDITIONAL_AMOUNT_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PAYMENT_DATE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantPaymentId != 0 ) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		String formattedDate = dateFormatter.format(selectedRegistrantPaymentObj.getPaymentDate());
		td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_DATE_STR, formattedDate));
	}	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_DATE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.RECEIPT_DATE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantPaymentId != 0 ) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		String formattedDate = dateFormatter.format(selectedRegistrantPaymentObj.getReceiptDate());
		td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.RECEIPT_DATE_STR, formattedDate));
	}	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.RECEIPT_DATE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PAYMENT_DETAILS_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantPaymentId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_DETAILS_STR, selectedRegistrantPaymentObj.getPaymentDetails()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_DETAILS_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PAYMENT_TOWARDS_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantPaymentId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_TOWARDS_STR, selectedRegistrantPaymentObj.getPaymentTowards()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_TOWARDS_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PAYMENT_REFERENCE_ID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantPaymentId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_REFERENCE_ID_STR, selectedRegistrantPaymentObj.getPaymentReferenceId()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_REFERENCE_ID_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PAYMENT_TAX_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantPaymentId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_TAX_STR, String.valueOf(selectedRegistrantPaymentObj.getPaymentTax())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_TAX_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PAYMENT_FEE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( registrantPaymentId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_FEE_STR, String.valueOf(selectedRegistrantPaymentObj.getPaymentFee())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PAYMENT_FEE_STR, Constants.EMPTY));
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
	cell.setCellValue(AppConstants.REGISTRANT_PAYMENT_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_EVENT_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.REGISTRANT_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PAYMENT_TYPE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PAYMENT_STATUS_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PAYMENT_AMOUNT_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PAYMENT_ADDITIONAL_AMOUNT_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PAYMENT_DATE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.RECEIPT_DATE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PAYMENT_DETAILS_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PAYMENT_TOWARDS_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PAYMENT_REFERENCE_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PAYMENT_TAX_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PAYMENT_FEE_LABEL);

	RegistrantPaymentInterface registrantpaymentIf = new RegistrantPaymentImpl();
	RegistrantPaymentObject[] registrantpaymentArr = registrantpaymentIf.getAllRegistrantPayments();
	if ( registrantpaymentArr != null && registrantpaymentArr.length > 0 ) {
	    for (int iterator = 0; iterator < registrantpaymentArr.length; iterator++) {
		RegistrantPaymentObject registrantpaymentObj = registrantpaymentArr[iterator];
		if ( registrantpaymentObj == null )
		    break;
		rowNum++;
		col = 0;
		row = sheet.createRow((short)rowNum);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getRegistrantPaymentId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue("INFO");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getRegistrantEvent());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getRegistrant());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getPaymentType());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getPaymentStatus());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getPaymentAmount());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getPaymentAdditionalAmount());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getPaymentDate());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getReceiptDate());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getPaymentDetails());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getPaymentTowards());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getPaymentReferenceId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getPaymentTax());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(registrantpaymentObj.getPaymentFee());
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
	RegistrantPaymentInterface registrantpaymentIf = new RegistrantPaymentImpl();
	RegistrantPaymentObject registrantpaymentObject = new RegistrantPaymentObject();

	rowNum = 0;
	while ( true ) {
	    row = sheet.getRow(++rowNum);
	    if ( row == null )
		break;
	    registrantpaymentObject = new RegistrantPaymentObject();
	    cell = row.getCell((short)1);
	    if ( cell != null )
		dbOp = Util.trim(cell.getStringCellValue());
	    else
		dbOp = null;
	    DebugHandler.fine("DbOp = |" + dbOp + "|");
	    if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    registrantpaymentObject.setRegistrantPaymentId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		registrantpaymentObject = registrantpaymentIf.getRegistrantPayment(registrantpaymentObject.getRegistrantPaymentId());
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setRegistrantEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setRegistrantEvent(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setRegistrant((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setRegistrant(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentType((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentType(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentStatus((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentStatus(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentAmount(cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentAmount(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentAdditionalAmount(cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentAdditionalAmount(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantpaymentObject.setPaymentDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantpaymentObject.setReceiptDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantpaymentObject.setPaymentDetails(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantpaymentObject.setPaymentTowards(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantpaymentObject.setPaymentReferenceId(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentTax(cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentTax(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentFee(cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentFee(0);
		}
		DebugHandler.fine("Updating RegistrantPayment " + registrantpaymentObject);
		registrantpaymentIf.updateRegistrantPayment(registrantpaymentObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setRegistrantEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setRegistrantEvent(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setRegistrant((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setRegistrant(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentType((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentType(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentStatus((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentStatus(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentAmount((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentAmount(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentAdditionalAmount((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentAdditionalAmount(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantpaymentObject.setPaymentDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantpaymentObject.setReceiptDate(cell.getDateCellValue());
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantpaymentObject.setPaymentDetails(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantpaymentObject.setPaymentTowards(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    registrantpaymentObject.setPaymentReferenceId(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentTax((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentTax(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    registrantpaymentObject.setPaymentFee((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    registrantpaymentObject.setPaymentFee(0);
		}
		DebugHandler.fine("Adding RegistrantPayment " + registrantpaymentObject);
		registrantpaymentIf.addRegistrantPayment(registrantpaymentObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    registrantpaymentObject.setRegistrantPaymentId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		registrantpaymentObject = registrantpaymentIf.getRegistrantPayment(registrantpaymentObject.getRegistrantPaymentId());
		registrantpaymentIf.deleteRegistrantPayment(registrantpaymentObject);
	    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
		throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
	    }
	}
    }
}
