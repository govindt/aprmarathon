package app.busobj;


public class CellObject {
	private String sheetName;
	private int rowNo;
	private String column;

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getSheetName() {
		return column;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String toString() {
		return "Sheet: " + sheetName + "|Row: " + rowNo + "|Column " + column;
	}
};
