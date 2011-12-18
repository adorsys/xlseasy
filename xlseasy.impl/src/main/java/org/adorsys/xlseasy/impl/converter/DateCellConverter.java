package org.adorsys.xlseasy.impl.converter;

import java.util.Date;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;


/**
 * TODO set Javadoc for Class
 * @version $Id: $
 * @author sso
 */
public class DateCellConverter extends CellConverter {

	public Date getDataCell(HSSFCell cell, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		return cell.getDateCellValue();
	}

	public void setHSSFCell(HSSFCell cell, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		if (value != null)
			cell.setCellValue((Date) value);
	}
	
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Date.class}; 
	}
	
}
