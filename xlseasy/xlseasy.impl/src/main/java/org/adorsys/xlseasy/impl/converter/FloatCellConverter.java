package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;


// TODO: Auto-generated Javadoc
/**
 * The Class FloatCellConverter.
 *
 * @version $Id: $
 * @author Sandro Sonntag <info@adorsys.de>
 */
public class FloatCellConverter extends NumberColumnConverter {

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CellConverter#getDataCell(java.lang.Object, java.lang.Class, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public Float getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		Double value = getDoubleCellValue(cell);
		return value != null ? value.floatValue() : null;
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CellConverter#getConveterTypes()
	 */
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Float.class, float.class}; 
	}
	
}
