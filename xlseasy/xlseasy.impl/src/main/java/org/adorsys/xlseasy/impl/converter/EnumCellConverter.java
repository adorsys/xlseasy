package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;

/**
 * The Class EnumCellConverter.
 *
 * @param <E> the element type
 * @author Sandro Sonntag
 */
public class EnumCellConverter<E extends Enum<E>> extends CellConverter {
		
	/**
	 * Gets the cell's value as Enum.
	 * */
	public Object getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session)
			throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
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

	/**
	 * Sets the cell's value as String.
	 * */
	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		HSSFCell cell = (HSSFCell) cellObject;
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		Enum<?> e = (Enum<?>) value;
		HSSFRichTextString stringValue = new HSSFRichTextString(
				value != null ? e.name() : "");
		cell.setCellValue(stringValue);
	}

	/**
	 * Gets the converter type. In this case, Enum.
	 * */
	@Override
	public Class<?>[] getConverterTypes() {
		return new Class<?>[] { Enum.class };
	}
}