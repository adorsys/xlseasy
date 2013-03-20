package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.apache.poi.hssf.usermodel.HSSFCell;

public abstract class NumberColumnConverter extends CellConverter {

	public NumberColumnConverter() {
		super();
	}

	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		HSSFCell cell = (HSSFCell) cellObject;
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		if (value != null) {
			cell.setCellValue(((Number)value).doubleValue());
		}
	}

}