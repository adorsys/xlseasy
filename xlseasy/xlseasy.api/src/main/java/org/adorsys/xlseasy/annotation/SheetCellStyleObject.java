package org.adorsys.xlseasy.annotation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Object implementing the same interface like a sheet cell stype
 * 
 * @author Francis Pouatcha
 *
 */
public class SheetCellStyleObject implements Serializable {
	
	private static final long serialVersionUID = 2771798134138583609L;

	private static SheetCellStyleObject defaultInstance = new SheetCellStyleObject();
	private static Map<SheetCellStyleObject, SheetCellStyleObject> singletons = new HashMap<SheetCellStyleObject, SheetCellStyleObject>();
	
	static{
		singletons.put(defaultInstance, defaultInstance);
	}
	
	public static SheetCellStyleObject newInstance(SheetCellStyle sheetCellStyle){
		if(sheetCellStyle==null) return defaultInstance;
		SheetCellStyleObject trans = new SheetCellStyleObject(sheetCellStyle);
		return resolveSingleton(trans);
	}
	
	public static SheetCellStyleObject newInstance(String dataFormat, boolean fontStyleBold){
		SheetCellStyleObject sheetCellStyleObject = new SheetCellStyleObject();
		sheetCellStyleObject.fontStyleBold = fontStyleBold;
		if(dataFormat!=null) sheetCellStyleObject.dataFormat=dataFormat;
		return resolveSingleton(sheetCellStyleObject);
	}	
	
	private static SheetCellStyleObject resolveSingleton(SheetCellStyleObject trans){
		if(singletons.containsKey(trans)) {
			return singletons.get(trans);
		} else {
			singletons.put(trans, trans);
			return trans;
		}
	}
	
	private SheetCellStyleObject() {
		super();
	}

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

	private String dataFormat = "";
	public String dataFormat(){
		return dataFormat;
	}

	String fontName = "";
	public String fontName() {
		return fontName;
	}
	int fontSize = -1;
	public int fontSize() {
		return fontSize;
	}
	
	boolean fontStyleBold = false;
	public boolean fontStyleBold(){
		return fontStyleBold;
	}
	
	boolean fontStyleItalic = false;
	public boolean fontStyleItalic(){
		return fontStyleItalic;
	}
	
	boolean fontStyleStrikeout = false;
	public boolean fontStyleStrikeout() {
		return fontStyleStrikeout;
	}
	
	boolean fontStyleUnderline = false;
	public boolean fontStyleUnderline() {
		return fontStyleUnderline;
	}
	
	short fontColor = -1;
	public short fontColor() {
		return fontColor;
	}
	
	short backgroundColor = -1;
	public short backgroundColor(){
		return backgroundColor;
	}
	
	CellAlign align = CellAlign.GENERAL;
	public CellAlign align() {
		return align;
	}
	
	boolean wrapText = false;
	public boolean wrapText() {
		return wrapText;
	}

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