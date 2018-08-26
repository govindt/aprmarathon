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
import app.util.AppConstants;
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
		SendMail.SMTP_HOST = AppConstants.SMTP_HOST;
		SendMail.gmail_username = "aprct.treasurer@gmail.com";
		SendMail.gmail_password = args[0];
		SendMail.debug = "true";
		try {
			SendMail.postMail(to, "Test", "Testing", AppConstants.EMAIL_FROM, null);
		}catch (MessagingException me) {
			DebugHandler.severe("Unable to send message to " + to[0]);
			me.printStackTrace();
		}
	}
}
