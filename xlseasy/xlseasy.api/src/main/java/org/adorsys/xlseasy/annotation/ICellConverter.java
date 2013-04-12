package org.adorsys.xlseasy.annotation;


/**
 * Replacing HSSFCell with Object
 * @author Francis Pouatcha
 *
 */
public interface ICellConverter {

	public abstract void setHSSFCell(Object cell, Object value,
			Class<?> objectType, ISheetSession<?, ?> session);

	public abstract Object getDataCell(Object cell, Class<?> objectType, ISheetSession<?, ?> session)
			throws SpreadsheetConverterException;

	public abstract Class<?>[] getConveterTypes();

}