package org.adorsys.xlseasy.impl.proc;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adorsys.xlseasy.annotation.SpreadsheetService;


public class SpreadsheetServiceImpl implements SpreadsheetService {
	
	private WorkbookDescFactory workbookDescFactory;
	

	public SpreadsheetServiceImpl() {
		this.workbookDescFactory = new WorkbookDescFactory();
	}
	
	public SpreadsheetServiceImpl(WorkbookDescFactory workbookDescFactory) {
		this.workbookDescFactory = workbookDescFactory;
	}
	
	public <WT> WT loadSpreadsheet(InputStream xlsStream, Class<WT> clazz) {
		SheetSession<WT, Object> sheetSession = new SheetSession<WT, Object>(clazz, workbookDescFactory);
		sheetSession.load(xlsStream);
		return sheetSession.toWorkbookBean();
	}
	
	public <RT> List<RT> loadSpreadsheetRecords(InputStream xlsStream, Class<RT> clazz) {
		SheetSession<Object, RT> sheetSession = new SheetSession<Object, RT>(clazz, workbookDescFactory);
		sheetSession.load(xlsStream);
		return sheetSession.toRecordList();
	}
	
	public <ST> void saveSpreadsheetRecords(Class<ST> sheetType, Collection<ST> rows, OutputStream outputStream) {
		SheetSession<Object, ST> sheetSession = new SheetSession<Object, ST>(sheetType, workbookDescFactory);
		sheetSession.load(rows);
		sheetSession.save(outputStream);
	}

	
	public <WT> void saveSpreadsheet(Class<WT> workbookType, WT model, OutputStream outputStream) {
		SheetSession<WT, Object> sheetSession = new SheetSession<WT, Object>(workbookType, workbookDescFactory);
		sheetSession.load(model);
		sheetSession.save(outputStream);
	}

}