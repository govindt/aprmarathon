/*
 *
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */
package app.appui;

import java.util.*;
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
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

public class MenuBean implements SpreadSheetInterface {
    public int menuId = 0;
    public int parentMenuId = 0;
    public int roleId = 0;
    MenuObject selectedMenuObj = new MenuObject();
    MenuInterface menuIf = new MenuImpl();

    public MenuBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	try {
	    menuId = Integer.parseInt(valuepairs.get(AppConstants.MENU_ID_STR));
	} catch (NumberFormatException nfe) {
	    menuId = 0;
	}
	try {
	    parentMenuId = Integer.parseInt(valuepairs.get(AppConstants.PARENT_MENU_ID_STR));
	} catch (NumberFormatException nfe) {
	    parentMenuId = 0;
	}
	try {
	    roleId = Integer.parseInt(valuepairs.get(AppConstants.ROLE_ID_STR));
	} catch (NumberFormatException nfe) {
	    roleId = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null ||
	     Boolean.valueOf(saveProfile).booleanValue() == false ) {
	    // This is to display the page
	    if ( menuId != 0 ) // Display the selected menu
		selectedMenuObj = menuIf.getMenu(menuId);
	}
	else {
	    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
	    if ( inputFileName == null ) {
		if ( menuId != 0 ) {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    buf = Util.trim(valuepairs.get(AppConstants.MENU_NAME_STR));
		    selectedMenuObj.setMenuName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.URL_STR));
		    selectedMenuObj.setUrl(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.MENU_ORDER_STR));
		    selectedMenuObj.setMenuOrder(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PARENT_MENU_ID_STR));
		    selectedMenuObj.setParentMenuId(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.ROLE_ID_STR));
		    selectedMenuObj.setRoleId(Integer.parseInt(buf));
		    DebugHandler.debug("Modifying Menu Object " + selectedMenuObj);
		    menuIf.updateMenu(selectedMenuObj);
		}
		else {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    MenuObject menuObj = new MenuObject();
		    buf = Util.trim(valuepairs.get(AppConstants.MENU_NAME_STR));
		    menuObj.setMenuName(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.URL_STR));
		    menuObj.setUrl(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.MENU_ORDER_STR));
		    menuObj.setMenuOrder(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PARENT_MENU_ID_STR));
		    menuObj.setParentMenuId(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.ROLE_ID_STR));
		    menuObj.setRoleId(Integer.parseInt(buf));
		    DebugHandler.debug("Adding Menu Object " + menuObj);
		    menuIf.addMenu(menuObj);
		}
	    }
	    else {
		String temp = System.getProperty("java.io.tmpdir");
		inputFileName = temp + File.separatorChar + inputFileName;
		DebugHandler.fine("readFromFile: " + inputFileName);
		readFromFile(inputFileName, null);
	    }
	}
    }

    public String getMenuInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	SelectElement se = null;
	InputElement ie = null;
	TextareaElement txt = null;
	MenuObject menuObj = menuIf.getMenu(menuId);

	if ( menuObj == null )
	    menuObj = new MenuObject();
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.CURRENT_MENU_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.addElement(td);
	Vector<String> nameVector = new Vector<String>();
	Vector<Integer> valueVector = new Vector<Integer>();
	MenuObject[] menuArr = menuIf.getAllMenus();
	nameVector.addElement(AppConstants.NEW_MENU);
	valueVector.addElement(new Integer(0));
	for (int iterator = 0; iterator < menuArr.length; iterator++) {
	    MenuObject menuObject = menuArr[iterator];
	    if ( menuObject == null )
		break;
	    nameVector.addElement(menuObject.getMenuName());
	    valueVector.addElement(new Integer(menuObject.getMenuId()));
	}
	se = new SelectElement(AppConstants.MENU_ID_STR, nameVector, valueVector, String.valueOf(menuId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.addElement(td);
	te.addElement(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.MENU_NAME_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.addElement(td);
	if ( menuId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MENU_NAME_STR, selectedMenuObj.getMenuName()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MENU_NAME_STR, Constants.EMPTY));
	tr.addElement(td);
	te.addElement(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.URL_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.addElement(td);
	if ( menuId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.URL_STR, selectedMenuObj.getUrl()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.URL_STR, Constants.EMPTY));
	tr.addElement(td);
	te.addElement(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.MENU_ORDER_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.addElement(td);
	if ( menuId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MENU_ORDER_STR, String.valueOf(selectedMenuObj.getMenuOrder())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MENU_ORDER_STR, Constants.EMPTY));
	tr.addElement(td);
	te.addElement(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PARENT_MENU_ID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.addElement(td);
	nameVector = new Vector<String>();
	valueVector = new Vector<Integer>();
	MenuInterface menuIf = new MenuImpl();
	MenuObject[] menuRefArr = menuIf.getAllMenus();
	for (int iterator = 0; iterator < menuRefArr.length; iterator++) {
	    MenuObject menuObject = menuRefArr[iterator];
	    if (menuObject == null)
		break;
	    nameVector.addElement(String.valueOf(menuObject.getMenuName()));
	    valueVector.addElement(new Integer(menuObject.getMenuId()));
	}
	if ( menuId != 0 )
		se = new SelectElement(AppConstants.PARENT_MENU_ID_STR, nameVector, valueVector, String.valueOf(selectedMenuObj.getParentMenuId()), 0);
	else
		se = new SelectElement(AppConstants.PARENT_MENU_ID_STR, nameVector, valueVector, String.valueOf(parentMenuId), 0);
	td = new TableDataElement(se);
	tr.addElement(td);
	te.addElement(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.ROLE_ID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.addElement(td);
	nameVector = new Vector<String>();
	valueVector = new Vector<Integer>();
	RoleInterface roleIf = new RoleImpl();
	RoleObject[] roleRefArr = roleIf.getAllRoles();
	for (int iterator = 0; iterator < roleRefArr.length; iterator++) {
	    RoleObject roleObject = roleRefArr[iterator];
	    if (roleObject == null)
		break;
	    nameVector.addElement(String.valueOf(roleObject.getRoleName()));
	    valueVector.addElement(new Integer(roleObject.getRoleId()));
	}
	if ( menuId != 0 )
		se = new SelectElement(AppConstants.ROLE_ID_STR, nameVector, valueVector, String.valueOf(selectedMenuObj.getRoleId()), 0);
	else
		se = new SelectElement(AppConstants.ROLE_ID_STR, nameVector, valueVector, String.valueOf(roleId), 0);
	td = new TableDataElement(se);
	tr.addElement(td);
	te.addElement(tr);


	tr = new TableRowElement();
	be = new BoldElement(Constants.UPLOAD_FILE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.addElement(td);

	ie = new InputElement(InputElement.FILE, Constants.UPLOAD_FILE_NAME_STR,"");
	td = new TableDataElement(ie);
	tr.addElement(td);
	te.addElement(tr);

	return te.getHTMLTag() + new BreakElement().getHTMLTag() +  new BreakElement().getHTMLTag() + UtilBean.getSubmitButton() + UtilBean.getDownloadButton();
    }

    public void writeToFile(String outputFileName, Object obj) throws AppException {
	DebugHandler.fine("writeToFile(" + outputFileName + "," + obj + ")");
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFFont font01Bold = wb.createFont();
	font01Bold.setFontHeightInPoints((short)12);
	font01Bold.setFontName("Times New Roman");
	font01Bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

	HSSFFont font01Normal = wb.createFont();
	font01Normal.setFontHeightInPoints((short)12);
	font01Normal.setFontName("Times New Roman");
	font01Normal.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

	// Create style
	HSSFCellStyle cellstyleTblHdr = wb.createCellStyle();
	cellstyleTblHdr.setFont(font01Bold);
	cellstyleTblHdr.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	cellstyleTblHdr.setWrapText(true);
	cellstyleTblHdr.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
	cellstyleTblHdr.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	cellstyleTblHdr.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	cellstyleTblHdr.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
	cellstyleTblHdr.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	cellstyleTblHdr.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
	cellstyleTblHdr.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	HSSFCellStyle cellstyleTblLeft = wb.createCellStyle();
	cellstyleTblLeft.setFont(font01Normal);
	cellstyleTblLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	cellstyleTblLeft.setWrapText(true);
	cellstyleTblLeft.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	cellstyleTblLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	cellstyleTblLeft.setBorderRight(HSSFCellStyle.BORDER_THIN);
	cellstyleTblLeft.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);

	HSSFSheet sheet = wb.createSheet();
	FileOutputStream fileOut = null;
	int rowNum = 0;
	int col = 0;
	HSSFRow row = null;
	HSSFCell cell = null;
	try {
	    fileOut = new FileOutputStream(outputFileName);
	} catch (FileNotFoundException fnf) {
	    throw new AppException("Unable to find file " + outputFileName);
	}
	row = sheet.createRow((short)rowNum);
	sheet.setColumnWidth((short)col, (short) (0));
	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.MENU_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.MENU_NAME_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.URL_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.MENU_ORDER_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PARENT_MENU_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.ROLE_ID_LABEL);

	MenuInterface menuIf = new MenuImpl();
	MenuObject[] menuArr = menuIf.getAllMenus();
	if ( menuArr != null && menuArr.length > 0 ) {
	    for (int iterator = 0; iterator < menuArr.length; iterator++) {
		MenuObject menuObj = menuArr[iterator];
		if ( menuObj == null )
		    break;
		rowNum++;
		col = 0;
		row = sheet.createRow((short)rowNum);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(menuObj.getMenuId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue("INFO");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(menuObj.getMenuName());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(menuObj.getUrl());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(menuObj.getMenuOrder());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(menuObj.getParentMenuId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(menuObj.getRoleId());
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
	POIFSFileSystem fs = null;
	HSSFWorkbook wb = null;
	try {
	    fs = new POIFSFileSystem(new FileInputStream(inputFileName));
	} catch (FileNotFoundException fnf) {
	    throw new AppException("Unable to find file " + inputFileName);
	} catch (IOException ioe) {
	    throw new AppException("IOException while opening file " + inputFileName);
	}
	try {
	    wb = new HSSFWorkbook(fs);
	} catch (IOException ioe) {
	    throw new AppException("IOException while getting workbook.");
	}
	HSSFSheet sheet = wb.getSheetAt(0);
	FileInputStream fileIn = null;
	try {
	    fileIn = new FileInputStream(inputFileName);
	} catch (FileNotFoundException fnf) {
	    throw new AppException("Unable to find file " + inputFileName);
	}
	int rowNum = 0;
	int col = 0;
	HSSFRow row = null;
	HSSFCell cell = null;
	String dbOp = null;
	MenuInterface menuIf = new MenuImpl();
	MenuObject menuObject = new MenuObject();

	rowNum = 0;
	while ( true ) {
	    row = sheet.getRow(++rowNum);
	    if ( row == null )
		break;
	    menuObject = new MenuObject();
	    cell = row.getCell((short)1);
	    if ( cell != null )
		dbOp = Util.trim(cell.getStringCellValue());
	    else
		dbOp = null;
	    DebugHandler.fine("DbOp = |" + dbOp + "|");
	    if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    menuObject.setMenuId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		menuObject = menuIf.getMenu(menuObject.getMenuId());
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    menuObject.setMenuName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    menuObject.setUrl(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    menuObject.setMenuOrder((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    menuObject.setMenuOrder(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    menuObject.setParentMenuId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    menuObject.setParentMenuId(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    menuObject.setRoleId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    menuObject.setRoleId(0);
		}
		DebugHandler.fine("Updating Menu " + menuObject);
		menuIf.updateMenu(menuObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    menuObject.setMenuName(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    menuObject.setUrl(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    menuObject.setMenuOrder((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    menuObject.setMenuOrder(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    menuObject.setParentMenuId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    menuObject.setParentMenuId(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    menuObject.setRoleId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    menuObject.setRoleId(0);
		}
		DebugHandler.fine("Adding Menu " + menuObject);
		menuIf.addMenu(menuObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    menuObject.setMenuId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		menuObject = menuIf.getMenu(menuObject.getMenuId());
		menuIf.deleteMenu(menuObject);
	    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
		throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
	    }
	}
    }
}
