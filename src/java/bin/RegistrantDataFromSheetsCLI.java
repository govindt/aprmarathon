import core.util.DebugHandler;
import core.util.AppException;
import java.text.SimpleDateFormat;
import app.busobj.EventObject;
import app.businterface.EventInterface;
import app.busimpl.EventImpl;
import app.businterface.BulkOpsInterface;
import app.busimpl.BulkOpsImpl;
import app.util.App;

public class RegistrantDataFromSheetsCLI {
	
    public static void main(String[] args) throws AppException {
		App theApp = App.getInstance();
		if ( args.length != 2 ) {
			DebugHandler.severe("Usage RegistrantDataFromSheetsCLI event_id operation[receipt|regupdate|partupdate]");
			System.exit(1);
		}
		int event_id = 0;
		try {
			event_id = Integer.parseInt(args[0]);
		} catch (NumberFormatException nfe) {
			throw new AppException("Invalid Event Id");
		}
		EventInterface eIf = new EventImpl();
		EventObject eObj = eIf.getEvent(event_id);
		if ( eObj == null ) 
			throw new AppException("Event not found for the given event id");
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String year = df.format(eObj.getEventStartDate());
		DebugHandler.info("Year: " + year);
		BulkOpsInterface bOIf = new BulkOpsImpl();
		if ( args[1].equals("receipt"))
			bOIf.bulkReceiptGenerate(year);
		else if ( args[1].equals("regupdate"))
			bOIf.bulkUpdateRegistrants(year);
		else if ( args[1].equals("partupdate"))
			bOIf.bulkUpdateParticipants(year);
    }
}
