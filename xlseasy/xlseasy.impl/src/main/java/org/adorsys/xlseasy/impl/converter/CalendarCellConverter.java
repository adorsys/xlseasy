package org.adorsys.xlseasy.impl.converter;

import java.util.Calendar;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * The Class CalendarCellConverter.
 */
public class CalendarCellConverter extends CellConverter {

	/**
	 * Gets the cell's value as Calendar.
	 * */
	public Calendar getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		Calendar instance = Calendar.getInstance();
		instance.setTime(cell.getDateCellValue());
		return instance;
	}

	/**
	 * Sets the cell's value by casting to Calendar.
	 * */
	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		HSSFCell cell = (HSSFCell) cellObject;
		cell.setCellValue((Calendar)value);
	}
	
	/**
	 * Gets the converter's type. In this case, Calendar.
	 * */
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Calendar.class}; 
	}
}