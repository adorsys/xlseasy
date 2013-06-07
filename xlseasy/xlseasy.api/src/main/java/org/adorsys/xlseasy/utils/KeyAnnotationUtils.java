package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.util.Collection;

import org.adorsys.xlseasy.annotation.Key;
import org.adorsys.xlseasy.annotation.filter.AnnotationUtil;

public abstract class KeyAnnotationUtils {

	public static Field extractKeyField(Class<?> clazz) {
		Collection<Field> keys = AnnotationUtil.findFieldsByAnnotation(clazz,
				true, Key.class);
		if (keys.isEmpty())
			return null;
		return keys.iterator().next();
	}
}