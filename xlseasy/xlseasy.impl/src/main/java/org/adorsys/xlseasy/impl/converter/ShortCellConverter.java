package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author Sandro Sonntag
 */
public class ShortCellConverter extends NumberColumnConverter {

	public Short getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		Double value = getDoubleCellValue(cell);
		return value != null ? value.shortValue() : null;
	}

	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Short.class, short.class}; 
	}	
}