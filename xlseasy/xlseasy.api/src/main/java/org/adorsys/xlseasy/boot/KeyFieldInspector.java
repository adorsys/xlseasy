package org.adorsys.xlseasy.boot;

import java.lang.reflect.Field;

/**
 * Inspects the key field and sets the found value on the WorkbookSheet
 * 
 * @author Francis Pouatcha
 *
 */
public interface KeyFieldInspector {
	public Field findKeyField(Class<?> sheetKlass);
}
