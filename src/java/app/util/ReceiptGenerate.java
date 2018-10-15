package app.util;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import app.busobj.SendMailObject;
import core.util.EnglishNumberToWords;
import core.util.DebugHandler;

public class ReceiptGenerate
{
	public static boolean debug = false;
	public static String[] FIND_STRS = { "%R_YEAR%", "%R_NO%", "%RECEIPT_DATE%", "%DONOR_NAME%",
				      "%ADDRESS%", "%AMOUNT%", "%TRANSFER_TYPE%",
				      "%TRANSFER_DETAILS%", "%TRANSFER_DATE%", 
				      "%TOWARDS%", "%AMOUNT_NO%" };
					  
	public void convertPdf(String docPath, String pdfPath) throws IOException, FileNotFoundException{
		InputStream doc = new FileInputStream(new File(docPath));
		XWPFDocument document = new XWPFDocument(doc);
		PdfOptions options = PdfOptions.create();
		OutputStream out = new FileOutputStream(new File(pdfPath));
		PdfConverter.getInstance().convert(document, out, options);
	}
		
	public void manipulateDocx(InputStream srcFile, String destFile, String[] findStrs, String[] replaceStrs) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		
		try {
			/**
			* if uploaded doc then use HWPF else if uploaded Docx file use
			* XWPFDocument
			*/
			XWPFDocument doc = new XWPFDocument(OPCPackage.open(srcFile));
			for (int i = 0; i < findStrs.length; i++) {
				if (debug) 
					DebugHandler.finest(findStrs[i] + "->" + replaceStrs[i]);

				for (XWPFParagraph p : doc.getParagraphs()) {
					List<XWPFRun> runs = p.getRuns();
					if (runs != null) {
						for (XWPFRun r : runs) {
							String text = r.getText(0);
							if ( debug )
								DebugHandler.finest("Text : " + text);
							if (text != null && text.contains(findStrs[i])) {
								text = text.replace(findStrs[i], replaceStrs[i]);//your content
								r.setText(text, 0);
							}
						}
					}
				}
			}
	
			for (int i = 0; i < findStrs.length; i++) {
				for (XWPFTable tbl : doc.getTables()) {
					for (XWPFTableRow row : tbl.getRows()) {
						for (XWPFTableCell cell : row.getTableCells()) {
							for (XWPFParagraph p : cell.getParagraphs()) {
								for (XWPFRun r : p.getRuns()) {
									String text = r.getText(0);
									if ( debug ) {
										DebugHandler.finest("Find Str: " + findStrs[i]);
										DebugHandler.finest("Replace Str: " + replaceStrs[i]);
									}
									if (text != null && text.contains(findStrs[i])) {
										text = text.replace(findStrs[i], replaceStrs[i]);
											r.setText(text, 0);
									}
								}
							}
						}
					}
				}
			}
			doc.write(new FileOutputStream(destFile));
		} finally {
		}
	}
	// Create Receipt
	public String createReceipt(String template, SendMailObject rObj, boolean manualReceipt) throws IOException,  InvalidFormatException {
		String pdfDirName = System.getProperty("java.io.tmpdir") + File.separator + "pdfs";
		String[] replaceStrs = {
			rObj.getReceiptYear(), 
			rObj.getReceiptNo(),
			rObj.getReceiptDate(), 
			rObj.getRegistrantName() + " " + rObj.getRegistrantLastName(),
			rObj.getReceiptAddress(), 
			(EnglishNumberToWords.convert(Double.parseDouble(rObj.getAmount()))).toUpperCase(),
			rObj.getTransferType(), 
			rObj.getTransferDetails(),
			rObj.getTransferDate(), 
			rObj.getTowards(),
			rObj.getAmount()
        };
		File pdfDir = new File(pdfDirName);
		if ( !pdfDir.exists() ) {
			pdfDir.mkdir();
		}
		String fileBaseName = pdfDirName + File.separator + 
			AppConstants.RECEIPT_NO_PREFIX  + "_" +
			rObj.getReceiptYear()  + "_" +
			rObj.getRegistrantId() + "_" +
			rObj.getRegistrantName() + "_" +
			rObj.getRegistrantLastName();
		fileBaseName = fileBaseName.replaceAll(" ", "_");
		String destFile = fileBaseName + ".pdf";
		String destFileWord = fileBaseName + ".docx";
		if ( debug ) {
			DebugHandler.finest("destFile :" + destFile);
			DebugHandler.finest("destFileWord :" + destFileWord);
		}
		InputStream buf = getClass().getResourceAsStream(template);
		manipulateDocx(buf, destFileWord, FIND_STRS, replaceStrs);
		if ( manualReceipt ) // Send the doc file and do not bother with pdf 
			return destFileWord;
		DebugHandler.finest("Generating...." + destFile);
        convertPdf(destFileWord, destFile );
		File destFileWordPtr = new File(destFileWord);
		destFileWordPtr.delete();
		return destFile;
	}
}
