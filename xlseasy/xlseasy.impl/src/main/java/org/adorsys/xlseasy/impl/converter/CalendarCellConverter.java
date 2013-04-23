package org.adorsys.xlseasy.impl.converter;

import java.util.Calendar;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class CalendarCellConverter extends CellConverter {

	public Calendar getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		Calendar instance = Calendar.getInstance();
		instance.setTime(cell.getDateCellValue());
		return instance;
	}

	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		HSSFCell cell = (HSSFCell) cellObject;
		cell.setCellValue((Calendar)value);
	}
	
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Calendar.class}; 
	}
}