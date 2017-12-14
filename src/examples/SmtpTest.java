/*
 * %W% %E%
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */


import java.lang.*;
import java.util.*;
import core.db.*;
import core.util.*;
import core.busobj.*;
import app.util.App;
import app.busobj.*;
import app.businterface.*;
import app.busimpl.*;
import javax.mail.*;


public class SmtpTest {
    public static void main(String args[]) throws core.util.AppException {
	App.getInstance();
	SendMail sm = new SendMail();
	String[] to = new String[1];
	to[0] = "govind@guks.com";
	try {
	    sm.postMail(to, "Test", "Testing", Constants.SMTP_FROM);
	}catch (MessagingException me) {
	    DebugHandler.severe("Unable to send message to " + to[1]);
	}
    }
}
