package org.adorsys.xlseasy.impl.converter;

import java.io.Serializable;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.record.formula.functions.Hyperlink;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;

/**
 * The Class HyperlinkCellConverter.
 *
 * @author Marius Guede
 */
public class HyperlinkCellConverter extends CellConverter {

	/**
	 * Gets the cell value.
	 * */
	public Object getDataCell(Object cellObject, Class<?> objectType,
			ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		return cell.getHyperlink();
	}

	/**
	 * Saves a value to the cell.
	 * */
	public void setHSSFCell(Object cellObject, Object value,
			Class<?> objectType, ISheetSession<?, ?> session) {
		
		// gets the cellObject
		HSSFCell cell = (HSSFCell) cellObject;
		
		// sets the cell's type to Hyperlink
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	
		// creates a hyperlink
		HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
		link.setAddress(String.valueOf(value));
		
		if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			// sets cell's value
			cell.setHyperlink(link);
		}
	}

	/**
	 * Gets the converter type. In this case, Hyperlink.
	 * */
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] { Hyperlink.class, Serializable.class };
	}
}