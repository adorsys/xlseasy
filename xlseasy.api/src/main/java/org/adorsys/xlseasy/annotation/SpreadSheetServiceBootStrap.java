package org.adorsys.xlseasy.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public abstract class SpreadSheetServiceBootStrap {

	@SuppressWarnings("unchecked")
	public static SpreadsheetService createSpreadService(
			Collection<String> excludedFields,
			Map<Class<?>, String> businessKeyFields,
			Map<Class<?>, Map<String, String>> fieldDateStyles)
	{
		Class<? extends SpreadsheetService> loadedClass;
		try {
			loadedClass = (Class<? extends SpreadsheetService>) SpreadsheetService.class.getClassLoader().
					loadClass("org.adorsys.xlseasy.cbe.SpreadsheetServiceCbeImpl");
		} catch (ClassNotFoundException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		}
		Constructor<? extends SpreadsheetService> constructor;
		try {
			constructor = loadedClass.getConstructor(Map.class, Collection.class, Map.class);
		} catch (NoSuchMethodException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		} catch (SecurityException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		}
		SpreadsheetService spreadsheetService;
		try {
			spreadsheetService = constructor.newInstance(fieldDateStyles,excludedFields,businessKeyFields);
		} catch (InstantiationException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		} catch (IllegalAccessException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		} catch (IllegalArgumentException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		} catch (InvocationTargetException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		}
		return spreadsheetService;
	}
}
