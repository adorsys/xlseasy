package org.adorsys.xlseasy.impl.converter;

import java.io.Serializable;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * The Class ErrorCellConverter.
 *
 * @author Marius Guede
 */
public class ErrorCellConverter extends CellConverter {

	/**
	 * Gets the cell value.
	 * */
	public Object getDataCell(Object cellObject, Class<?> objectType,
			ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		return cell.getErrorCellValue();
	}

	/**
	 * Saves a value to the cell.
	 * */
	public void setHSSFCell(Object cellObject, Object value,
			Class<?> objectType, ISheetSession<?, ?> session) {
		
		// gets the cellObject
		HSSFCell cell = (HSSFCell) cellObject;
		
		// sets the cell's type to Error
		cell.setCellType(HSSFCell.CELL_TYPE_ERROR);
				
		// sets cell's value
		cell.setCellValue("????");
	}

	/**
	 * Gets the converter type. In this case, Error.
	 * */
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] { Error.class, Serializable.class };
	}
}