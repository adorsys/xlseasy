package org.adorsys.xlseasy.boot;

import java.util.Collection;
import java.util.Collections;

// TODO: Auto-generated Javadoc
/**
 * The Class NullExcludedFieldInspector.
 */
public class NullExcludedFieldInspector implements ExcludedFieldInspector {

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.boot.ExcludedFieldInspector#getExcludedFieldNames(java.lang.Class)
	 */
	public Collection<String> getExcludedFieldNames(Class<?> klass) {
		return Collections.emptyList();
	}

}
