package org.adorsys.xlseasy.annotation;

import java.util.List;

/**
 * Replacing HSSFWorkbook with object. Implementation will cast to correct value.
 * @author francis
 *
 * @param <WT>
 * @param <RT>
 */
public interface ISheetSession<WT, RT> {

	public abstract List<?> getSheetRecords(String name);

	public abstract Object getWorkbook();
	
	public <T> void setObjectByKey(Class<T> recordClass, Object key, T object);
	
	public <T> T getObjectByKey(Class<T> recordClass, Object key);

}