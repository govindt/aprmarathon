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

public class AgeCategoryBean implements SpreadSheetInterface {
    public int ageCategoryId = 0;
    AgeCategoryObject selectedAgeCategoryObj = new AgeCategoryObject();
    AgeCategoryInterface ageCategoryIf = new AgeCategoryImpl();

    public AgeCategoryBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	try {
	    ageCategoryId = Integer.parseInt(valuepairs.get(AppConstants.AGE_CATEGORY_ID_STR));
	} catch (NumberFormatException nfe) {
	    ageCategoryId = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null ||
	     Boolean.valueOf(saveProfile).booleanValue() == false ) {
	    // This is to display the page
	    if ( ageCategoryId != 0 ) // Display the selected agecategory
		selectedAgeCategoryObj = ageCategoryIf.getAgeCategory(ageCategoryId);
	}
	else {
	    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
	    if ( inputFileName == null ) {
		if ( ageCategoryId != 0 ) {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    buf = Util.trim(valuepairs.get(AppConstants.AGE_CATEGORY_STR));
		    selectedAgeCategoryObj.setAgeCategory(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.MIN_AGE_STR));
		    selectedAgeCategoryObj.setMinAge(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.MAX_AGE_STR));
		    selectedAgeCategoryObj.setMaxAge(Integer.parseInt(buf));
		    DebugHandler.debug("Modifying AgeCategory Object " + selectedAgeCategoryObj);
		    ageCategoryIf.updateAgeCategory(selectedAgeCategoryObj);
		}
		else {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    AgeCategoryObject ageCategoryObj = new AgeCategoryObject();
		    buf = Util.trim(valuepairs.get(AppConstants.AGE_CATEGORY_STR));
		    ageCategoryObj.setAgeCategory(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.MIN_AGE_STR));
		    ageCategoryObj.setMinAge(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.MAX_AGE_STR));
		    ageCategoryObj.setMaxAge(Integer.parseInt(buf));
		    DebugHandler.debug("Adding AgeCategory Object " + ageCategoryObj);
		    ageCategoryIf.addAgeCategory(ageCategoryObj);
		}
	    }
	    else {
		String temp = System.getProperty("java.io.tmpdir");
		inputFileName = temp + File.separatorChar + inputFileName;
		readFromFile(inputFileName, null);
	    }
	}
    }

    public String getAgeCategoryInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	SelectElement se = null;
	InputElement ie = null;
	TextareaElement txt = null;
	AgeCategoryObject ageCategoryObj = ageCategoryIf.getAgeCategory(ageCategoryId);

	if ( ageCategoryObj == null )
	    ageCategoryObj = new AgeCategoryObject();
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.CURRENT_AGECATEGORY_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	ArrayList<String> nameArrayList = new ArrayList<String>();
	ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	AgeCategoryObject[] ageCategoryArr = ageCategoryIf.getAllAgeCategorys();
	nameArrayList.add(AppConstants.NEW_AGECATEGORY);
	valueArrayList.add(new Integer(0));
	for (int iterator = 0; iterator < ageCategoryArr.length; iterator++) {
		AgeCategoryObject ageCategoryObject = ageCategoryArr[iterator];
		if ( ageCategoryObject == null )
			break;
		nameArrayList.add(ageCategoryObject.getAgeCategory());
		valueArrayList.add(new Integer(ageCategoryObject.getAgeCategoryId()));
	}
	se = new SelectElement(AppConstants.AGE_CATEGORY_ID_STR, nameArrayList, valueArrayList, String.valueOf(ageCategoryId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.AGE_CATEGORY_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( ageCategoryId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.AGE_CATEGORY_STR, selectedAgeCategoryObj.getAgeCategory()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.AGE_CATEGORY_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.MIN_AGE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( ageCategoryId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MIN_AGE_STR, String.valueOf(selectedAgeCategoryObj.getMinAge())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MIN_AGE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.MAX_AGE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( ageCategoryId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MAX_AGE_STR, String.valueOf(selectedAgeCategoryObj.getMaxAge())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MAX_AGE_STR, Constants.EMPTY));
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
	cell.setCellValue(AppConstants.AGE_CATEGORY_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.AGE_CATEGORY_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.MIN_AGE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.MAX_AGE_LABEL);

	AgeCategoryInterface agecategoryIf = new AgeCategoryImpl();
	AgeCategoryObject[] agecategoryArr = agecategoryIf.getAllAgeCategorys();
	if ( agecategoryArr != null && agecategoryArr.length > 0 ) {
	    for (int iterator = 0; iterator < agecategoryArr.length; iterator++) {
		AgeCategoryObject agecategoryObj = agecategoryArr[iterator];
		if ( agecategoryObj == null )
		    break;
		rowNum++;
		col = 0;
		row = sheet.createRow((short)rowNum);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(agecategoryObj.getAgeCategoryId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue("INFO");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(agecategoryObj.getAgeCategory());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(agecategoryObj.getMinAge());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(agecategoryObj.getMaxAge());
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
	AgeCategoryInterface agecategoryIf = new AgeCategoryImpl();
	AgeCategoryObject agecategoryObject = new AgeCategoryObject();

	rowNum = 0;
	while ( true ) {
	    row = sheet.getRow(++rowNum);
	    if ( row == null )
		break;
	    agecategoryObject = new AgeCategoryObject();
	    cell = row.getCell((short)1);
	    if ( cell != null )
		dbOp = Util.trim(cell.getStringCellValue());
	    else
		dbOp = null;
	    DebugHandler.fine("DbOp = |" + dbOp + "|");
	    if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    agecategoryObject.setAgeCategoryId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		agecategoryObject = agecategoryIf.getAgeCategory(agecategoryObject.getAgeCategoryId());
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    agecategoryObject.setAgeCategory(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    agecategoryObject.setMinAge((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    agecategoryObject.setMinAge(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    agecategoryObject.setMaxAge((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    agecategoryObject.setMaxAge(0);
		}
		DebugHandler.fine("Updating AgeCategory " + agecategoryObject);
		agecategoryIf.updateAgeCategory(agecategoryObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    agecategoryObject.setAgeCategory(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    agecategoryObject.setMinAge((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    agecategoryObject.setMinAge(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    agecategoryObject.setMaxAge((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    agecategoryObject.setMaxAge(0);
		}
		DebugHandler.fine("Adding AgeCategory " + agecategoryObject);
		agecategoryIf.addAgeCategory(agecategoryObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    agecategoryObject.setAgeCategoryId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		agecategoryObject = agecategoryIf.getAgeCategory(agecategoryObject.getAgeCategoryId());
		agecategoryIf.deleteAgeCategory(agecategoryObject);
	    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
		throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
	    }
	}
    }
}
