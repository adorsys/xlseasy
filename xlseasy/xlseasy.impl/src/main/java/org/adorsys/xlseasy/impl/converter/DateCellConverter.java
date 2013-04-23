package org.adorsys.xlseasy.impl.converter;

import java.util.Date;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author Sandro Sonntag
 */
public class DateCellConverter extends CellConverter {

	public Date getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		return cell.getDateCellValue();
	}

	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		if (value != null){
			HSSFCell cell = (HSSFCell) cellObject;
			cell.setCellValue((Date) value);
		}
	}
	
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Date.class}; 
	}	
}