package org.adorsys.xlseasy.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.SheetSystemException;

public abstract class XlseasyUtils {

	public static Class<?> extractElementType(Field field){
		Type genericType = field.getGenericType();
		if (!(genericType instanceof ParameterizedType)) {
			return null;
		}
		ParameterizedType pt = (ParameterizedType) genericType;
		Type[] actualTypeArguments = pt.getActualTypeArguments();
		if (actualTypeArguments.length != 1)
			return null;
			// only process paramerized types with one type arg
		return (Class<?>) actualTypeArguments[0];
	}
	
	public static Class<?> extractRawType(Field field){
		Type genericType = field.getGenericType();
		if (!(genericType instanceof ParameterizedType)) {
			return null;
		}
		
		ParameterizedType pt = (ParameterizedType) genericType;
		return (Class<?>) pt.getRawType();
	}
	
	public static final boolean isCollectionType(Field field){
		Class<?> rawType = extractRawType(field);
		if(rawType==null) return false;
		return Collection.class.isAssignableFrom(rawType);
	}
	
	public static List<Field> readWorkbookFields(Class<?> clazz) {

		CompositeFieldFilter compositeFieldFilter = new CompositeFieldFilter(
				new CollectionFieldFilter(), 
				new ExcludeStaticFieldFilter());
		
		// just gather all fields that are collections.
		CollectionFieldCallback collectingFieldCallback = new CollectionFieldCallback();
		ReflectionUtils.doWithFields(clazz, collectingFieldCallback,compositeFieldFilter);

		return collectingFieldCallback.getFields();
	}	

	public static List<Field> readSheetFields(Class<?> clazz, Collection<String> excludedFields) {
		CollectionFieldCallback collectingFieldCallback = new CollectionFieldCallback();
		ReflectionUtils.doWithFields(clazz, collectingFieldCallback,
				new CompositeFieldFilter(
						new ExcludeByFieldNameFilter(excludedFields), 
						new ExcludeStaticFieldFilter()));
		
		return collectingFieldCallback.getFields();
	}	
	
	public static <T> Constructor<T> getConstructor(Class<T> klass, Class<?>... paramTypes){
		try {
			return klass.getConstructor(paramTypes);
		} catch (NoSuchMethodException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		} catch (SecurityException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		}
	}
	
	public static <T> T newInstance(Constructor<T> constructor, Object...objects){
		try {
			return constructor.newInstance(objects);
		} catch (InstantiationException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		} catch (IllegalAccessException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		} catch (IllegalArgumentException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		} catch (InvocationTargetException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		}
	}

	public static <T> T newInstance(Class<T> klass){
		try {
			return klass.newInstance();
		} catch (InstantiationException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		} catch (IllegalAccessException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		} catch (IllegalArgumentException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		}
	}
}
