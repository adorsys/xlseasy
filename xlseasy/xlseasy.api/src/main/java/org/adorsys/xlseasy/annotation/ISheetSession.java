package org.adorsys.xlseasy.annotation;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Replacing HSSFWorkbook with object. Implementation will cast to correct value.
 *
 * @param <WT> the generic type
 * @param <RT> the generic type
 * @author Francis Pouatcha <info@adorsys.de>
 */
public interface ISheetSession<WT, RT> {

	/**
	 * Gets the sheet records.
	 *
	 * @param name the name
	 * @return the sheet records
	 */
	public abstract List<?> getSheetRecords(String name);

	/**
	 * Gets the workbook.
	 *
	 * @return the workbook
	 */
	public abstract Object getWorkbook();
	
	/**
	 * Sets the object by key.
	 *
	 * @param <T> the generic type
	 * @param recordClass the record class
	 * @param key the key
	 * @param object the object
	 */
	public <T> void setObjectByKey(Class<T> recordClass, Object key, T object);
	
	/**
	 * Gets the object by key.
	 *
	 * @param <T> the generic type
	 * @param recordClass the record class
	 * @param key the key
	 * @return the object by key
	 */
	public <T> T getObjectByKey(Class<T> recordClass, Object key);

}