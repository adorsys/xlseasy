package org.adorsys.xlseasy.annotation;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface ISheetSession<WT, RT> {

	public abstract List<?> getSheetRecords(String name);

	public abstract HSSFWorkbook getWorkbook();
	
	public <T> void setObjectByKey(Class<T> recordClass, Object key, T object);
	
	public <T> T getObjectByKey(Class<T> recordClass, Object key);

}