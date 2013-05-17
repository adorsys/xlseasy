package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * The Class FloatCellConverter.
 *
 * @author Sandro Sonntag
 */
public class FloatCellConverter extends NumberColumnConverter {

	/**
	 * Gets the cell's value as Float.
	 * */
	public Float getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		Double value = getDoubleCellValue(cell);
		return value != null ? value.floatValue() : null;
	}
	
	/**
	 * Gets the converter type. In this case, Float.
	 * */	
	@Override
	public Class<?>[] getConverterTypes() {
		return new Class<?>[] {Float.class, float.class}; 
	}	
}