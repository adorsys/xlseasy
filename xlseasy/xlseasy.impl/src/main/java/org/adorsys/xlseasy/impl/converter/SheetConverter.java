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
 * @author Francis Pouatcha <info@adorsys.de>
 *
 */
public class SheetConverter implements ICellConverter, CollectionElementConverter {

	/** The key field. */
	private final Field keyField;
	
	/** The element type. */
	private final Class<?> elementType;
	
	/**
	 * Instantiates a new sheet converter.
	 *
	 * @param elementType the element type
	 * @param keyField the key field
	 */
	private SheetConverter(Class<?> elementType, Field keyField) {
		this.keyField = keyField;
		this.elementType = elementType;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.annotation.ICellConverter#getConveterTypes()
	 */
	public Class<?>[] getConveterTypes() {
		return new Class<?>[]{};
	}

	/**
	 * Gets the element type.
	 *
	 * @return the element type
	 */
	public Class<?> getElementType() {
		return elementType;
	}

	/**
	 * Gets the key field.
	 *
	 * @return the key field
	 */
	public Field getKeyField() {
		return keyField;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.annotation.ICellConverter#getDataCell(java.lang.Object, java.lang.Class, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public Object getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session)
			throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		String  key = (String) CellConverter.getConverterForType(String.class).getDataCell(cell, objectType, session);
		Object objectByKey = session.getObjectByKey(objectType, key);
		return objectByKey;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.annotation.ICellConverter#setHSSFCell(java.lang.Object, java.lang.Object, java.lang.Class, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		if (value != null) {
			HSSFCell cell = (HSSFCell) cellObject;
			KeyGenerator keyGenerator = new KeyGenerator(objectType, keyField);
			CellConverter.getConverterForType(String.class).setHSSFCell(cell, keyGenerator.getKey(value), objectType, session);
		}
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CollectionElementConverter#getStringForCollection(java.lang.Object, java.lang.Object, java.lang.Class, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public String getStringForCollection(Object cellObject, Object value,
			Class<?> objectType, ISheetSession<?, ?> session) {
		if (value == null) return null;
		KeyGenerator keyGenerator = new KeyGenerator(objectType, keyField);
		return keyGenerator.getKey(value);
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CollectionElementConverter#getValueFromCollection(java.lang.Object, java.lang.String, java.lang.Class, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public Object getValueFromCollection(Object cellObject, String keyValue,
			Class<?> objectType, ISheetSession<?, ?> session) {
		Object objectByKey = session.getObjectByKey(objectType, keyValue);
		return objectByKey;
	}

	/** The Constant REG. */
	private static final Map<SheetConverterKey, SheetConverter> REG = 
			new HashMap<SheetConverterKey, SheetConverter>();
	
	/**
	 * Gets the converter.
	 *
	 * @param elementType the element type
	 * @param keyField the key field
	 * @return the converter
	 */
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
	public static final String KEY_SEPARATOR = ",";
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CollectionElementConverter#getElementSeparator()
	 */
	public String getElementSeparator() {
		return KEY_SEPARATOR;
	}
}