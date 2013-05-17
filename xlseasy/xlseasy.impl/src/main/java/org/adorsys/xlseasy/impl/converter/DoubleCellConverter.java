package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author Sandro Sonntag
 */
public class DoubleCellConverter extends NumberColumnConverter {

	/**
	 * Gets the cell's value as Double.
	 * */
	public Double getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		Double value = getDoubleCellValue(cell);
		return value != null ? value.doubleValue() : null;
	}
	
	/**
	 * Gets the converter's type. In this case, Double.
	 * */
	@Override
	public Class<?>[] getConverterTypes() {
		return new Class<?>[] {Double.class, double.class}; 
	}	
}