package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * Converter that can instantiate an element from a String value. Also
 * provides a toStringValue that returns the string representation and 
 * a sparator used to concatenate elements.
 * 
 * @author Francis Pouatcha <info@adorsys.de>
 *
 */
public class GenericCollectionElementConverter implements
		CollectionElementConverter {
	
	/** The generic instance. */
	private static GenericCollectionElementConverter genericInstance = new GenericCollectionElementConverter();
	
	/**
	 * Generic instance.
	 *
	 * @return the generic collection element converter
	 */
	public static GenericCollectionElementConverter genericInstance(){
		return genericInstance;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CollectionElementConverter#getStringForCollection(java.lang.Object, java.lang.Object, java.lang.Class, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public String getStringForCollection(Object cellObject, Object value,
			Class<?> objectType, ISheetSession<?, ?> session) {
		if(value==null) return null;
		return ConvertUtils.convert(value);
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CollectionElementConverter#getValueFromCollection(java.lang.Object, java.lang.String, java.lang.Class, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public Object getValueFromCollection(Object cellObject, String stringValue,
			Class<?> objectType, ISheetSession<?, ?> session)  
	{
		if(stringValue==null) return null;
		return ConvertUtils.convert(stringValue, objectType);
	}

	/**
	 * Subclass can override this if they have "," in the string representation.
	 */
	public static final String RECORD_SEPARATOR = ",";
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CollectionElementConverter#getElementSeparator()
	 */
	public String getElementSeparator() {
		return RECORD_SEPARATOR;
	}
}
