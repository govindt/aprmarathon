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

public class ParticipantEventBean implements SpreadSheetInterface {
    public int participantEventId = 0;
    public int participantId = 0;
    public int participantEvent = 0;
    public int participantType = 0;
    public int participantEventType = 0;
    public int participantGroup = 0;
    ParticipantEventObject selectedParticipantEventObj = new ParticipantEventObject();
    ParticipantEventInterface participantEventIf = new ParticipantEventImpl();

    public ParticipantEventBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
		HttpSession session = request.getSession();
		if (session == null)
			throw new NullPointerException();
		@SuppressWarnings("unchecked")
		Hashtable<String,String> valuepairs =
			(Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
		try {
			participantEventId = Integer.parseInt(valuepairs.get(AppConstants.PARTICIPANT_EVENT_ID_STR));
		} catch (NumberFormatException nfe) {
			participantEventId = 0;
		}
		try {
			participantId = Integer.parseInt(valuepairs.get(AppConstants.PARTICIPANT_ID_STR));
		} catch (NumberFormatException nfe) {
			participantId = 0;
		}
		try {
			participantEvent = Integer.parseInt(valuepairs.get(AppConstants.PARTICIPANT_EVENT_STR));
		} catch (NumberFormatException nfe) {
			participantEvent = 0;
		}
		try {
			participantType = Integer.parseInt(valuepairs.get(AppConstants.PARTICIPANT_TYPE_STR));
		} catch (NumberFormatException nfe) {
			participantType = 0;
		}
		try {
			participantEventType = Integer.parseInt(valuepairs.get(AppConstants.PARTICIPANT_EVENT_TYPE_STR));
		} catch (NumberFormatException nfe) {
			participantEventType = 0;
		}
		try {
			participantGroup = Integer.parseInt(valuepairs.get(AppConstants.PARTICIPANT_GROUP_STR));
		} catch (NumberFormatException nfe) {
			participantGroup = 0;
		}
		String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
		if ( saveProfile == null ||
			 Boolean.valueOf(saveProfile).booleanValue() == false ) {
			// This is to display the page
			if ( participantEventId != 0 ) // Display the selected participantevent
				selectedParticipantEventObj = participantEventIf.getParticipantEvent(participantEventId);
		}
		else {
			String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
			if ( inputFileName == null ) {
				if ( participantEventId != 0 ) {
					String buf = "";
					Date date = null;
					SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_ID_STR));
					selectedParticipantEventObj.setParticipantId(Integer.parseInt(buf));
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_EVENT_STR));
					selectedParticipantEventObj.setParticipantEvent(Integer.parseInt(buf));
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_TYPE_STR));
					selectedParticipantEventObj.setParticipantType(Integer.parseInt(buf));
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_EVENT_TYPE_STR));
					selectedParticipantEventObj.setParticipantEventType(Integer.parseInt(buf));
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_BIB_NO_STR));
					selectedParticipantEventObj.setParticipantBibNo(buf);
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_GROUP_STR));
					selectedParticipantEventObj.setParticipantGroup(Integer.parseInt(buf));
					DebugHandler.debug("Modifying ParticipantEvent Object " + selectedParticipantEventObj);
					participantEventIf.updateParticipantEvent(selectedParticipantEventObj);
				}
				else {
					String buf = "";
					Date date = null;
					SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
					ParticipantEventObject participantEventObj = new ParticipantEventObject();
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_ID_STR));
					participantEventObj.setParticipantId(Integer.parseInt(buf));
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_EVENT_STR));
					participantEventObj.setParticipantEvent(Integer.parseInt(buf));
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_TYPE_STR));
					participantEventObj.setParticipantType(Integer.parseInt(buf));
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_EVENT_TYPE_STR));
					participantEventObj.setParticipantEventType(Integer.parseInt(buf));
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_BIB_NO_STR));
					participantEventObj.setParticipantBibNo(buf);
					buf = Util.trim(valuepairs.get(AppConstants.PARTICIPANT_GROUP_STR));
					participantEventObj.setParticipantGroup(Integer.parseInt(buf));
					DebugHandler.debug("Adding ParticipantEvent Object " + participantEventObj);
					participantEventIf.addParticipantEvent(participantEventObj);
				}
			}
			else {
				String temp = System.getProperty("java.io.tmpdir");
				inputFileName = temp + File.separatorChar + inputFileName;
				readFromFile(inputFileName, null);
			}
		}
    }

    public String getParticipantEventInfo() throws AppException {
		TableElement te = new TableElement();
		te.setClass(Constants.BODY_TABLE_STYLE);
		TableRowElement tr = null;
		TableDataElement td = null;
		BoldElement be = null;
		SelectElement se = null;
		InputElement ie = null;
		TextareaElement txt = null;
		ParticipantEventObject participantEventObj = participantEventIf.getParticipantEvent(participantEventId);

		if ( participantEventObj == null )
			participantEventObj = new ParticipantEventObject();
		tr = new TableRowElement();
		be = new BoldElement(AppConstants.CURRENT_PARTICIPANTEVENT_LABEL);
		be.setId(Constants.BODY_ROW_STYLE);
		td = new TableDataElement(be);
		tr.add(td);
		ArrayList<String> nameArrayList = new ArrayList<String>();
		ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
		ParticipantEventObject[] participantEventArr = participantEventIf.getAllParticipantEvents();
		nameArrayList.add(AppConstants.NEW_PARTICIPANTEVENT);
		valueArrayList.add(new Integer(0));
		for (int iterator = 0; iterator < participantEventArr.length; iterator++) {
			ParticipantEventObject participantEventObject = participantEventArr[iterator];
			if ( participantEventObject == null )
				break;
			nameArrayList.add(String.valueOf(participantEventObject.getParticipantId()));
			valueArrayList.add(new Integer(participantEventObject.getParticipantEventId()));
		}
		se = new SelectElement(AppConstants.PARTICIPANT_EVENT_ID_STR, nameArrayList, valueArrayList, String.valueOf(participantEventId), 0);
		se.setOnChange(UtilBean.JS_SUBMIT_FORM);
		td = new TableDataElement(se);
		tr.add(td);
		te.add(tr);

		tr = new TableRowElement();
		be = new BoldElement(AppConstants.PARTICIPANT_ID_LABEL);
		be.setId(Constants.BODY_ROW_STYLE);
		td = new TableDataElement(be);
		tr.add(td);
		nameArrayList = new ArrayList<String>();
		valueArrayList = new ArrayList<Integer>();
		ParticipantInterface participantIf = new ParticipantImpl();
		ParticipantObject[] participantRefArr = participantIf.getAllParticipants();
		for (int iterator = 0; iterator < participantRefArr.length; iterator++) {
			ParticipantObject participantObject = participantRefArr[iterator];
			if (participantObject == null)
				break;
			nameArrayList.add(String.valueOf(participantObject.getParticipantFirstName()));
			valueArrayList.add(new Integer(participantObject.getParticipantId()));
		}
		if ( participantEventId != 0 )
			se = new SelectElement(AppConstants.PARTICIPANT_ID_STR, nameArrayList, valueArrayList, String.valueOf(selectedParticipantEventObj.getParticipantId()), 0);
		else
			se = new SelectElement(AppConstants.PARTICIPANT_ID_STR, nameArrayList, valueArrayList, String.valueOf(participantId), 0);
		td = new TableDataElement(se);
		tr.add(td);
		te.add(tr);

		tr = new TableRowElement();
		be = new BoldElement(AppConstants.PARTICIPANT_EVENT_LABEL);
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
		if ( participantEventId != 0 )
			se = new SelectElement(AppConstants.PARTICIPANT_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(selectedParticipantEventObj.getParticipantEvent()), 0);
		else
			se = new SelectElement(AppConstants.PARTICIPANT_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(participantEvent), 0);
		td = new TableDataElement(se);
		tr.add(td);
		te.add(tr);

		tr = new TableRowElement();
		be = new BoldElement(AppConstants.PARTICIPANT_TYPE_LABEL);
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
		if ( participantEventId != 0 )
			se = new SelectElement(AppConstants.PARTICIPANT_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(selectedParticipantEventObj.getParticipantType()), 0);
		else
			se = new SelectElement(AppConstants.PARTICIPANT_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(participantType), 0);
		td = new TableDataElement(se);
		tr.add(td);
		te.add(tr);

		tr = new TableRowElement();
		be = new BoldElement(AppConstants.PARTICIPANT_EVENT_TYPE_LABEL);
		be.setId(Constants.BODY_ROW_STYLE);
		td = new TableDataElement(be);
		tr.add(td);
		nameArrayList = new ArrayList<String>();
		valueArrayList = new ArrayList<Integer>();
		EventTypeInterface eventtypeIf = new EventTypeImpl();
		EventTypeObject[] eventtypeRefArr = eventtypeIf.getAllEventTypes();
		for (int iterator = 0; iterator < eventtypeRefArr.length; iterator++) {
			EventTypeObject eventtypeObject = eventtypeRefArr[iterator];
			if (eventtypeObject == null)
			break;
			nameArrayList.add(String.valueOf(eventtypeObject.getEventTypeName()));
			valueArrayList.add(new Integer(eventtypeObject.getEventTypeId()));
		}
		if ( participantEventId != 0 )
			se = new SelectElement(AppConstants.PARTICIPANT_EVENT_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(selectedParticipantEventObj.getParticipantEventType()), 0);
		else
			se = new SelectElement(AppConstants.PARTICIPANT_EVENT_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(participantEventType), 0);
		td = new TableDataElement(se);
		tr.add(td);
		te.add(tr);

		tr = new TableRowElement();
		be = new BoldElement(AppConstants.PARTICIPANT_BIB_NO_LABEL);
		be.setId(Constants.BODY_ROW_STYLE);
		td = new TableDataElement(be);
		tr.add(td);
		if ( participantEventId != 0 )
			td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PARTICIPANT_BIB_NO_STR, selectedParticipantEventObj.getParticipantBibNo()));
		else
			td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.PARTICIPANT_BIB_NO_STR, Constants.EMPTY));
		tr.add(td);
		te.add(tr);

		tr = new TableRowElement();
		be = new BoldElement(AppConstants.PARTICIPANT_GROUP_LABEL);
		be.setId(Constants.BODY_ROW_STYLE);
		td = new TableDataElement(be);
		tr.add(td);
		nameArrayList = new ArrayList<String>();
		valueArrayList = new ArrayList<Integer>();
		RegistrantEventInterface registrant_eventIf = new RegistrantEventImpl();
		RegistrantEventObject[] registrant_eventRefArr = registrant_eventIf.getAllRegistrantEvents();
		for (int iterator = 0; iterator < registrant_eventRefArr.length; iterator++) {
			RegistrantEventObject registrant_eventObject = registrant_eventRefArr[iterator];
			if (registrant_eventObject == null)
				break;
			nameArrayList.add(String.valueOf(registrant_eventObject.getRegistrantId()));
			valueArrayList.add(new Integer(registrant_eventObject.getRegistrantEventId()));
		}
		if ( participantEventId != 0 )
			se = new SelectElement(AppConstants.PARTICIPANT_GROUP_STR, nameArrayList, valueArrayList, String.valueOf(selectedParticipantEventObj.getParticipantGroup()), 0);
		else
			se = new SelectElement(AppConstants.PARTICIPANT_GROUP_STR, nameArrayList, valueArrayList, String.valueOf(participantGroup), 0);
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
		cell.setCellValue(AppConstants.PARTICIPANT_EVENT_ID_LABEL);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblHdr);
		cell.setCellValue("DB Operation");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblHdr);
		cell.setCellValue(AppConstants.PARTICIPANT_ID_LABEL);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblHdr);
		cell.setCellValue(AppConstants.PARTICIPANT_EVENT_LABEL);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblHdr);
		cell.setCellValue(AppConstants.PARTICIPANT_TYPE_LABEL);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblHdr);
		cell.setCellValue(AppConstants.PARTICIPANT_EVENT_TYPE_LABEL);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblHdr);
		cell.setCellValue(AppConstants.PARTICIPANT_BIB_NO_LABEL);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblHdr);
		cell.setCellValue(AppConstants.PARTICIPANT_GROUP_LABEL);

		ParticipantEventInterface participanteventIf = new ParticipantEventImpl();
		ParticipantEventObject[] participanteventArr = participanteventIf.getAllParticipantEvents();
		if ( participanteventArr != null && participanteventArr.length > 0 ) {
			for (int iterator = 0; iterator < participanteventArr.length; iterator++) {
				ParticipantEventObject participanteventObj = participanteventArr[iterator];
				if ( participanteventObj == null )
					break;
				rowNum++;
				col = 0;
				row = sheet.createRow((short)rowNum);

				cell = row.createCell((short)col++);
				cell.setCellStyle(cellstyleTblLeft);
				cell.setCellValue(participanteventObj.getParticipantEventId());

				cell = row.createCell((short)col++);
				cell.setCellStyle(cellstyleTblLeft);
				cell.setCellValue("INFO");

				cell = row.createCell((short)col++);
				cell.setCellStyle(cellstyleTblLeft);
				cell.setCellValue(participanteventObj.getParticipantId());

				cell = row.createCell((short)col++);
				cell.setCellStyle(cellstyleTblLeft);
				cell.setCellValue(participanteventObj.getParticipantEvent());

				cell = row.createCell((short)col++);
				cell.setCellStyle(cellstyleTblLeft);
				cell.setCellValue(participanteventObj.getParticipantType());

				cell = row.createCell((short)col++);
				cell.setCellStyle(cellstyleTblLeft);
				cell.setCellValue(participanteventObj.getParticipantEventType());

				cell = row.createCell((short)col++);
				cell.setCellStyle(cellstyleTblLeft);
				cell.setCellValue(participanteventObj.getParticipantBibNo());

				cell = row.createCell((short)col++);
				cell.setCellStyle(cellstyleTblLeft);
				cell.setCellValue(participanteventObj.getParticipantGroup());
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
		ParticipantEventInterface participanteventIf = new ParticipantEventImpl();
		ParticipantEventObject participanteventObject = new ParticipantEventObject();

		rowNum = 0;
		while ( true ) {
			row = sheet.getRow(++rowNum);
			if ( row == null )
			break;
			participanteventObject = new ParticipantEventObject();
			cell = row.getCell((short)1);
			if ( cell != null )
				dbOp = Util.trim(cell.getStringCellValue());
			else
				dbOp = null;
			DebugHandler.fine("DbOp = |" + dbOp + "|");
			if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
				cell = row.getCell((short)0); // Get the first column
			try {
				participanteventObject.setParticipantEventId((int)cell.getNumericCellValue());
			} catch (NumberFormatException nfe) {
				throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
			}
			participanteventObject = participanteventIf.getParticipantEvent(participanteventObject.getParticipantEventId());
			col = 2; // Starting from 3rd Column
			cell = row.getCell((short)col++);
			if ( cell != null )
			try {
				participanteventObject.setParticipantId((int)cell.getNumericCellValue());
			} catch (NumberFormatException nfe) {
				participanteventObject.setParticipantId(0);
			}
			cell = row.getCell((short)col++);
			if ( cell != null )
			try {
				participanteventObject.setParticipantEvent((int)cell.getNumericCellValue());
			} catch (NumberFormatException nfe) {
				participanteventObject.setParticipantEvent(0);
			}
			cell = row.getCell((short)col++);
			if ( cell != null )
			try {
				participanteventObject.setParticipantType((int)cell.getNumericCellValue());
			} catch (NumberFormatException nfe) {
				participanteventObject.setParticipantType(0);
			}
			cell = row.getCell((short)col++);
			if ( cell != null )
			try {
				participanteventObject.setParticipantEventType((int)cell.getNumericCellValue());
			} catch (NumberFormatException nfe) {
				participanteventObject.setParticipantEventType(0);
			}
			cell = row.getCell((short)col++);
			if ( cell != null )
				participanteventObject.setParticipantBibNo(Util.trim(cell.getStringCellValue()));
			cell = row.getCell((short)col++);
			if ( cell != null )
			try {
				participanteventObject.setParticipantGroup((int)cell.getNumericCellValue());
			} catch (NumberFormatException nfe) {
				participanteventObject.setParticipantGroup(0);
			}
			DebugHandler.fine("Updating ParticipantEvent " + participanteventObject);
			participanteventIf.updateParticipantEvent(participanteventObject);
			} else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
			col = 2; // Starting from 3rd Column
			cell = row.getCell((short)col++);
			if ( cell != null )
			try {
				participanteventObject.setParticipantId((int)cell.getNumericCellValue());
			} catch (NumberFormatException nfe) {
				participanteventObject.setParticipantId(0);
			}
			cell = row.getCell((short)col++);
			if ( cell != null )
			try {
				participanteventObject.setParticipantEvent((int)cell.getNumericCellValue());
			} catch (NumberFormatException nfe) {
				participanteventObject.setParticipantEvent(0);
			}
			cell = row.getCell((short)col++);
			if ( cell != null )
			try {
				participanteventObject.setParticipantType((int)cell.getNumericCellValue());
			} catch (NumberFormatException nfe) {
				participanteventObject.setParticipantType(0);
			}
			cell = row.getCell((short)col++);
			if ( cell != null )
			try {
				participanteventObject.setParticipantEventType((int)cell.getNumericCellValue());
			} catch (NumberFormatException nfe) {
				participanteventObject.setParticipantEventType(0);
			}
			cell = row.getCell((short)col++);
			if ( cell != null )
				participanteventObject.setParticipantBibNo(Util.trim(cell.getStringCellValue()));
			cell = row.getCell((short)col++);
			if ( cell != null )
			try {
				participanteventObject.setParticipantGroup((int)cell.getNumericCellValue());
			} catch (NumberFormatException nfe) {
				participanteventObject.setParticipantGroup(0);
			}
			DebugHandler.fine("Adding ParticipantEvent " + participanteventObject);
			participanteventIf.addParticipantEvent(participanteventObject);
			} else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
				cell = row.getCell((short)0); // Get the first column
				try {
					participanteventObject.setParticipantEventId((int)cell.getNumericCellValue());
				} catch (NumberFormatException nfe) {
					throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
				}
				participanteventObject = participanteventIf.getParticipantEvent(participanteventObject.getParticipantEventId());
				participanteventIf.deleteParticipantEvent(participanteventObject);
			} else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
				throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
			}
		}
    }
}
