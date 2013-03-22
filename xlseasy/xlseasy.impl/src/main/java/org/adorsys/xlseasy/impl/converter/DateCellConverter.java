package org.adorsys.xlseasy.impl.converter;

import java.util.Date;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

// TODO: Auto-generated Javadoc
/**
 * The Class DateCellConverter.
 *
 * @version $Id: $
 * @author Sandro Sonntag <info@adorsys.de>
 */
public class DateCellConverter extends CellConverter {

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CellConverter#getDataCell(java.lang.Object, java.lang.Class, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public Date getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		return cell.getDateCellValue();
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CellConverter#setHSSFCell(java.lang.Object, java.lang.Object, java.lang.Class, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		if (value != null){
			HSSFCell cell = (HSSFCell) cellObject;
			cell.setCellValue((Date) value);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CellConverter#getConveterTypes()
	 */
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Date.class}; 
	}
	
}
