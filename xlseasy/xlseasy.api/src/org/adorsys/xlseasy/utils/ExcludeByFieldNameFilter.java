package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;

import org.adorsys.xlseasy.utils.ReflectionUtils.FieldFilter;

/**
 * The Class ExcludeByFieldNameFilter.
 */
public class ExcludeByFieldNameFilter implements FieldFilter {

	/** The discriminated field names. */
	private final Collection<String> discriminatedFieldNames;
	
	/**
	 * Instantiates a new object of type ExcludeByFieldNameFilter.
	 *
	 * @param discriminatedFieldNames the discriminated field names
	 */
	public ExcludeByFieldNameFilter(
			Collection<String> discriminatedFieldNames) {
		if(discriminatedFieldNames==null){
			this.discriminatedFieldNames = new HashSet<String>();
		} else {
			this.discriminatedFieldNames = discriminatedFieldNames;
		}
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.utils.ReflectionUtils.FieldFilter#matches(java.lang.reflect.Field)
	 */
	public boolean matches(Field field) {
		if(discriminatedFieldNames.contains(field.getName())) return false;
		return true;
	}

}
