package org.adorsys.xlseasy.impl.converter;

import java.io.Serializable;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.record.formula.eval.BlankEval;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * The Class BlankCellConverter.
 * 
 * @author Marius Guede
 */
public class BlankCellConverter extends CellConverter {

	/**
	 * Gets the cell value.
	 * */
	public Object getDataCell(Object cellObject, Class<?> objectType,
			ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		return cell.getStringCellValue();
	}

	/**
	 * Saves a value to the cell.
	 * */
	public void setHSSFCell(Object cellObject, Object value,
			Class<?> objectType, ISheetSession<?, ?> session) {

		// gets the cellObject
		HSSFCell cell = (HSSFCell) cellObject;

		// sets the cell's type to Blank
		cell.setCellType(HSSFCell.CELL_TYPE_BLANK);

		if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
			// sets cell's value
			cell.setCellValue("");
		}
	}

	/**
	 * Gets the converter type. In this case, Blank.
	 * */
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] { BlankEval.class, Serializable.class };
	}
}