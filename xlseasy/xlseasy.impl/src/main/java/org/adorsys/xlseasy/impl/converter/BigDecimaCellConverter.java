package org.adorsys.xlseasy.impl.converter;

import java.math.BigDecimal;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * The Class BigDecimaCellConverter.
 *
 * @author Sandro Sonntag
 */
public class BigDecimaCellConverter extends NumberColumnConverter {

	/**
	 * Gets the cell's value as BigDecimal.
	 * */
	public BigDecimal getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		Double value = getDoubleCellValue(cell);
		return value != null ? BigDecimal.valueOf(value) : null;
	}
	
	/**
	 * Gets the converter's type. In this case, BigDecimal.
	 * */
	@Override
	public Class<?>[] getConverterTypes() {
		return new Class<?>[] {BigDecimal.class}; 
	}	
}