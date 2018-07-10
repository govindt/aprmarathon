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

public class BeneficiaryBean implements SpreadSheetInterface {
    public int beneficiaryId = 0;
    public int beneficiaryEvent = 0;
    BeneficiaryObject selectedBeneficiaryObj = new BeneficiaryObject();
    BeneficiaryInterface beneficiaryIf = new BeneficiaryImpl();

    public BeneficiaryBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	try {
	    beneficiaryId = Integer.parseInt(valuepairs.get(AppConstants.BENEFICIARY_ID_STR));
	} catch (NumberFormatException nfe) {
	    beneficiaryId = 0;
	}
	try {
	    beneficiaryEvent = Integer.parseInt(valuepairs.get(AppConstants.BENEFICIARY_EVENT_STR));
	} catch (NumberFormatException nfe) {
	    beneficiaryEvent = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null ||
	     Boolean.valueOf(saveProfile).booleanValue() == false ) {
	    // This is to display the page
	    if ( beneficiaryId != 0 ) // Display the selected beneficiary
		selectedBeneficiaryObj = beneficiaryIf.getBeneficiary(beneficiaryId);
	}
	else {
	    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
	    if ( inputFileName == null ) {
		if ( beneficiaryId != 0 ) {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    buf = Util.trim(valuepairs.get(AppConstants.BENEFICIARY_NAME_STR));
		    selectedBeneficiaryObj.setBeneficiaryName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.BENEFICIARY_EVENT_STR));
		    selectedBeneficiaryObj.setBeneficiaryEvent(Integer.parseInt(buf));
		    DebugHandler.debug("Modifying Beneficiary Object " + selectedBeneficiaryObj);
		    beneficiaryIf.updateBeneficiary(selectedBeneficiaryObj);
		}
		else {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    BeneficiaryObject beneficiaryObj = new BeneficiaryObject();
		    buf = Util.trim(valuepairs.get(AppConstants.BENEFICIARY_NAME_STR));
		    beneficiaryObj.setBeneficiaryName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.BENEFICIARY_EVENT_STR));
		    beneficiaryObj.setBeneficiaryEvent(Integer.parseInt(buf));
		    DebugHandler.debug("Adding Beneficiary Object " + beneficiaryObj);
		    beneficiaryIf.addBeneficiary(beneficiaryObj);
		}
	    }
	    else {
		String temp = System.getProperty("java.io.tmpdir");
		inputFileName = temp + File.separatorChar + inputFileName;
		readFromFile(inputFileName, null);
	    }
	}
    }

    public String getBeneficiaryInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	SelectElement se = null;
	InputElement ie = null;
	TextareaElement txt = null;
	BeneficiaryObject beneficiaryObj = beneficiaryIf.getBeneficiary(beneficiaryId);

	if ( beneficiaryObj == null )
	    beneficiaryObj = new BeneficiaryObject();
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.CURRENT_BENEFICIARY_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	ArrayList<String> nameArrayList = new ArrayList<String>();
	ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	BeneficiaryObject[] beneficiaryArr = beneficiaryIf.getAllBeneficiarys();
	nameArrayList.add(AppConstants.NEW_BENEFICIARY);
	valueArrayList.add(new Integer(0));
	for (int iterator = 0; iterator < beneficiaryArr.length; iterator++) {
	    BeneficiaryObject beneficiaryObject = beneficiaryArr[iterator];
	    if ( beneficiaryObject == null )
		break;
	    nameArrayList.add(beneficiaryObject.getBeneficiaryName());
	    valueArrayList.add(new Integer(beneficiaryObject.getBeneficiaryId()));
	}
	se = new SelectElement(AppConstants.BENEFICIARY_ID_STR, nameArrayList, valueArrayList, String.valueOf(beneficiaryId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.BENEFICIARY_NAME_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( beneficiaryId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.BENEFICIARY_NAME_STR, selectedBeneficiaryObj.getBeneficiaryName()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.BENEFICIARY_NAME_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.BENEFICIARY_EVENT_LABEL);
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
	if ( beneficiaryId != 0 )
		se = new SelectElement(AppConstants.BENEFICIARY_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(selectedBeneficiaryObj.getBeneficiaryEvent()), 0);
	else
		se = new SelectElement(AppConstants.BENEFICIARY_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(beneficiaryEvent), 0);
	td = new TableDataElement(se);
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
	cell.setCellValue(AppConstants.BENEFICIARY_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.BENEFICIARY_NAME_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.BENEFICIARY_EVENT_LABEL);

	BeneficiaryInterface beneficiaryIf = new BeneficiaryImpl();
	BeneficiaryObject[] beneficiaryArr = beneficiaryIf.getAllBeneficiarys();
	if ( beneficiaryArr != null && beneficiaryArr.length > 0 ) {
	    for (int iterator = 0; iterator < beneficiaryArr.length; iterator++) {
		BeneficiaryObject beneficiaryObj = beneficiaryArr[iterator];
		if ( beneficiaryObj == null )
		    break;
		rowNum++;
		col = 0;
		row = sheet.createRow((short)rowNum);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(beneficiaryObj.getBeneficiaryId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue("INFO");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(beneficiaryObj.getBeneficiaryName());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(beneficiaryObj.getBeneficiaryEvent());
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
	BeneficiaryInterface beneficiaryIf = new BeneficiaryImpl();
	BeneficiaryObject beneficiaryObject = new BeneficiaryObject();

	rowNum = 0;
	while ( true ) {
	    row = sheet.getRow(++rowNum);
	    if ( row == null )
		break;
	    beneficiaryObject = new BeneficiaryObject();
	    cell = row.getCell((short)1);
	    if ( cell != null )
		dbOp = Util.trim(cell.getStringCellValue());
	    else
		dbOp = null;
	    DebugHandler.fine("DbOp = |" + dbOp + "|");
	    if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    beneficiaryObject.setBeneficiaryId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		beneficiaryObject = beneficiaryIf.getBeneficiary(beneficiaryObject.getBeneficiaryId());
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    beneficiaryObject.setBeneficiaryName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    beneficiaryObject.setBeneficiaryEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    beneficiaryObject.setBeneficiaryEvent(0);
		}
		DebugHandler.fine("Updating Beneficiary " + beneficiaryObject);
		beneficiaryIf.updateBeneficiary(beneficiaryObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    beneficiaryObject.setBeneficiaryName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    beneficiaryObject.setBeneficiaryEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    beneficiaryObject.setBeneficiaryEvent(0);
		}
		DebugHandler.fine("Adding Beneficiary " + beneficiaryObject);
		beneficiaryIf.addBeneficiary(beneficiaryObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    beneficiaryObject.setBeneficiaryId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		beneficiaryObject = beneficiaryIf.getBeneficiary(beneficiaryObject.getBeneficiaryId());
		beneficiaryIf.deleteBeneficiary(beneficiaryObject);
	    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
		throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
	    }
	}
    }
}
