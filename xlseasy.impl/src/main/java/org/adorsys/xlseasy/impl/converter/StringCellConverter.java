package org.adorsys.xlseasy.impl.converter;

import java.io.Serializable;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;


/**
 * TODO set Javadoc for Class
 * @version $Id: $
 * @author sso
 */
public class StringCellConverter extends CellConverter {

	public String getDataCell(HSSFCell cell, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		return getStringCellValue(cell);
	}

	public void setHSSFCell(HSSFCell cell, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		HSSFRichTextString stringValue = new HSSFRichTextString(value != null ? String.valueOf(value) : "");
		cell.setCellValue(stringValue);
	}
	
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {String.class, Serializable.class}; 
	}
}
