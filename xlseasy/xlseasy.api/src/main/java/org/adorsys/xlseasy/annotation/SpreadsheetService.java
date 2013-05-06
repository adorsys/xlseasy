package org.adorsys.xlseasy.annotation;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

public interface SpreadsheetService {
	
	public <WT> WT loadSpreadsheet(InputStream xlsStream, Class<WT> clazz);
	
	public <RT> List<RT> loadSpreadsheetRecords(InputStream xlsStream, Class<RT> clazz);
	
	public <ST> void saveSpreadsheetRecords(Class<ST> sheetType, Collection<ST> rows, OutputStream outputStream);
	
	public <WT> void saveSpreadsheet(Class<WT> workbookType, WT model, OutputStream outputStream);
}