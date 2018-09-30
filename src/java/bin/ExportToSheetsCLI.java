import core.util.AppException;
import app.util.App;
import core.util.GoogleSheetWrite;
import core.util.DebugHandler;
import app.businterface.ExportToSheetsInterface;
import app.busimpl.ExportToSheetsImpl;


public class ExportToSheetsCLI {
    public static void main(String[] args) throws AppException {
	    if (args.length != 1) {
			DebugHandler.severe("Usage ExportToSheetsCLI <event_id>");
			System.exit(1);
		}
		App theApp = App.getInstance();
		GoogleSheetWrite grs = new GoogleSheetWrite(args[0]);
		DebugHandler.info(grs);
		ExportToSheetsInterface eTSIf = new ExportToSheetsImpl();
		eTSIf.updateRegistrants();
		eTSIf.updateParticipants();
    }
}
