package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

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

}
