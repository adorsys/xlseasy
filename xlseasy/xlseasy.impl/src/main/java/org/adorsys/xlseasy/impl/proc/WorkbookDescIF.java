package org.adorsys.xlseasy.impl.proc;

import java.io.Serializable;
import java.util.List;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

// TODO: Auto-generated Javadoc
/**
 * TODO set Javadoc for Class.
 *
 * @param <T> the generic type
 * @version $Id: $
 * @author Sandro Sonntag
 */
public interface WorkbookDescIF<T> extends Serializable {

	/**
	 * Adds the sheet.
	 *
	 * @param <R> the generic type
	 * @param recordClass the record class
	 * @param label the label
	 * @param beanProperty the bean property
	 * @param sheetIndex the sheet index
	 */
	public <R> void addSheet(Class<R> recordClass, String label, String beanProperty, int sheetIndex);
	
	/**
	 * Gets the sheet.
	 *
	 * @param sheetLabel the sheet label
	 * @return the sheet
	 */
	public SheetDescIF<?, T> getSheet(String sheetLabel);
	
	/**
	 * Gets the sheet.
	 *
	 * @param <R> the generic type
	 * @param rowType the row type
	 * @return the sheet
	 */
	public <R> SheetDescIF<R, T> getSheet(Class<R> rowType);

	/**
	 * Gets the ordered sheets.
	 *
	 * @return the ordered sheets
	 */
	public List<? extends SheetDescIF<?, T>> getOrderedSheets();
	
	/**
	 * Load workbook bean.
	 *
	 * @param wb the wb
	 * @param workBookInstance the work book instance
	 * @param session the session
	 * @return the t
	 */
	public T loadWorkbookBean(HSSFWorkbook wb, T workBookInstance, ISheetSession<?, ?> session);

	/**
	 * Load sheet.
	 *
	 * @param wb the wb
	 * @param workBookInstance the work book instance
	 * @param sheetName the sheet name
	 * @param session the session
	 * @return the list
	 */
	public List<?> loadSheet(HSSFWorkbook wb, T workBookInstance, String
			sheetName,  ISheetSession<?, ?> session);
	
	/**
	 * Creates the workbook instance.
	 *
	 * @return the t
	 */
	public T createWorkbookInstance();
}
