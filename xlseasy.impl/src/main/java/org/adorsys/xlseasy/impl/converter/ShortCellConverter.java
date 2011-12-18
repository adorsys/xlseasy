package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;


/**
 * TODO set Javadoc for Class
 * @version $Id: $
 * @author sso
 */
public class ShortCellConverter extends NumberColumnConverter {

	public Short getDataCell(HSSFCell cell, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		Double value = getDoubleCellValue(cell);
		return value != null ? value.shortValue() : null;
	}

	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Short.class, short.class}; 
	}
	
}
