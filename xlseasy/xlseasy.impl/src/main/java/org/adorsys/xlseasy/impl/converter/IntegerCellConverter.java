package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * The Class IntegerCellConverter.
 *
 * @author Sandro Sonntag
 */
public class IntegerCellConverter extends NumberColumnConverter {

	/**
	 * Gets the cell's value as Integer.
	 * */
	public Integer getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		Double value = getDoubleCellValue(cell);
		return value != null ? value.intValue() : null;
	}
	
	/**
	 * Gets the converter type. In this case, Integer.
	 * */
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Integer.class, int.class}; 
	}	
}