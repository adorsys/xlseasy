package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.adorsys.xlseasy.utils.ReflectionUtils.FieldFilter;


public class CompositeFieldFilter implements FieldFilter {
	private final List<FieldFilter> filters;
	
	public CompositeFieldFilter(List<FieldFilter> filters) {
		this.filters = filters;
	}

	public CompositeFieldFilter(FieldFilter... filters) {
		this.filters = Arrays.asList(filters);
	}
	
	public boolean matches(Field field) {
		for (FieldFilter fieldFilter : filters) {
			if(!fieldFilter.matches(field)) return false;
		}
		return true;
	}

}
