package org.adorsys.xlseasy.boot;

import java.lang.reflect.Field;

/**
 * Inspect the key field and set the found value on the WorkbookSheet.
 *
 * @author Francis Pouatcha <info@adorsys.de>
 */
public interface KeyFieldInspector {
	
	/**
	 * Find key field.
	 *
	 * @param sheetKlass the sheet klass
	 * @return the field
	 */
	public Field findKeyField(Class<?> sheetKlass);
}
