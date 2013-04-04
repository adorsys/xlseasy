package org.adorsys.xlseasy.boot;

import java.lang.reflect.Field;

/**
 * The Class NullKeyFieldInspector.
 */
public class NullKeyFieldInspector implements KeyFieldInspector {

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.boot.KeyFieldInspector#findKeyField(java.lang.Class)
	 */
	public Field findKeyField(Class<?> sheetKlass) {
		return null;
	}

}
