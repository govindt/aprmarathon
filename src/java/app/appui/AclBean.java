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
	tr.addElement(td);
	Vector<String> nameVector = new Vector<String>();
	Vector<Integer> valueVector = new Vector<Integer>();
	AclObject[] aclArr = aclIf.getAllAcls();
	nameVector.addElement(AppConstants.NEW_ACL);
	valueVector.addElement(new Integer(0));
	for (int iterator = 0; iterator < aclArr.length; iterator++) {
	    AclObject aclObject = aclArr[iterator];
	    if ( aclObject == null )
		break;
	    nameVector.addElement(aclObject.getAclPage());
	    valueVector.addElement(new Integer(aclObject.getAclId()));
	}
	se = new SelectElement(AppConstants.ACL_ID_STR, nameVector, valueVector, String.valueOf(aclId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.addElement(td);
	te.addElement(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.ACL_PAGE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.addElement(td);
	if ( aclId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.ACL_PAGE_STR, selectedAclObj.getAclPage()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.ACL_PAGE_STR, Constants.EMPTY));
	tr.addElement(td);
	te.addElement(tr);

	tr = new TableRowElement();
	be = new BoldElement(Constants.IS_VALID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.addElement(td);
	if ( aclId != 0 )
	    td = new TableDataElement(new StringElement(UtilBean.getYesNoRadio(Constants.IS_VALID_STR, selectedAclObj.getIsValid(), Constants.EMPTY)));
	else
	    td = new TableDataElement(new StringElement(UtilBean.getYesNoRadio(Constants.IS_VALID_STR, Constants.YES_STR, Constants.EMPTY)));
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
	se = new SelectElement(AppConstants.ROLE_ID_STR, nameVector, valueVector, String.valueOf(roleId), 0);
	td = new TableDataElement(se);
	tr.addElement(td);
	te.addElement(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.USERS_ID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.addElement(td);
	nameVector = new Vector<String>();
	valueVector = new Vector<Integer>();
	UsersInterface usersIf = new UsersImpl();
	UsersObject[] usersRefArr = usersIf.getAllUsers();
	for (int iterator = 0; iterator < usersRefArr.length; iterator++) {
	    UsersObject usersObject = usersRefArr[iterator];
	    if (usersObject == null)
		break;
	    nameVector.addElement(String.valueOf(usersObject.getUsername()));
	    valueVector.addElement(new Integer(usersObject.getUsersId()));
	}
	se = new SelectElement(AppConstants.USERS_ID_STR, nameVector, valueVector, String.valueOf(usersId), 0);
	td = new TableDataElement(se);
	tr.addElement(td);
	te.addElement(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PERMISSION_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.addElement(td);
	if ( aclId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PERMISSION_STR, String.valueOf(selectedAclObj.getPermission())));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PERMISSION_STR, Constants.EMPTY));
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
