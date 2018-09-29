import core.util.AppException;
import app.util.App;
import core.util.GoogleSheetWrite;
import core.util.DebugHandler;
import app.businterface.ExportToSheetsInterface;
import app.busimpl.ExportToSheetsImpl;


public class ExportToSheetsCLI {
    public static void main(String[] args) throws AppException {
		App theApp = App.getInstance();
		GoogleSheetWrite grs = new GoogleSheetWrite();
		DebugHandler.info(grs);
		ExportToSheetsInterface eTSIf = new ExportToSheetsImpl();
		eTSIf.updateRegistrants();
		eTSIf.updateParticipants();
    }
}
