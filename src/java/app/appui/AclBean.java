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

public class AclBean implements SpreadSheetInterface {
    public int aclId = 0;
    public int roleId = 0;
    public int usersId = 0;
    AclObject selectedAclObj = new AclObject();
    AclInterface aclIf = new AclImpl();

    public AclBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	try {
	    aclId = Integer.parseInt(valuepairs.get(AppConstants.ACL_ID_STR));
	} catch (NumberFormatException nfe) {
	    aclId = 0;
	}
	try {
	    roleId = Integer.parseInt(valuepairs.get(AppConstants.ROLE_ID_STR));
	} catch (NumberFormatException nfe) {
	    roleId = 0;
	}
	try {
	    usersId = Integer.parseInt(valuepairs.get(AppConstants.USERS_ID_STR));
	} catch (NumberFormatException nfe) {
	    usersId = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null ||
	     Boolean.valueOf(saveProfile).booleanValue() == false ) {
	    // This is to display the page
	    if ( aclId != 0 ) // Display the selected acl
		selectedAclObj = aclIf.getAcl(aclId);
	}
	else {
	    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
	    if ( inputFileName == null ) {
		if ( aclId != 0 ) {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    buf = Util.trim(valuepairs.get(AppConstants.ACL_PAGE_STR));
		    selectedAclObj.setAclPage(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.IS_VALID_STR));
		    selectedAclObj.setIsValid(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.ROLE_ID_STR));
		    selectedAclObj.setRoleId(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.USERS_ID_STR));
		    selectedAclObj.setUsersId(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PERMISSION_STR));
		    selectedAclObj.setPermission(Integer.parseInt(buf));
		    DebugHandler.debug("Modifying Acl Object " + selectedAclObj);
		    aclIf.updateAcl(selectedAclObj);
		}
		else {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    AclObject aclObj = new AclObject();
		    buf = Util.trim(valuepairs.get(AppConstants.ACL_PAGE_STR));
		    aclObj.setAclPage(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.IS_VALID_STR));
		    aclObj.setIsValid(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.ROLE_ID_STR));
		    aclObj.setRoleId(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.USERS_ID_STR));
		    aclObj.setUsersId(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.PERMISSION_STR));
		    aclObj.setPermission(Integer.parseInt(buf));
		    DebugHandler.debug("Adding Acl Object " + aclObj);
		    aclIf.addAcl(aclObj);
		}
	    }
	    else {
		String temp = System.getProperty("java.io.tmpdir");
		inputFileName = temp + File.separatorChar + inputFileName;
		readFromFile(inputFileName, null);
	    }
	}
    }

    public String getAclInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	SelectElement se = null;
	InputElement ie = null;
	TextareaElement txt = null;
	AclObject aclObj = aclIf.getAcl(aclId);

	if ( aclObj == null )
	    aclObj = new AclObject();
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.CURRENT_ACL_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	ArrayList<String> nameArrayList = new ArrayList<String>();
	ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	AclObject[] aclArr = aclIf.getAllAcls();
	nameArrayList.add(AppConstants.NEW_ACL);
	valueArrayList.add(new Integer(0));
	for (int iterator = 0; iterator < aclArr.length; iterator++) {
	    AclObject aclObject = aclArr[iterator];
	    if ( aclObject == null )
		break;
	    nameArrayList.add(aclObject.getAclPage());
	    valueArrayList.add(new Integer(aclObject.getAclId()));
	}
	se = new SelectElement(AppConstants.ACL_ID_STR, nameArrayList, valueArrayList, String.valueOf(aclId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.ACL_PAGE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( aclId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.ACL_PAGE_STR, selectedAclObj.getAclPage()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.ACL_PAGE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(Constants.IS_VALID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( aclId != 0 )
	    td = new TableDataElement(new StringElement(UtilBean.getYesNoRadio(Constants.IS_VALID_STR, selectedAclObj.getIsValid(), Constants.EMPTY)));
	else
	    td = new TableDataElement(new StringElement(UtilBean.getYesNoRadio(Constants.IS_VALID_STR, Constants.YES_STR, Constants.EMPTY)));
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
	se = new SelectElement(AppConstants.ROLE_ID_STR, nameArrayList, valueArrayList, String.valueOf(roleId), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.USERS_ID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	UsersInterface usersIf = new UsersImpl();
	UsersObject[] usersRefArr = usersIf.getAllUsers();
	for (int iterator = 0; iterator < usersRefArr.length; iterator++) {
	    UsersObject usersObject = usersRefArr[iterator];
	    if (usersObject == null)
		break;
	    nameArrayList.add(String.valueOf(usersObject.getUsername()));
	    valueArrayList.add(new Integer(usersObject.getUsersId()));
	}
	se = new SelectElement(AppConstants.USERS_ID_STR, nameArrayList, valueArrayList, String.valueOf(usersId), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PERMISSION_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( aclId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PERMISSION_STR, String.valueOf(selectedAclObj.getPermission())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PERMISSION_STR, Constants.EMPTY));
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
	cell.setCellValue(AppConstants.ACL_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.ACL_PAGE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.IS_VALID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.ROLE_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.USERS_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.PERMISSION_LABEL);

	AclInterface aclIf = new AclImpl();
	AclObject[] aclArr = aclIf.getAllAcls();
	if ( aclArr != null && aclArr.length > 0 ) {
	    for (int iterator = 0; iterator < aclArr.length; iterator++) {
		AclObject aclObj = aclArr[iterator];
		if ( aclObj == null )
		    break;
		rowNum++;
		col = 0;
		row = sheet.createRow((short)rowNum);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(aclObj.getAclId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue("INFO");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(aclObj.getAclPage());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(aclObj.getIsValid());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(aclObj.getRoleId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(aclObj.getUsersId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(aclObj.getPermission());
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
	AclInterface aclIf = new AclImpl();
	AclObject aclObject = new AclObject();

	rowNum = 0;
	while ( true ) {
	    row = sheet.getRow(++rowNum);
	    if ( row == null )
		break;
	    aclObject = new AclObject();
	    cell = row.getCell((short)1);
	    if ( cell != null )
		dbOp = Util.trim(cell.getStringCellValue());
	    else
		dbOp = null;
	    DebugHandler.fine("DbOp = |" + dbOp + "|");
	    if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    aclObject.setAclId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		aclObject = aclIf.getAcl(aclObject.getAclId());
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    aclObject.setAclPage(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    aclObject.setIsValid(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    aclObject.setRoleId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    aclObject.setRoleId(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    aclObject.setUsersId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    aclObject.setUsersId(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    aclObject.setPermission((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    aclObject.setPermission(0);
		}
		DebugHandler.fine("Updating Acl " + aclObject);
		aclIf.updateAcl(aclObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		    aclObject.setAclPage(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    aclObject.setIsValid(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    aclObject.setRoleId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    aclObject.setRoleId(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    aclObject.setUsersId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    aclObject.setUsersId(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    aclObject.setPermission((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    aclObject.setPermission(0);
		}
		DebugHandler.fine("Adding Acl " + aclObject);
		aclIf.addAcl(aclObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    aclObject.setAclId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		aclObject = aclIf.getAcl(aclObject.getAclId());
		aclObject.setIsValid(Constants.NO_STR);
		aclIf.updateAcl(aclObject);
	    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
		throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
	    }
	}
    }
}
