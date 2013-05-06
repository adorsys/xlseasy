package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The Class FieldHelper.
 * 
 * Helps to build setter and getter Methods for a given field
 */
public class FieldHelper {

	/**
	 * Builds the getter method.
	 *
	 * @param field the field
	 * @param klass the class
	 * @return the getter method
	 */
	public static Method getterMethod(Field field, Class<?> klass){
		String getter = getterMethodName(field.getName());
		return ReflectionUtils.findMethod(klass, getter);
	}

	/**
	 * Builds the setter method.
	 *
	 * @param field the field
	 * @param klass the class
	 * @return the setter method
	 */
	public static Method setterMethod(Field field, Class<?> klass){
		String setter = setterMethodName(field.getName());
		return ReflectionUtils.findMethod(klass, setter, field.getType());
	}

	/**
	 * Sets the getter method's name.
	 *
	 * @param name the name
	 * @return the getter method's name
	 */
	public static String getterMethodName(String name) {
	    return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	/**
	 * Sets the setter method's name.
	 *
	 * @param name the name
	 * @return the setter method's name
	 */
	public static String setterMethodName(String name) {
	    return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
}