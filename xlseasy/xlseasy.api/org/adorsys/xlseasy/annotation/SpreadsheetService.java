package org.adorsys.xlseasy.annotation;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 * The Interface SpreadsheetService.
 */
public interface SpreadsheetService {
	
	/**
	 * Load spreadsheet.
	 *
	 * @param <WT> the generic type
	 * @param xlsStream the xls stream
	 * @param clazz the class
	 * @return the wt
	 */
	public <WT> WT loadSpreadsheet(InputStream xlsStream, Class<WT> clazz);
	
	/**
	 * Load spreadsheet records.
	 *
	 * @param <RT> the generic type
	 * @param xlsStream the xls stream
	 * @param clazz the class
	 * @return the list
	 */
	public <RT> List<RT> loadSpreadsheetRecords(InputStream xlsStream, Class<RT> clazz);
	
	/**
	 * Save spreadsheet records.
	 *
	 * @param <ST> the generic type
	 * @param sheetType the sheet type
	 * @param rows the rows
	 * @param outputStream the output stream
	 */
	public <ST> void saveSpreadsheetRecords(Class<ST> sheetType, Collection<ST> rows, OutputStream outputStream) ;
	
	/**
	 * Save spreadsheet.
	 *
	 * @param <WT> the generic type
	 * @param workbookType the workbook type
	 * @param model the model
	 * @param outputStream the output stream
	 */
	public <WT> void saveSpreadsheet(Class<WT> workbookType, WT model, OutputStream outputStream);
	
}
