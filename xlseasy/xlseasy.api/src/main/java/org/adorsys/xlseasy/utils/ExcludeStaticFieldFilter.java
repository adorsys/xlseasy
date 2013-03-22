package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.adorsys.xlseasy.utils.ReflectionUtils.FieldFilter;


// TODO: Auto-generated Javadoc
/**
 * The Class ExcludeStaticFieldFilter.
 */
public class ExcludeStaticFieldFilter implements FieldFilter {

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.utils.ReflectionUtils.FieldFilter#matches(java.lang.reflect.Field)
	 */
	public boolean matches(Field field) {
		if(ReflectionUtils.isPublicStaticFinal(field)) return false;
		int modifiers = field.getModifiers();
	    if (Modifier.isStatic(modifiers)) return false;
	    return true;
	}

}
