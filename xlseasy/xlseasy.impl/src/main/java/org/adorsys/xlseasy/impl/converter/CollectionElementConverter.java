package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ISheetSession;

/**
 * The collection element converter is used to help display elements of 
 * a collection as a string and retrieve that same collection from the
 * string.
 * 
 * @author Francis Pouatcha
 */
public interface CollectionElementConverter {

	/**
	 * Turns the string value, that will be concatenated into a larger string
	 * with other elements of the collection, using the collection separator
	 * provided by the collection converter.
	 *
	 * @param cellObject the cell object
	 * @param value the value
	 * @param objectType the object type
	 * @param session the session
	 * @return the string for collection
	 */
	public String getStringForCollection(Object cellObject, Object value, 
			Class<?> objectType, ISheetSession<?, ?> session);
	
	public Object getValueFromCollection(Object cellObject, String stringValue, Class<?> objectType,
			ISheetSession<?, ?> session);
	
	public String getElementSeparator();
}
