package org.adorsys.xlseasy.impl.converter;

import java.io.Serializable;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;

/**
 * The Class StringCellConverter.
 *
 * @author Sandro Sonntag
 */
public class StringCellConverter extends CellConverter {

	/**
	 * Gets the cell value as String.
	 * */
	public String getDataCell(Object cellObject, Class<?> objectType,
			ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		return getStringCellValue(cell);
	}

	/**
	 * Saves a value to the cell.
	 * */
	public void setHSSFCell(Object cellObject, Object value,
			Class<?> objectType, ISheetSession<?, ?> session) {
		
		// gets the cellObject
		HSSFCell cell = (HSSFCell) cellObject;
		
		// sets the cell's type to String
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		
		// saves object's value as HSSFRichTextString
		HSSFRichTextString stringValue = new HSSFRichTextString(
				value != null ? String.valueOf(value) : "");
		
		// sets cell's value
		cell.setCellValue(stringValue);
	}

	/**
	 * Gets the converter type. In this case, String.
	 * */
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] { String.class, Serializable.class };
	}
}