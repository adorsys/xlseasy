package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;

import org.adorsys.xlseasy.utils.ReflectionUtils.FieldFilter;


public class ExcludeByFieldNameFilter implements FieldFilter {

	private final Collection<String> discriminatedFieldNames;
	
	public ExcludeByFieldNameFilter(
			Collection<String> discriminatedFieldNames) {
		if(discriminatedFieldNames==null){
			this.discriminatedFieldNames = new HashSet<String>();
		} else {
			this.discriminatedFieldNames = discriminatedFieldNames;
		}
	}

	public boolean matches(Field field) {
		if(discriminatedFieldNames.contains(field.getName())) return false;
		return true;
	}

}
