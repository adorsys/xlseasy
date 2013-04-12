package org.adorsys.xlseasy.boot;

import java.util.Collections;
import java.util.Map;

public class NullDateStylesInspector implements DateStylesInspector {

	public Map<String, String> inspectDateStyles(Class<?> sheetKlass) {
		return Collections.emptyMap();
	}

}
