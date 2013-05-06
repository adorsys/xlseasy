package org.adorsys.xlseasy.impl.converter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class SetCellConverter.
 */
public class SetCellConverter extends CollectionTypeConverter {

	/**
	 * Instantiates a new cell converter.
	 */
	public SetCellConverter(Class<?> typeParameter,
			CollectionElementConverter collectionElementConverter) {
		super(typeParameter, collectionElementConverter);
	}

	/**
	 * Gets the converter's type. In this case, Set.
	 * */
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] { Set.class };
	}

	/**
	 * Instantiates a new HashSet<Object>
	 * */
	@Override
	protected Collection<Object> newCollectionInstance() {
		return new HashSet<Object>();
	}
}