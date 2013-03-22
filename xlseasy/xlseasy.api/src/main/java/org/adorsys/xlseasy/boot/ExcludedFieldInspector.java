package org.adorsys.xlseasy.boot;

import java.util.Collection;

/**
 * The Interface ExcludedFieldInspector.
 */
public interface ExcludedFieldInspector {
	
	/**
	 * Gets the excluded field names.
	 *
	 * @param klass the klass
	 * @return the excluded field names
	 */
	public Collection<String> getExcludedFieldNames(Class<?> klass);
}
