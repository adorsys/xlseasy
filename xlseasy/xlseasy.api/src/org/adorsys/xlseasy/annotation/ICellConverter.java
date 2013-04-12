package org.adorsys.xlseasy.annotation;

/**
 * Replacing HSSFCell with Object.
 *
 * @author Francis Pouatcha <info@adorsys.de>
 */
public interface ICellConverter {

	/**
	 * Sets the hssf cell.
	 *
	 * @param cell the cell
	 * @param value the value
	 * @param objectType the object type
	 * @param session the session
	 */
	public abstract void setHSSFCell(Object cell, Object value,
			Class<?> objectType, ISheetSession<?, ?> session);

	/**
	 * Gets the data cell.
	 *
	 * @param cell the cell
	 * @param objectType the object type
	 * @param session the session
	 * @return the data cell
	 * @throws SpreadsheetConverterException the spreadsheet converter exception
	 */
	public abstract Object getDataCell(Object cell, Class<?> objectType, ISheetSession<?, ?> session)
			throws SpreadsheetConverterException;

	/**
	 * Gets the conveter types.
	 *
	 * @return the conveter types
	 */
	public abstract Class<?>[] getConveterTypes();

}