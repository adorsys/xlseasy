package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * The Class FieldHelper.
 */
public class FieldHelper {

	/**
	 * Getter method.
	 *
	 * @param field the field
	 * @param klass the class
	 * @return the method
	 */
	public static Method getterMethod(Field field, Class<?> klass){
		String getter = getterMethodName(field.getName());
		return ReflectionUtils.findMethod(klass, getter);
	}

	/**
	 * Setter method.
	 *
	 * @param field the field
	 * @param klass the class
	 * @return the method
	 */
	public static Method setterMethod(Field field, Class<?> klass){
		String setter = setterMethodName(field.getName());
		return ReflectionUtils.findMethod(klass, setter, field.getType());
	}

	/**
	 * Creates a setter method from name.
	 *
	 * @param name the name of the get-method to create
	 * @return the method's name
	 */
	public static String getterMethodName(String name) {
	    return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	/**
	 * Creates a setter method from name.
	 *
	 * @param name the name of the set-method to create
	 * @return the string
	 */
	public static String setterMethodName(String name) {
	    return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
}
