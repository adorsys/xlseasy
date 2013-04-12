package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;


/**
 * TODO set Javadoc for Class
 * @version $Id: $
 * @author sso
 */
public class DoubleCellConverter extends NumberColumnConverter {

	public Double getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		Double value = getDoubleCellValue(cell);
		return value != null ? value.doubleValue() : null;
	}
	
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Double.class, double.class}; 
	}
	
}
