package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.adorsys.xlseasy.utils.ReflectionUtils.FieldFilter;


/**
 * Match all fields that ar collection types.
 * 
 * @author Francis Pouatcha <info@adorsys.de>
 *
 */
public class CollectionFieldFilter implements FieldFilter {

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.utils.ReflectionUtils.FieldFilter#matches(java.lang.reflect.Field)
	 */
	public boolean matches(Field field) {
		Type genericType = field.getGenericType();
		if (genericType instanceof ParameterizedType) {  
            ParameterizedType pt = (ParameterizedType) genericType;  
            if (Collection.class.isAssignableFrom((Class<?>) pt.getRawType())) return true;
        }
		return false;
	}
}