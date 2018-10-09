/*
 * @(#)SendMail.java	1.31 04/08/20
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */




package core.util;

import java.lang.*;
import java.util.*;
import java.net.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.activation.*;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;


public class SendMail {
	public static String SMTP_HOST = "";
    public static String subject = "";
    public static String body = "";
    public static String from = "";
    public static String debug = "false";
    public static String sent_by = "";
    public static String gmail_username = "";
    public static String gmail_password = "";
    public static String port = "465";
    private static String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
	
    public static void postMail( String recipients[ ], String subject, String message , String from, String attachment) throws MessagingException
    {
		boolean bdebug = Boolean.parseBoolean(debug);

		//Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.user", gmail_username);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.socketFactory.port", port);  	

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", bdebug);
		
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");
		
		// create some properties and get the default Session
	
		if ( bdebug) {
			System.out.println("Gmail Username " + gmail_username);
			System.out.println("Gmail Password " + gmail_password);
		}
		//Session session = Session.getDefaultInstance(props, null);
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(gmail_username, gmail_password);
			}
		});
		session.setDebug(bdebug);
	
		// create a message
		MimeMessage msg = new MimeMessage(session);
		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}

		msg.setRecipients(Message.RecipientType.TO, addressTo);
		msg.setSubject(subject);
		if ( attachment == null ) {
			msg.setContent(message, "text/html; charset=utf-8");
		} else {
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(message, "text/html; charset=utf-8");
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();   
			DataSource source = new FileDataSource(attachment);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			String fileName = attachment.substring(attachment.lastIndexOf(File.separator) + 1);
			messageBodyPart2.setFileName(fileName);     
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);   
			msg.setContent(multipart);
		}

		msg.saveChanges();
		Transport transport = session.getTransport("smtp");
		transport.connect(SMTP_HOST, gmail_username, gmail_password);
		transport.close();
		Transport.send(msg);
    }
	
	public static String getContents(InputStream inputStream) throws AppException {
		int bufferSize = 1024;
		char[] buffer = new char[bufferSize];
		StringBuilder out = new StringBuilder();
		
		try {
		
			Reader in = new InputStreamReader(inputStream, "UTF-8");
			for (; ; ) {
				int rsz = in.read(buffer, 0, buffer.length);
				if (rsz < 0)
					break;
				out.append(buffer, 0, rsz);
			}
		} catch ( UnsupportedEncodingException uee ) {
			throw new AppException("Caught UnsupportedEncodingException " + uee.getMessage());
		} catch (IOException ioe) {
			throw new AppException("Caught IOException " + ioe.getMessage());
		}
		return out.toString();
	}
}


