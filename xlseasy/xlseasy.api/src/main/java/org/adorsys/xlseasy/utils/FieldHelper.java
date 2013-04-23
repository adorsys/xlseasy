package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldHelper {

	public static Method getterMethod(Field field, Class<?> klass){
		String getter = getterMethodName(field.getName());
		return ReflectionUtils.findMethod(klass, getter);
	}

	public static Method setterMethod(Field field, Class<?> klass){
		String setter = setterMethodName(field.getName());
		return ReflectionUtils.findMethod(klass, setter, field.getType());
	}

	public static String getterMethodName(String name)
	{
	    return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	public static String setterMethodName(String name)
	{
	    return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}
}