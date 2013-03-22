package org.adorsys.xlseasy.annotation;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class SheetColumnObject.
 */
public class SheetColumnObject implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6933652382850421643L;
	
	/**
	 * Instantiates a new sheet column object.
	 *
	 * @param columnName the column name
	 * @param converter the converter
	 * @param headerStyle the header style
	 * @param columnStyle the column style
	 */
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
	
	/** The column name. */
	String columnName = "";
	
	/**
	 * Column name.
	 *
	 * @return the string
	 */
	public String columnName() { return columnName;}

	/** The merged. */
	boolean merged = false;
	
	/**
	 * Merged.
	 *
	 * @return true, if successful
	 */
	public boolean merged() { return merged;}

	/** The header merged. */
	int headerMerged = 0;
	
	/**
	 * Header merged.
	 *
	 * @return the int
	 */
	public int headerMerged() { return headerMerged;}

	/** The optional. */
	boolean optional = false;
	
	/**
	 * Optional.
	 *
	 * @return true, if successful
	 */
	public boolean optional() { return optional;}
	
	/** The converter. */
	Class<?> converter = Object.class;
	
	/**
	 * Converter.
	 *
	 * @return the class
	 */
	public Class<?> converter() { return converter;}

	/** The hidden. */
	boolean hidden = false;
	
	/**
	 * Hidden.
	 *
	 * @return true, if successful
	 */
	public boolean hidden() { return hidden;}

	/** The header comment. */
	String headerComment = "";
	
	/**
	 * Header comment.
	 *
	 * @return the string
	 */
	public String headerComment() { return headerComment;}
	
	/** The header style. */
	SheetCellStyleObject headerStyle = SheetCellStyleObject.newInstance(null);
	
	/**
	 * Header style.
	 *
	 * @return the sheet cell style object
	 */
	public SheetCellStyleObject headerStyle() { return headerStyle; }

	/** The column style. */
	SheetCellStyleObject columnStyle = SheetCellStyleObject.newInstance(null);
	
	/**
	 * Column style.
	 *
	 * @return the sheet cell style object
	 */
	public SheetCellStyleObject columnStyle() { return columnStyle; }
}
