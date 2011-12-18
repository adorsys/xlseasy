package org.adorsys.xlseasy.impl.converter;

import java.util.Calendar;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class CalendarCellConverter extends CellConverter {

	public Calendar getDataCell(HSSFCell cell, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		Calendar instance = Calendar.getInstance();
		instance.setTime(cell.getDateCellValue());
		return instance;
	}

	public void setHSSFCell(HSSFCell cell, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		cell.setCellValue((Calendar)value);
	}
	
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Calendar.class}; 
	}

}
