package org.adorsys.xlseasy.impl.converter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.ICellConverter;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * A parameterized converter is one that can convert classes with 
 * type parameters. This is the case of the SheetConverter, that is used 
 * to link sheets and the CollectionConverter.
 * 
 * A SheetConverter must always now the type of the key class. And shaa not always 
 * assume the presence of the @Key annotation.
 * 
 * @author Francis Pouatcha
 *
 */
public abstract class CollectionTypeConverter implements ICellConverter {

	/**
	 * Used to convert elements of this collection.
	 */
	private final CollectionElementConverter collectionElementConverter;
	
	private final Class<?> typeParameter;
	
	private final String collectionElementSeparator;

	public CollectionTypeConverter(Class<?> typeParameter, CollectionElementConverter collectionElementConverter) {
		super();
		this.typeParameter = typeParameter;
		this.collectionElementConverter = collectionElementConverter;
		this.collectionElementSeparator = collectionElementConverter.getElementSeparator();
	}

	public Class<?> getTypeParameter() {
		return typeParameter;
	}

	public CollectionElementConverter getCollectionElementConverter() {
		return collectionElementConverter;
	}

	/**
	 * Generally in this context a set is a collection of an object of type parameter type.
	 */
	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType,
			ISheetSession<?, ?> session) {
		if (value != null) {
			HSSFCell cell = (HSSFCell) cellObject;
			Class<?> tp = getTypeParameter();

			// check if the type parameter also has a converter. 
			// for this release, we avoid nested collections.
			String concatenated = "";

			Collection<?> col = (Collection<?>) value;
			for (Object object : col) {
				if(object==null) continue;
				String keyValue = collectionElementConverter.getStringForCollection(cellObject, object, tp, session);
				if(StringUtils.isBlank(keyValue)) continue;
				if(keyValue.contains(collectionElementSeparator)){
					throw new SheetSystemException(ErrorCodeSheet.SEPARATOR_CHARACTER_NOT_ALLOWED_IN_KEY_STRING);
				}
				if(StringUtils.isBlank(concatenated)){
					concatenated += keyValue;
				} else {
					concatenated += collectionElementSeparator+keyValue;
				}
			}
			if(StringUtils.isNotBlank(concatenated))
				CellConverter.getConverterForType(String.class).setHSSFCell(cell, concatenated, objectType, session);
		}
	}

	public Object getDataCell(Object cellObject, Class<?> objectType,
			ISheetSession<?, ?> session) throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		String  keys = (String) CellConverter.getConverterForType(String.class).getDataCell(cell, objectType, session);
		if(keys==null) keys="";
		String[] split = keys.split(collectionElementSeparator);
		Collection<Object> col = newCollectionInstance();
		Class<?> tp = getTypeParameter();
		for (String keyValue : split) {
			Object valueFromCollection = collectionElementConverter.getValueFromCollection(cellObject, keyValue, tp, session);
			if(valueFromCollection!=null){
				if(valueFromCollection instanceof String){
					if(StringUtils.isNotBlank((String) valueFromCollection))
						col.add(valueFromCollection);
				} else {
					col.add(valueFromCollection);
				}
			}
		}
		return col;
	}
	
	protected abstract Collection<Object> newCollectionInstance();

	public static CollectionTypeConverter getConverter(Class<?> rawType,
			Class<?> elementType, CollectionElementConverter elementConverter) {
		
		CollectionTypeConverterKey key = new CollectionTypeConverterKey(rawType, elementType, elementConverter);
		
		CollectionTypeConverter converter = converterMap.get(key);
		if(converter!=null) return converter;
		
		if(Set.class.isAssignableFrom(rawType)){
			converter = converterMap.get(new CollectionTypeConverterKey(Set.class, elementType, elementConverter));
			if(converter!=null) return converter;
		} 
		
		if (List.class.isAssignableFrom(rawType)){
			converter = converterMap.get(new CollectionTypeConverterKey(List.class, elementType, elementConverter));
			if(converter!=null) return converter;
		}
		
		if (Collection.class.isAssignableFrom(rawType)){
			converter = converterMap.get(new CollectionTypeConverterKey(Collection.class, elementType, elementConverter));
			if(converter!=null) return converter;
		}
		
		if(Set.class.isAssignableFrom(rawType)){
			converter = new SetCellConverter(elementType, elementConverter);
			converterMap.put(new CollectionTypeConverterKey(Set.class, elementType, elementConverter), converter);
			return converter;
		} else if (List.class.isAssignableFrom(rawType)){
			converter = new ListCellConverter(elementType, elementConverter);
			converterMap.put(new CollectionTypeConverterKey(List.class, elementType, elementConverter), converter);
			return converter;
		} else if (Collection.class.isAssignableFrom(rawType)){
			converter = new SetCellConverter(elementType, elementConverter);
			converterMap.put(new CollectionTypeConverterKey(Collection.class, elementType, elementConverter), converter);
			return converter;
		} else {
			throw new SheetSystemException(ErrorCodeSheet.NO_CONVERTER_FOR_TYPE);
		}
	}
	
	private static Map<CollectionTypeConverterKey, CollectionTypeConverter> converterMap = 
			new HashMap<CollectionTypeConverterKey, CollectionTypeConverter>();

}
