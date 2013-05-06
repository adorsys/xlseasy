package org.adorsys.xlseasy.impl.converter;

import java.lang.reflect.Field;
import java.util.Collection;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.Key;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.annotation.filter.AnnotationUtil;

public class KeyGenerator {
	private final Class<?> rowClass;
	private final Field field;

	public KeyGenerator(Class<?> rowClass) {
		super();
		this.rowClass = rowClass;
		Collection<Field> findFieldsByAnnotation = AnnotationUtil.findFieldsByAnnotation(rowClass, true, Key.class);
		if (findFieldsByAnnotation.size() > 0) {
			field = findFieldsByAnnotation.iterator().next();
			field.setAccessible(true);
		} else {
			field = null;
		}
	}
	
	public KeyGenerator(Class<?> rowClass, Field keyField) {
		super();
		this.rowClass = rowClass;
		this.field = keyField;
		field.setAccessible(true);
	}
	
	/**
	 * Gets the key from object.
	 */
	public String getKey(Object object) {
		if (field == null) {
			return null;
		}
		try {
			return (String) field.get(object);
		} catch (IllegalArgumentException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION, e);
		} catch (IllegalAccessException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION, e);
		} 
	}
	
	/**
	 * Gets the key column name.
	 */
	public String getKeyColumnName() {
		return  field != null ? field.getName() : null;
	}
}