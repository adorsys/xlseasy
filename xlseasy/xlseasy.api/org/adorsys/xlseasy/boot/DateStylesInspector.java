package org.adorsys.xlseasy.boot;

import java.util.Map;


/**
 * Inspect date styles for fields of a given class.
 * 
 * @author Francis Pouatcha <info@adorsys.de>
 */
public interface DateStylesInspector {

	/**
	 * Inspect date styles.
	 *
	 * @param sheetKlass the sheet class
	 * @return the map
	 */
	public Map<String, String> inspectDateStyles(Class<?> sheetKlass);

}
