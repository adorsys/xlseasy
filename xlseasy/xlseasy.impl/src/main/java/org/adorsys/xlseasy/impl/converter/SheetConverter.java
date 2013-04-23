package org.adorsys.xlseasy.impl.converter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.adorsys.xlseasy.annotation.ICellConverter;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Extending this converter with a key field, because converter shall 
 * not automatically discover key field using @Key annotation. This, there will
 * be no singleton, each sheet will carry proper converter.
 * 
 * @author Francis Pouatcha
 *
 */
public class SheetConverter implements ICellConverter, CollectionElementConverter {

	private final Field keyField;
	private final Class<?> elementType;
	public static final String KEY_SEPARATOR = ",";
	
	private SheetConverter(Class<?> elementType, Field keyField) {
		this.keyField = keyField;
		this.elementType = elementType;
	}

	public Class<?>[] getConveterTypes() {
		return new Class<?>[]{};
	}

	public Class<?> getElementType() {
		return elementType;
	}

	public Field getKeyField() {
		return keyField;
	}

	public Object getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session)
			throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		String  key = (String) CellConverter.getConverterForType(String.class).getDataCell(cell, objectType, session);
		Object objectByKey = session.getObjectByKey(objectType, key);
		return objectByKey;
	}

	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		if (value != null) {
			HSSFCell cell = (HSSFCell) cellObject;
			KeyGenerator keyGenerator = new KeyGenerator(objectType, keyField);
			CellConverter.getConverterForType(String.class).setHSSFCell(cell, keyGenerator.getKey(value), objectType, session);
		}
	}

	public String getStringForCollection(Object cellObject, Object value,
			Class<?> objectType, ISheetSession<?, ?> session) {
		if (value == null) return null;
		KeyGenerator keyGenerator = new KeyGenerator(objectType, keyField);
		return keyGenerator.getKey(value);
	}

	public Object getValueFromCollection(Object cellObject, String keyValue,
			Class<?> objectType, ISheetSession<?, ?> session) {
		Object objectByKey = session.getObjectByKey(objectType, keyValue);
		return objectByKey;
	}

	private static final Map<SheetConverterKey, SheetConverter> REG = 
			new HashMap<SheetConverterKey, SheetConverter>();
	
	public static SheetConverter getConverter(Class<?> elementType, Field keyField) {
		SheetConverterKey key = new SheetConverterKey(elementType, keyField.getName());
		SheetConverter converter = REG.get(key);
		if (converter == null) {
			synchronized (REG) {
				converter = REG.get(key);
				if (converter == null) {
					converter = new SheetConverter(elementType, keyField);
					REG.put(key, converter);
				}
			}
		}
		return converter;
	}

	/**
	 * We assume there is no comma in key strings.
	 */
	public String getElementSeparator() {
		return KEY_SEPARATOR;
	}
}
