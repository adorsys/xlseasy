package org.adorsys.xlseasy.impl.converter;

import java.io.Serializable;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.formula.Formula;

/**
 * The Class FormulaCellConverter.
 *
 * @author Marius Guede
 */
public class FormulaCellConverter extends CellConverter {

	/**
	 * Gets the cell value.
	 * */
	public Object getDataCell(Object cellObject, Class<?> objectType,
			ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		return cell.getCellFormula();
	}

	/**
	 * Saves a value to the cell.
	 * */
	public void setHSSFCell(Object cellObject, Object value,
			Class<?> objectType, ISheetSession<?, ?> session) {
		
		// gets the cellObject
		HSSFCell cell = (HSSFCell) cellObject;
		
		// sets the cell's type to Formula
		cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
		
		if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {			
			// sets cell's value
			cell.setCellFormula(String.valueOf(value));
		}		
	}

	/**
	 * Gets the converter type. In this case, Formula.
	 * */
	@Override
	public Class<?>[] getConverterTypes() {
		return new Class<?>[] { Formula.class, Serializable.class };
	}
}