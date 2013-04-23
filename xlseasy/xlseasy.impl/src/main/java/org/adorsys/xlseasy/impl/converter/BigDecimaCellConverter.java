package org.adorsys.xlseasy.impl.converter;

import java.math.BigDecimal;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author Sandro Sonntag
 */
public class BigDecimaCellConverter extends NumberColumnConverter {

	public BigDecimal getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		Double value = getDoubleCellValue(cell);
		return value != null ? BigDecimal.valueOf(value) : null;
	}
	
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {BigDecimal.class}; 
	}	
}