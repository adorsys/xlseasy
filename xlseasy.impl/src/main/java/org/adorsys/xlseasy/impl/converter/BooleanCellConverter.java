package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.commons.lang.BooleanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;


/**
 * TODO set Javadoc for Class
 * @version $Id: $
 * @author sso
 */
public class BooleanCellConverter extends CellConverter {

	public Boolean getDataCell(HSSFCell cell, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		} else {
			return BooleanUtils.toBoolean(cell.toString());
		}
	}

	public void setHSSFCell(HSSFCell cell, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
		if (value != null) {
			cell.setCellValue((Boolean)value);
		}
	}
	
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Boolean.class, boolean.class}; 
	}
	
}
