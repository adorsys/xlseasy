package org.adorsys.xlseasy.boot;

import java.lang.reflect.Field;

public class NullKeyFieldInspector implements KeyFieldInspector {

	public Field findKeyField(Class<?> sheetKlass) {
		return null;
	}
}