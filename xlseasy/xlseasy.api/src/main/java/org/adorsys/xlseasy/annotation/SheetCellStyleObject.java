package org.adorsys.xlseasy.annotation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Object implementing the same interface like a sheet cell style.
 *
 * @author Francis Pouatcha <info@adorsys.de>
 */
public class SheetCellStyleObject implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2771798134138583609L;

	/** The default instance. */
	private static SheetCellStyleObject defaultInstance = new SheetCellStyleObject();
	
	/** The singletons. */
	private static Map<SheetCellStyleObject, SheetCellStyleObject> singletons = new HashMap<SheetCellStyleObject, SheetCellStyleObject>();
	
	static{
		singletons.put(defaultInstance, defaultInstance);
	}
	
	/**
	 * New instance.
	 *
	 * @param sheetCellStyle the sheet cell style
	 * @return the sheet cell style object
	 */
	public static SheetCellStyleObject newInstance(SheetCellStyle sheetCellStyle){
		if(sheetCellStyle==null) return defaultInstance;
		SheetCellStyleObject trans = new SheetCellStyleObject(sheetCellStyle);
		return resolveSingleton(trans);
	}
	
	/**
	 * New instance.
	 *
	 * @param dataFormat the data format
	 * @param fontStyleBold the font style bold
	 * @return the sheet cell style object
	 */
	public static SheetCellStyleObject newInstance(String dataFormat, boolean fontStyleBold){
		SheetCellStyleObject sheetCellStyleObject = new SheetCellStyleObject();
		sheetCellStyleObject.fontStyleBold = fontStyleBold;
		if(dataFormat!=null) sheetCellStyleObject.dataFormat=dataFormat;
		return resolveSingleton(sheetCellStyleObject);
	}	
	
	/**
	 * Resolve singleton.
	 *
	 * @param trans the trans
	 * @return the sheet cell style object
	 */
	private static SheetCellStyleObject resolveSingleton(SheetCellStyleObject trans){
		if(singletons.containsKey(trans)) {
			return singletons.get(trans);
		} else {
			singletons.put(trans, trans);
			return trans;
		}
	}
	
	/**
	 * Instantiates a new sheet cell style object.
	 */
	private SheetCellStyleObject() {
		super();
	}

	/**
	 * Instantiates a new sheet cell style object.
	 *
	 * @param sheetCellStyle the sheet cell style
	 */
	private SheetCellStyleObject(SheetCellStyle sheetCellStyle) {
		dataFormat = sheetCellStyle.dataFormat();
		fontName = sheetCellStyle.fontName();
		fontSize = sheetCellStyle.fontSize();
		fontStyleBold = sheetCellStyle.fontStyleBold();
		fontStyleItalic = sheetCellStyle.fontStyleItalic();
		fontStyleStrikeout = sheetCellStyle.fontStyleStrikeout();
		fontStyleUnderline = sheetCellStyle.fontStyleUnderline();
		fontColor = sheetCellStyle.fontColor();
		backgroundColor = sheetCellStyle.backgroundColor();
		align = sheetCellStyle.align();
		wrapText = sheetCellStyle.wrapText();
	}

	/** The data format. */
	private String dataFormat = "";
	
	/**
	 * Data format.
	 *
	 * @return the string
	 */
	public String dataFormat(){
		return dataFormat;
	}

	/** The font name. */
	String fontName = "";
	
	/**
	 * Font name.
	 *
	 * @return the string
	 */
	public String fontName() {
		return fontName;
	}
	
	/** The font size. */
	int fontSize = -1;
	
	/**
	 * Font size.
	 *
	 * @return the int
	 */
	public int fontSize() {
		return fontSize;
	}
	
	/** The font style bold. */
	boolean fontStyleBold = false;
	
	/**
	 * Font style bold.
	 *
	 * @return true, if successful
	 */
	public boolean fontStyleBold(){
		return fontStyleBold;
	}
	
	/** The font style italic. */
	boolean fontStyleItalic = false;
	
	/**
	 * Font style italic.
	 *
	 * @return true, if successful
	 */
	public boolean fontStyleItalic(){
		return fontStyleItalic;
	}
	
	/** The font style strikeout. */
	boolean fontStyleStrikeout = false;
	
	/**
	 * Font style strikeout.
	 *
	 * @return true, if successful
	 */
	public boolean fontStyleStrikeout() {
		return fontStyleStrikeout;
	}
	
	/** The font style underline. */
	boolean fontStyleUnderline = false;
	
	/**
	 * Font style underline.
	 *
	 * @return true, if successful
	 */
	public boolean fontStyleUnderline() {
		return fontStyleUnderline;
	}
	
	/** The font color. */
	short fontColor = -1;
	
	/**
	 * Font color.
	 *
	 * @return the short
	 */
	public short fontColor() {
		return fontColor;
	}
	
	/** The background color. */
	short backgroundColor = -1;
	
	/**
	 * Background color.
	 *
	 * @return the short
	 */
	public short backgroundColor(){
		return backgroundColor;
	}
	
	/** The align. */
	CellAlign align = CellAlign.GENERAL;
	
	/**
	 * Align.
	 *
	 * @return the cell align
	 */
	public CellAlign align() {
		return align;
	}
	
	/** The wrap text. */
	boolean wrapText = false;
	
	/**
	 * Wrap text.
	 *
	 * @return true, if successful
	 */
	public boolean wrapText() {
		return wrapText;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((align == null) ? 0 : align.hashCode());
		result = prime * result + backgroundColor;
		result = prime * result
				+ ((dataFormat == null) ? 0 : dataFormat.hashCode());
		result = prime * result + fontColor;
		result = prime * result
				+ ((fontName == null) ? 0 : fontName.hashCode());
		result = prime * result + fontSize;
		result = prime * result + (fontStyleBold ? 1231 : 1237);
		result = prime * result + (fontStyleItalic ? 1231 : 1237);
		result = prime * result + (fontStyleStrikeout ? 1231 : 1237);
		result = prime * result + (fontStyleUnderline ? 1231 : 1237);
		result = prime * result + (wrapText ? 1231 : 1237);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SheetCellStyleObject other = (SheetCellStyleObject) obj;
		if (align != other.align)
			return false;
		if (backgroundColor != other.backgroundColor)
			return false;
		if (dataFormat == null) {
			if (other.dataFormat != null)
				return false;
		} else if (!dataFormat.equals(other.dataFormat))
			return false;
		if (fontColor != other.fontColor)
			return false;
		if (fontName == null) {
			if (other.fontName != null)
				return false;
		} else if (!fontName.equals(other.fontName))
			return false;
		if (fontSize != other.fontSize)
			return false;
		if (fontStyleBold != other.fontStyleBold)
			return false;
		if (fontStyleItalic != other.fontStyleItalic)
			return false;
		if (fontStyleStrikeout != other.fontStyleStrikeout)
			return false;
		if (fontStyleUnderline != other.fontStyleUnderline)
			return false;
		if (wrapText != other.wrapText)
			return false;
		return true;
	}
}
