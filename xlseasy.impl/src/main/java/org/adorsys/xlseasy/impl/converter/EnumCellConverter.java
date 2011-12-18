package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;

/**
 * TODO set Javadoc for Class
 * 
 * @version $Id: $
 * @author sso
 */
public class EnumCellConverter<E extends Enum<E>> extends CellConverter {
	public Object getDataCell(HSSFCell cell, Class<?> objectType, ISheetSession<?, ?> session)
			throws SpreadsheetConverterException {
		String s = getStringCellValue(cell);
		if (s == null) {
			return null;
		} else if (Enum.class.isAssignableFrom(objectType)) {
			@SuppressWarnings("unchecked")
			E e = (E) Enum.valueOf((Class<E>) objectType, s);
			return e;
		} else {
			throw (SpreadsheetConverterException) new SpreadsheetConverterException(
					ErrorCodeSheet.UNCOMPATIBE_TYPE_FOR_THIS_CONVERTER)
					.addValue("type", objectType);
		}
	}

	public void setHSSFCell(HSSFCell cell, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		Enum<?> e = (Enum<?>) value;
		HSSFRichTextString stringValue = new HSSFRichTextString(
				value != null ? e.name() : "");
		cell.setCellValue(stringValue);
	}

	@Override
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] { Enum.class };
	}

}
