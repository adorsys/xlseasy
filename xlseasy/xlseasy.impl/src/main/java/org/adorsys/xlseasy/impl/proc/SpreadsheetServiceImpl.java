package org.adorsys.xlseasy.impl.proc;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adorsys.xlseasy.annotation.SpreadsheetService;

// TODO: Auto-generated Javadoc
/**
 * The Class SpreadsheetServiceImpl.
 */
public class SpreadsheetServiceImpl implements SpreadsheetService {

	/** The workbook desc factory. */
	private WorkbookDescFactory workbookDescFactory;

	/**
	 * Instantiates a new spreadsheet service impl.
	 */
	public SpreadsheetServiceImpl() {
		this.workbookDescFactory = new WorkbookDescFactory();
	}

	/**
	 * Instantiates a new spreadsheet service impl.
	 *
	 * @param workbookDescFactory the workbook desc factory
	 */
	public SpreadsheetServiceImpl(WorkbookDescFactory workbookDescFactory) {
		this.workbookDescFactory = workbookDescFactory;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.annotation.SpreadsheetService#loadSpreadsheet(java.io.InputStream, java.lang.Class)
	 */
	public <WT> WT loadSpreadsheet(InputStream xlsStream, Class<WT> clazz) {
		SheetSession<WT, Object> sheetSession = new SheetSession<WT, Object>(
				clazz, workbookDescFactory);
		sheetSession.load(xlsStream);
		return sheetSession.toWorkbookBean();
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.annotation.SpreadsheetService#loadSpreadsheetRecords(java.io.InputStream, java.lang.Class)
	 */
	public <RT> List<RT> loadSpreadsheetRecords(InputStream xlsStream,
			Class<RT> clazz) {
		SheetSession<Object, RT> sheetSession = new SheetSession<Object, RT>(
				clazz, workbookDescFactory);
		sheetSession.load(xlsStream);
		return sheetSession.toRecordList();
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.annotation.SpreadsheetService#saveSpreadsheetRecords(java.lang.Class, java.util.Collection, java.io.OutputStream)
	 */
	public <ST> void saveSpreadsheetRecords(Class<ST> sheetType,
			Collection<ST> rows, OutputStream outputStream) {
		SheetSession<Object, ST> sheetSession = new SheetSession<Object, ST>(
				sheetType, workbookDescFactory);
		sheetSession.load(rows);
		sheetSession.save(outputStream);
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.annotation.SpreadsheetService#saveSpreadsheet(java.lang.Class, java.lang.Object, java.io.OutputStream)
	 */
	public <WT> void saveSpreadsheet(Class<WT> workbookType, WT model,
			OutputStream outputStream) {
		SheetSession<WT, Object> sheetSession = new SheetSession<WT, Object>(
				workbookType, workbookDescFactory);
		sheetSession.load(model);
		sheetSession.save(outputStream);
	}

}