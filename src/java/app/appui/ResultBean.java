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

public class ResultBean implements SpreadSheetInterface {
    public int resultId = 0;
    public int resultEvent = 0;
    public int resultEventType = 0;
    public int resultMedal = 0;
    public int resultWinner = 0;
    public int resultWinnerRegistrant = 0;
    ResultObject selectedResultObj = new ResultObject();
    ResultInterface resultIf = new ResultImpl();

    public ResultBean() {}

    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	try {
	    resultId = Integer.parseInt(valuepairs.get(AppConstants.RESULT_ID_STR));
	} catch (NumberFormatException nfe) {
	    resultId = 0;
	}
	try {
	    resultEvent = Integer.parseInt(valuepairs.get(AppConstants.RESULT_EVENT_STR));
	} catch (NumberFormatException nfe) {
	    resultEvent = 0;
	}
	try {
	    resultEventType = Integer.parseInt(valuepairs.get(AppConstants.RESULT_EVENT_TYPE_STR));
	} catch (NumberFormatException nfe) {
	    resultEventType = 0;
	}
	try {
	    resultMedal = Integer.parseInt(valuepairs.get(AppConstants.RESULT_MEDAL_STR));
	} catch (NumberFormatException nfe) {
	    resultMedal = 0;
	}
	try {
	    resultWinner = Integer.parseInt(valuepairs.get(AppConstants.RESULT_WINNER_STR));
	} catch (NumberFormatException nfe) {
	    resultWinner = 0;
	}
	try {
	    resultWinnerRegistrant = Integer.parseInt(valuepairs.get(AppConstants.RESULT_WINNER_REGISTRANT_STR));
	} catch (NumberFormatException nfe) {
	    resultWinnerRegistrant = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null ||
	     Boolean.valueOf(saveProfile).booleanValue() == false ) {
	    // This is to display the page
	    if ( resultId != 0 ) // Display the selected result
		selectedResultObj = resultIf.getResult(resultId);
	}
	else {
	    String inputFileName = valuepairs.get(Constants.UPLOAD_FILE_NAME_STR);
	    if ( inputFileName == null ) {
		if ( resultId != 0 ) {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_EVENT_STR));
		    selectedResultObj.setResultEvent(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_EVENT_TYPE_STR));
		    selectedResultObj.setResultEventType(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_MEDAL_STR));
		    selectedResultObj.setResultMedal(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_WINNER_STR));
		    selectedResultObj.setResultWinner(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_WINNER_REGISTRANT_STR));
		    selectedResultObj.setResultWinnerRegistrant(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_SCORE_STR));
		    selectedResultObj.setResultScore(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_TIMING_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    selectedResultObj.setResultTiming(date);
		    DebugHandler.debug("Modifying Result Object " + selectedResultObj);
		    resultIf.updateResult(selectedResultObj);
		}
		else {
		    String buf = "";
		    Date date = null;
		    SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		    ResultObject resultObj = new ResultObject();
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_EVENT_STR));
		    resultObj.setResultEvent(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_EVENT_TYPE_STR));
		    resultObj.setResultEventType(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_MEDAL_STR));
		    resultObj.setResultMedal(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_WINNER_STR));
		    resultObj.setResultWinner(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_WINNER_REGISTRANT_STR));
		    resultObj.setResultWinnerRegistrant(Integer.parseInt(buf));
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_SCORE_STR));
		    resultObj.setResultScore(buf);
		    buf = Util.trim(valuepairs.get(AppConstants.RESULT_TIMING_STR));
		    try {
			date = dateFormatter.parse(buf);
		    } catch (java.text.ParseException pe) {
			throw new AppException("Parse Exception while parsing " + buf);
		    }
		    resultObj.setResultTiming(date);
		    DebugHandler.debug("Adding Result Object " + resultObj);
		    resultIf.addResult(resultObj);
		}
	    }
	    else {
		String temp = System.getProperty("java.io.tmpdir");
		inputFileName = temp + File.separatorChar + inputFileName;
		readFromFile(inputFileName, null);
	    }
	}
    }

    public String getResultInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	SelectElement se = null;
	InputElement ie = null;
	TextareaElement txt = null;
	ResultObject resultObj = resultIf.getResult(resultId);

	if ( resultObj == null )
	    resultObj = new ResultObject();
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.CURRENT_RESULT_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	ArrayList<String> nameArrayList = new ArrayList<String>();
	ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	ResultObject[] resultArr = resultIf.getAllResults();
	nameArrayList.add(AppConstants.NEW_RESULT);
	valueArrayList.add(new Integer(0));
	for (int iterator = 0; iterator < resultArr.length; iterator++) {
		ResultObject resultObject = resultArr[iterator];
		if ( resultObject == null )
			break;
		nameArrayList.add(String.valueOf(resultObject.getResultEvent()));
		valueArrayList.add(new Integer(resultObject.getResultId()));
	}
	se = new SelectElement(AppConstants.RESULT_ID_STR, nameArrayList, valueArrayList, String.valueOf(resultId), 0);
	se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.RESULT_EVENT_LABEL);
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
	if ( resultId != 0 )
		se = new SelectElement(AppConstants.RESULT_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(selectedResultObj.getResultEvent()), 0);
	else
		se = new SelectElement(AppConstants.RESULT_EVENT_STR, nameArrayList, valueArrayList, String.valueOf(resultEvent), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.RESULT_EVENT_TYPE_LABEL);
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
	if ( resultId != 0 )
		se = new SelectElement(AppConstants.RESULT_EVENT_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(selectedResultObj.getResultEventType()), 0);
	else
		se = new SelectElement(AppConstants.RESULT_EVENT_TYPE_STR, nameArrayList, valueArrayList, String.valueOf(resultEventType), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.RESULT_MEDAL_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	nameArrayList = new ArrayList<String>();
	valueArrayList = new ArrayList<Integer>();
	MedalInterface medalIf = new MedalImpl();
	MedalObject[] medalRefArr = medalIf.getAllMedals();
	for (int iterator = 0; iterator < medalRefArr.length; iterator++) {
	    MedalObject medalObject = medalRefArr[iterator];
	    if (medalObject == null)
		break;
	    nameArrayList.add(String.valueOf(medalObject.getMedalName()));
	    valueArrayList.add(new Integer(medalObject.getMedalId()));
	}
	if ( resultId != 0 )
		se = new SelectElement(AppConstants.RESULT_MEDAL_STR, nameArrayList, valueArrayList, String.valueOf(selectedResultObj.getResultMedal()), 0);
	else
		se = new SelectElement(AppConstants.RESULT_MEDAL_STR, nameArrayList, valueArrayList, String.valueOf(resultMedal), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.RESULT_WINNER_LABEL);
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
	if ( resultId != 0 )
		se = new SelectElement(AppConstants.RESULT_WINNER_STR, nameArrayList, valueArrayList, String.valueOf(selectedResultObj.getResultWinner()), 0);
	else
		se = new SelectElement(AppConstants.RESULT_WINNER_STR, nameArrayList, valueArrayList, String.valueOf(resultWinner), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.RESULT_WINNER_REGISTRANT_LABEL);
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
	if ( resultId != 0 )
		se = new SelectElement(AppConstants.RESULT_WINNER_REGISTRANT_STR, nameArrayList, valueArrayList, String.valueOf(selectedResultObj.getResultWinnerRegistrant()), 0);
	else
		se = new SelectElement(AppConstants.RESULT_WINNER_REGISTRANT_STR, nameArrayList, valueArrayList, String.valueOf(resultWinnerRegistrant), 0);
	td = new TableDataElement(se);
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.RESULT_SCORE_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( resultId != 0 )
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.RESULT_SCORE_STR, selectedResultObj.getResultScore()));
	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.RESULT_SCORE_STR, Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.RESULT_TIMING_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	if ( resultId != 0 ) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		String formattedDate = dateFormatter.format(selectedResultObj.getResultTiming());
		td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.RESULT_TIMING_STR, formattedDate));
	}	else
	    td = new TableDataElement(new InputElement(InputElement.TEXT, AppConstants.RESULT_TIMING_STR, Constants.EMPTY));
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
	cell.setCellValue(AppConstants.RESULT_ID_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue("DB Operation");

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.RESULT_EVENT_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.RESULT_EVENT_TYPE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.RESULT_MEDAL_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.RESULT_WINNER_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.RESULT_WINNER_REGISTRANT_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.RESULT_SCORE_LABEL);

	cell = row.createCell((short)col++);
	cell.setCellStyle(cellstyleTblHdr);
	cell.setCellValue(AppConstants.RESULT_TIMING_LABEL);

	ResultInterface resultIf = new ResultImpl();
	ResultObject[] resultArr = resultIf.getAllResults();
	if ( resultArr != null && resultArr.length > 0 ) {
	    for (int iterator = 0; iterator < resultArr.length; iterator++) {
		ResultObject resultObj = resultArr[iterator];
		if ( resultObj == null )
		    break;
		rowNum++;
		col = 0;
		row = sheet.createRow((short)rowNum);

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(resultObj.getResultId());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue("INFO");

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(resultObj.getResultEvent());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(resultObj.getResultEventType());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(resultObj.getResultMedal());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(resultObj.getResultWinner());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(resultObj.getResultWinnerRegistrant());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(resultObj.getResultScore());

		cell = row.createCell((short)col++);
		cell.setCellStyle(cellstyleTblLeft);
		cell.setCellValue(resultObj.getResultTiming());
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
	ResultInterface resultIf = new ResultImpl();
	ResultObject resultObject = new ResultObject();

	rowNum = 0;
	while ( true ) {
	    row = sheet.getRow(++rowNum);
	    if ( row == null )
		break;
	    resultObject = new ResultObject();
	    cell = row.getCell((short)1);
	    if ( cell != null )
		dbOp = Util.trim(cell.getStringCellValue());
	    else
		dbOp = null;
	    DebugHandler.fine("DbOp = |" + dbOp + "|");
	    if ( dbOp != null &&  dbOp.equalsIgnoreCase("UPDATE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    resultObject.setResultId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		resultObject = resultIf.getResult(resultObject.getResultId());
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    resultObject.setResultEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    resultObject.setResultEvent(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    resultObject.setResultEventType((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    resultObject.setResultEventType(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    resultObject.setResultMedal((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    resultObject.setResultMedal(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    resultObject.setResultWinner((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    resultObject.setResultWinner(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    resultObject.setResultWinnerRegistrant((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    resultObject.setResultWinnerRegistrant(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		    resultObject.setResultScore(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    resultObject.setResultTiming(cell.getDateCellValue());
		DebugHandler.fine("Updating Result " + resultObject);
		resultIf.updateResult(resultObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("INSERT") ) {
		col = 2; // Starting from 3rd Column
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    resultObject.setResultEvent((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    resultObject.setResultEvent(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    resultObject.setResultEventType((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    resultObject.setResultEventType(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    resultObject.setResultMedal((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    resultObject.setResultMedal(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    resultObject.setResultWinner((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    resultObject.setResultWinner(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		try {
		    resultObject.setResultWinnerRegistrant((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    resultObject.setResultWinnerRegistrant(0);
		}
		cell = row.getCell((short)col++);
		if ( cell != null )
		    resultObject.setResultScore(Util.trim(cell.getStringCellValue()));
		cell = row.getCell((short)col++);
		if ( cell != null )
		    resultObject.setResultTiming(cell.getDateCellValue());
		DebugHandler.fine("Adding Result " + resultObject);
		resultIf.addResult(resultObject);
	    } else if ( dbOp != null && dbOp.equalsIgnoreCase("DELETE") ) {
		cell = row.getCell((short)0); // Get the first column
		try {
		    resultObject.setResultId((int)cell.getNumericCellValue());
		} catch (NumberFormatException nfe) {
		    throw new AppException("Column A has been changed in " + wb.getSheetName((short)0) + " Current value is Row num " + row + " is : " + cell.getStringCellValue());
		}
		resultObject = resultIf.getResult(resultObject.getResultId());
		resultIf.deleteResult(resultObject);
	    } else if ( dbOp != null && ! dbOp.equalsIgnoreCase("INFO") ) {
		throw new AppException("Invalid operation " + dbOp + " in Row num: " + rowNum);
	    }
	}
    }
}
