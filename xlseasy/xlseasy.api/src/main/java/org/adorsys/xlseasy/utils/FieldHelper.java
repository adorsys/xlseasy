package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


// TODO: Auto-generated Javadoc
/**
 * The Class FieldHelper.
 */
public class FieldHelper {

	/**
	 * Gets the ter method.
	 *
	 * @param field the field
	 * @param klass the klass
	 * @return the ter method
	 */
	public static Method getterMethod(Field field, Class<?> klass){
		String getter = getterMethodName(field.getName());
		return ReflectionUtils.findMethod(klass, getter);
	}

	/**
	 * Setter method.
	 *
	 * @param field the field
	 * @param klass the klass
	 * @return the method
	 */
	public static Method setterMethod(Field field, Class<?> klass){
		String setter = setterMethodName(field.getName());
		return ReflectionUtils.findMethod(klass, setter, field.getType());
	}

	/**
	 * Gets the ter method name.
	 *
	 * @param name the name
	 * @return the ter method name
	 */
	public static String getterMethodName(String name) {
	    return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	/**
	 * Setter method name.
	 *
	 * @param name the name
	 * @return the string
	 */
	public static String setterMethodName(String name) {
	    return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
}
