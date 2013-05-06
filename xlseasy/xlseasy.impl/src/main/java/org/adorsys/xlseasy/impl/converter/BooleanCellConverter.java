package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.commons.lang.BooleanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * The Class BooleanCellConverter.
 *
 * @author Sandro Sonntag
 */
public class BooleanCellConverter extends CellConverter {

	/**
	 * Gets the cell's value as Boolean.
	 * */
	public Boolean getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		} else {
			return BooleanUtils.toBoolean(cell.toString());
		}
	}

	/**
	 * Sets the cell's type to Boolean.
	 * */
	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		HSSFCell cell = (HSSFCell) cellObject;
		cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
		if (value != null) {
			cell.setCellValue((Boolean)value);
		}
	}
	
	/**
	 * Gets the converter's type. In this case, Boolean.
	 * */
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Boolean.class, boolean.class}; 
	}
}