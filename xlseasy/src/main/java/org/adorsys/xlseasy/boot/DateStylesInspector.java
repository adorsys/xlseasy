package org.adorsys.xlseasy.boot;

import java.util.Map;


/**
 * Inspect date styles for fields of a given klass.
 * 
 * @author francis
 *
 */
public interface DateStylesInspector {

	public Map<String, String> inspectDateStyles(Class<?> sheetKlass);

}
