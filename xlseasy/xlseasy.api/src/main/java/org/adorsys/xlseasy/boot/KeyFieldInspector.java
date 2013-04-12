package org.adorsys.xlseasy.boot;

import java.lang.reflect.Field;

/**
 * Inspect the key field and set the found value on the WorkbookSheet
 * 
 * @author Francis Pouatcha
 *
 */
public interface KeyFieldInspector {
	public Field findKeyField(Class<?> sheetKlass);
}
