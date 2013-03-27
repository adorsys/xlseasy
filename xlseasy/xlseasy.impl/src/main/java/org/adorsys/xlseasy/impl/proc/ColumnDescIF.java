package org.adorsys.xlseasy.impl.proc;

import org.adorsys.xlseasy.annotation.ICellConverter;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetCellStyleObject;
import org.adorsys.xlseasy.annotation.SheetColumnObject;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * The Interface ColumnDescIF.
 */
public interface ColumnDescIF {

	/**
	 * Gets the property name.
	 *
	 * @return the property name
	 */
	public String getPropertyName();

	/**
	 * Gets the xls column label.
	 *
	 * @return the xls column label
	 */
	public String getXlsColumnLabel();

	/**
	 * Gets the converter.
	 *
	 * @return the converter
	 */
	public ICellConverter getConverter();

	/**
	 * Gets the anno sheet column.
	 *
	 * @return the anno sheet column
	 */
	public SheetColumnObject getAnnoSheetColumn();

	/**
	 * Gets the anno sheet column style.
	 *
	 * @return the anno sheet column style
	 */
	public SheetCellStyleObject getAnnoSheetColumnStyle();

	/**
	 * Gets the anno sheet header style.
	 *
	 * @return the anno sheet header style
	 */
	public SheetCellStyleObject getAnnoSheetHeaderStyle();

	/**
	 * Gets the column index.
	 *
	 * @return the column index
	 */
	public int getColumnIndex();

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Class<?> getType();
	
	/**
	 * Copy cell value to bean.
	 *
	 * @param bean the bean
	 * @param cell the cell
	 * @param session the session
	 */
	public void copyCellValueToBean(Object bean, HSSFCell cell, ISheetSession<?, ?> session);
	
	/**
	 * Copy bean property value to cell.
	 *
	 * @param bean the bean
	 * @param cell the cell
	 * @param session the session
	 */
	public void copyBeanPropertyValueToCell(Object bean, HSSFCell cell, ISheetSession<?, ?> session);
	
	/**
	 * Sets the header label.
	 *
	 * @param cell the cell
	 * @param session the session
	 */
	public void setHeaderLabel(HSSFCell cell, ISheetSession<?, ?> session);
	
	/**
	 * Format data cell.
	 *
	 * @param session the session
	 * @param cell the cell
	 */
	public void formatDataCell(SheetSession<?, ?> session, HSSFCell cell);
	
	/**
	 * Format header cell.
	 *
	 * @param session the session
	 * @param cell the cell
	 */
	public void formatHeaderCell(SheetSession<?, ?> session, HSSFCell cell);
}