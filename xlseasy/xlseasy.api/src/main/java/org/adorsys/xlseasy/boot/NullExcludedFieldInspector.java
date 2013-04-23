package org.adorsys.xlseasy.boot;

import java.util.Collection;
import java.util.Collections;

public class NullExcludedFieldInspector implements ExcludedFieldInspector {

	public Collection<String> getExcludedFieldNames(Class<?> klass) {
		return Collections.emptyList();
	}
}