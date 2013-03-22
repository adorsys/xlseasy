package org.adorsys.xlseasy.boot;

import java.util.Collections;
import java.util.Map;


/**
 * The Class NullDateStylesInspector.
 */
public class NullDateStylesInspector implements DateStylesInspector {

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.boot.DateStylesInspector#inspectDateStyles(java.lang.Class)
	 */
	public Map<String, String> inspectDateStyles(Class<?> sheetKlass) {
		return Collections.emptyMap();
	}

}
