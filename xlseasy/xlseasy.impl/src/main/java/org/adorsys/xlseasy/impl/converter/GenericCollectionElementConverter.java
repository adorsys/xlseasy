package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * Converter that can instantiate an element from a String value. Also provides
 * a toStringValue that returns the String representation and a separator used
 * to concatenate elements.
 * 
 * @author Francis Pouatcha
 * 
 */
public class GenericCollectionElementConverter implements
		CollectionElementConverter {

	private static GenericCollectionElementConverter genericInstance = new GenericCollectionElementConverter();

	public static GenericCollectionElementConverter genericInstance() {
		return genericInstance;
	}

	public String getStringForCollection(Object cellObject, Object value,
			Class<?> objectType, ISheetSession<?, ?> session) {
		if (value == null)
			return null;
		return ConvertUtils.convert(value);
	}

	public Object getValueFromCollection(Object cellObject, String stringValue,
			Class<?> objectType, ISheetSession<?, ?> session) {
		if (stringValue == null)
			return null;
		return ConvertUtils.convert(stringValue, objectType);
	}

	/**
	 * Subclass can override this if they have "," in the string representation.
	 */
	public static final String RECORD_SEPARATOR = ",";

	public String getElementSeparator() {
		return RECORD_SEPARATOR;
	}
}