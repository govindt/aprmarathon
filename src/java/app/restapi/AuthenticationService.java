package app.rest;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;
import app.busimpl.UsersImpl;
import app.businterface.UsersInterface;
import app.busobj.UsersObject;
import core.util.AppException;
import app.util.App;

public class AuthenticationService {
	public boolean authenticate(String authCredentials) {

		if (null == authCredentials)
			return false;
		// header value format will be "Basic encodedstring" for Basic
		// authentication. Example "Basic YWRtaW46YWRtaW4="
		final String encodedUserPassword = authCredentials.replaceFirst("Basic"
				+ " ", "");
		String usernameAndPassword = null;
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(
					encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		final StringTokenizer tokenizer = new StringTokenizer(
				usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		// we have fixed the userid and password as admin
		// call some UserService/LDAP here
		boolean authenticationStatus = false;
		try {	
			App.getInstance();
			UsersInterface uif = new UsersImpl();
			UsersObject usersObj = uif.authenticate(username, password);
			authenticationStatus = (usersObj != null);	
		} catch (AppException ae) {
			authenticationStatus = false;
			ae.printStackTrace();
		}
		return authenticationStatus;
	}
}