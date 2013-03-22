package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.commons.lang.BooleanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;


// TODO: Auto-generated Javadoc
/**
 * The Class BooleanCellConverter.
 *
 * @version $Id: $
 * @author Sandro Sonntag <info@adorsys.de>
 */
public class BooleanCellConverter extends CellConverter {

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CellConverter#getDataCell(java.lang.Object, java.lang.Class, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public Boolean getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		} else {
			return BooleanUtils.toBoolean(cell.toString());
		}
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CellConverter#setHSSFCell(java.lang.Object, java.lang.Object, java.lang.Class, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		HSSFCell cell = (HSSFCell) cellObject;
		cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
		if (value != null) {
			cell.setCellValue((Boolean)value);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CellConverter#getConveterTypes()
	 */
	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Boolean.class, boolean.class}; 
	}

}
