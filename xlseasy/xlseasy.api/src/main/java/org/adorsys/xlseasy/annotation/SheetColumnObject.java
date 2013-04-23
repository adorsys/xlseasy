package org.adorsys.xlseasy.annotation;

import java.io.Serializable;

public class SheetColumnObject implements Serializable {

	private static final long serialVersionUID = 6933652382850421643L;
	
	public SheetColumnObject(String columnName, Class<?> converter, SheetCellStyleObject headerStyle, SheetCellStyleObject columnStyle){
		this.columnName = columnName;
		if(converter!=null)
			this.converter = converter;
		if(headerStyle!=null)
			this.headerStyle = headerStyle;
		if(columnStyle!=null)
			this.columnStyle = columnStyle;
	}
	
	/**
	 * Instantiates a new sheet column object.
	 */
	public SheetColumnObject() {
		super();
	}

	/**
	 * Instantiates a new sheet column object.
	 *
	 * @param sheetColumn the sheet column
	 */
	public SheetColumnObject(SheetColumn sheetColumn) {
		columnName = sheetColumn.columnName();
		merged = sheetColumn.merged();
		headerMerged = sheetColumn.headerMerged();
		optional = sheetColumn.optional();
		converter = sheetColumn.converter();
		hidden = sheetColumn.hidden();
		headerComment = sheetColumn.headerComment();
		headerStyle = SheetCellStyleObject.newInstance(sheetColumn.headerStyle());
		columnStyle = SheetCellStyleObject.newInstance(sheetColumn.columnStyle());
	}	
	
	String columnName = "";
	public String columnName() { return columnName;}

	boolean merged = false;
	public boolean merged() { return merged;}

	int headerMerged = 0;
	public int headerMerged() { return headerMerged;}

	boolean optional = false;
	public boolean optional() { return optional;}
	
	Class<?> converter = Object.class;
	public Class<?> converter() { return converter;}

	boolean hidden = false;
	public boolean hidden() { return hidden;}

	String headerComment = "";
	public String headerComment() { return headerComment;}
	
	SheetCellStyleObject headerStyle = SheetCellStyleObject.newInstance(null);
	public SheetCellStyleObject headerStyle() { return headerStyle; }

	SheetCellStyleObject columnStyle = SheetCellStyleObject.newInstance(null);
	public SheetCellStyleObject columnStyle() { return columnStyle; }
}
