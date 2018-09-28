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
		
		/*RegistrationTypeObject[] rTObjArr = new RegistrationTypeObject[4];
		RegistrationTypeObject rTObj = new RegistrationTypeObject();
		rTObj.setRegistrationTypeId(7);
		rTObjArr[0] = rTObj;
		rTObj = new RegistrationTypeObject();
		rTObj.setRegistrationTypeId(8);
		rTObjArr[1] = rTObj;
		rTObj = new RegistrationTypeObject();
		rTObj.setRegistrationTypeId(9);
		rTObjArr[2] = rTObj;
		rTObj = new RegistrationTypeObject();
		rTObj.setRegistrationTypeId(10);
		rTObjArr[3] = rTObj;*/
		RegistrationTypeObject[] rTObjArr1 = new RegistrationTypeObject[rTObjArr.length];
		
		int k = 0;
		for ( int j = 0; j < rTObjArr.length; j++ ) {
			if ( rTObjArr[j].getRegistrationTypeId() != AppConstants.ONLINE_REGISTRANT_ID ) {
				DebugHandler.info(rTObjArr[j]);
				rTObjArr1[k] = rTObjArr[j];
				k++;
			}
		}
		rEObjArr = rEIf.getRegistrantEvents(rTObjArr1);

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
		
		String[] ages = { "06-16-1968", "01-01-2016", "02-04-2010", "06-18-1940", "11-12-2001" };
		ParticipantInterface pIf = new ParticipantImpl();
		ParticipantObject pObj = new ParticipantObject();
		pObj.setParticipantGroup(149);
		for ( int i = 0; i < ages.length; i++ ) {
			try {
				pObj.setParticipantDateOfBirth(sDateFormat.parse(ages[i]));
				pIf.updateParticipantAgeCategory(pObj);
			} catch (ParseException pe) {
				System.err.println("Parse Exception " + ages[i]);
			}
			
		}
    }
}
