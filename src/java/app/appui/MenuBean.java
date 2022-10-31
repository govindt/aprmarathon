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
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors; 

public class MenuBean implements SpreadSheetInterface {
    public int menuId = 0;
    public int parentMenuId = 0;
    public int roleId = 0;
	public int siteId = 0;
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
	try {
	    siteId = Integer.parseInt(valuepairs.get(AppConstants.SITE_ID_STR));
	} catch (NumberFormatException nfe) {
	    siteId = 0;
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
			buf = Util.trim(valuepairs.get(AppConstants.SITE_ID_STR));
		    selectedMenuObj.setSiteId(Integer.parseInt(buf));
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
			buf = Util.trim(valuepairs.get(AppConstants.SITE_ID_STR));
		    menuObj.setSiteId(Integer.parseInt(buf));
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
	tr.add(td);
	ArrayList<String> nameArrayList = new ArrayList<String>();
	ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	MenuObject[] menuArr = menuIf.getAllMenus();
	nameArrayList.add(AppConstants.NEW_MENU);
	valueArrayList.add(new Integer(0));
	for (int iterator = 0; iterator < menuArr.length; iterator++) {
	    MenuObject menuObject = menuArr[iterator];
	    if ( menuObject == null )
		break;
	    nameArrayList.add(menuObject.getMenuName());
	    valueArrayList.add(new Integer(menuObject.getMenuId()));
	}
	se = new SelectElement(AppConstants.MENU_ID_STR, nameArrayList, valueArrayList, String.valueOf(menuId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.MENU_NAME_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( menuId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MENU_NAME_STR, selectedMenuObj.getMenuName()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MENU_NAME_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);
	
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.SITE_ID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( menuId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.SITE_ID_STR, String.valueOf(selectedMenuObj.getSiteId())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.SITE_ID_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.URL_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( menuId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.URL_STR, selectedMenuObj.getUrl()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.URL_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.MENU_ORDER_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( menuId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MENU_ORDER_STR, String.valueOf(selectedMenuObj.getMenuOrder())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.MENU_ORDER_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PARENT_MENU_ID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	MenuInterface menuIf = new MenuImpl();
	MenuObject[] menuRefArr = menuIf.getAllMenus();
	for (int iterator = 0; iterator < menuRefArr.length; iterator++) {
	    MenuObject menuObject = menuRefArr[iterator];
	    if (menuObject == null)
		break;
	    nameArrayList.add(String.valueOf(menuObject.getMenuName()));
	    valueArrayList.add(new Integer(menuObject.getMenuId()));
	}
	if ( menuId != 0 )
		se = new SelectElement(AppConstants.PARENT_MENU_ID_STR, nameArrayList, valueArrayList, String.valueOf(selectedMenuObj.getParentMenuId()), 0);
	else
		se = new SelectElement(AppConstants.PARENT_MENU_ID_STR, nameArrayList, valueArrayList, String.valueOf(parentMenuId), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.ROLE_ID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	RoleInterface roleIf = new RoleImpl();
	RoleObject[] roleRefArr = roleIf.getAllRoles();
	for (int iterator = 0; iterator < roleRefArr.length; iterator++) {
	    RoleObject roleObject = roleRefArr[iterator];
	    if (roleObject == null)
		break;
	    nameArrayList.add(String.valueOf(roleObject.getRoleName()));
	    valueArrayList.add(new Integer(roleObject.getRoleId()));
	}
	if ( menuId != 0 )
		se = new SelectElement(AppConstants.ROLE_ID_STR, nameArrayList, valueArrayList, String.valueOf(selectedMenuObj.getRoleId()), 0);
	else
		se = new SelectElement(AppConstants.ROLE_ID_STR, nameArrayList, valueArrayList, String.valueOf(roleId), 0);
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
	cell.setCellValue(AppConstants.MENU_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.MENU_NAME_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.SITE_ID_LABEL);

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
		cell.setCellValue(menuObj.getSiteId());

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
	InputStream fs = null;
	XSSFWorkbook wb = null;
	try {
	    fs = new FileInputStream(inputFileName);
	} catch (FileNotFoundException fnf) {
	    throw new AppException("Unable to find file " + inputFileName);
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
		try {
		    menuObject.setSiteId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    menuObject.setSiteId(0);
		}
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
		try {
		    menuObject.setSiteId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    menuObject.setSiteId(0);
		}
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
