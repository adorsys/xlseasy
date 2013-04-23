package org.adorsys.xlseasy.impl.proc;

import java.io.Serializable;
import java.util.List;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Sandro Sonntag
 */
public interface WorkbookDescIF<T> extends Serializable {

	public <R> void addSheet(Class<R> recordClass, String label, String beanProperty, int sheetIndex);
	
	public SheetDescIF<?, T> getSheet(String sheetLabel);
	
	public <R> SheetDescIF<R, T> getSheet(Class<R> rowType);

	public List<? extends SheetDescIF<?, T>> getOrderedSheets();
	
	public T loadWorkbookBean(HSSFWorkbook wb, T workBookInstance, ISheetSession<?, ?> session);

	public List<?> loadSheet(HSSFWorkbook wb, T workBookInstance, String
			sheetName,  ISheetSession<?, ?> session);
	
	public T createWorkbookInstance();
}