package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.adorsys.xlseasy.utils.ReflectionUtils.FieldFilter;


/**
 * The Class CompositeFieldFilter.
 */
public class CompositeFieldFilter implements FieldFilter {
	
	/** The filters. */
	private final List<FieldFilter> filters;
	
	/**
	 * Instantiates a new composite field filter.
	 *
	 * @param filters the filters
	 */
	public CompositeFieldFilter(List<FieldFilter> filters) {
		this.filters = filters;
	}

	/**
	 * Instantiates a new composite field filter.
	 *
	 * @param filters the filters
	 */
	public CompositeFieldFilter(FieldFilter... filters) {
		this.filters = Arrays.asList(filters);
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.utils.ReflectionUtils.FieldFilter#matches(java.lang.reflect.Field)
	 */
	public boolean matches(Field field) {
		for (FieldFilter fieldFilter : filters) {
			if(!fieldFilter.matches(field)) return false;
		}
		return true;
	}

}
