package org.adorsys.xlseasy.boot;

import java.util.Collection;

public interface ExcludedFieldInspector {
	public Collection<String> getExcludedFieldNames(Class<?> klass);
}