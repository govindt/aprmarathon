/*
 * %W% %E%
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */


import java.lang.*;
import java.util.*;
import java.text.*;
import core.db.*;
import core.util.*;
import core.busobj.*;
import app.util.App;
import app.util.AppConstants;
import app.busobj.*;
import app.businterface.*;
import app.busimpl.*;


public class DbTest {
    public static void main(String args[]) throws core.util.AppException {
		if ( args.length != 1 ) {
			throw new AppException("Usage: DbTest event_id");
		}
		int event_id;
		try {
			event_id = Integer.parseInt(args[0]);
		} catch (NumberFormatException nfe) {
			throw new AppException("Event ID not Integer");
		}
		App.getInstance();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
		UsersInterface uIf = new UsersImpl();
		UsersObject[] usersObj;
		RegistrantEventInterface rEIf = new RegistrantEventImpl();
		RegistrantInterface rIf = new RegistrantImpl();
		RegistrationTypeInterface rTIf = new RegistrationTypeImpl();
		ArrayList<RegistrantEventObject> rEObjArr = new ArrayList<RegistrantEventObject>();
		ArrayList<RegistrantObject> rObjArr = new ArrayList<RegistrantObject>();
		RegistrationTypeObject[] rTObjArr = rTIf.getAllRegistrationTypes();
		RegistrationTypeObject[] rTObjArr1 = new RegistrationTypeObject[rTObjArr.length];
		
		int k = 0;
		for ( int j = 0; j < rTObjArr.length; j++ ) {
			if ( rTObjArr[j].getRegistrationTypeId() != AppConstants.ONLINE_REGISTRANT_ID ) {
				DebugHandler.info(rTObjArr[j]);
				rTObjArr1[k] = rTObjArr[j];
				k++;
			}
		}
		rEObjArr = rEIf.getRegistrantEvents(rTObjArr1, event_id);

		if ( rEObjArr != null ) {
			System.out.println(rEObjArr.size());
			for ( int i = 0; i < rEObjArr.size(); i++) {
				rObjArr.add(rIf.getRegistrant(rEObjArr.get(i).getRegistrantId()));
			}
		}
		System.out.println(rObjArr);
		System.out.println(rObjArr.size());
		
		long start = System.currentTimeMillis();
		usersObj = uIf.getAllUsers();
		DebugHandler.info(usersObj.length);
		long end = System.currentTimeMillis();
    }
}
