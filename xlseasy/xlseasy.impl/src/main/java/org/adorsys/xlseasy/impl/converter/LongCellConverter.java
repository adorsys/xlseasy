package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * The Class LongCellConverter.
 *
 * @author Sandro Sonntag
 */
public class LongCellConverter extends NumberColumnConverter {

	/**
	 * Gets the cell value as Long.
	 * */
	public Long getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		Double value = getDoubleCellValue(cell);
		return value != null ? value.longValue() : null;
	}
	
	/**
	 * Gets the converter type. In this case, Long.
	 * */
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Long.class, long.class}; 
	}
	
}
