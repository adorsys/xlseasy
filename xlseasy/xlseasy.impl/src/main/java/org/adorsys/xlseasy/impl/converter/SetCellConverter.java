package org.adorsys.xlseasy.impl.converter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Class SetCellConverter.
 */
public class SetCellConverter extends CollectionTypeConverter {
			
	/**
	 * Instantiates a new sets the cell converter.
	 *
	 * @param typeParameter the type parameter
	 * @param collectionElementConverter the collection element converter
	 */
	public SetCellConverter(Class<?> typeParameter,CollectionElementConverter collectionElementConverter) {
		super(typeParameter, collectionElementConverter);
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.annotation.ICellConverter#getConveterTypes()
	 */
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Set.class} ;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CollectionTypeConverter#newCollectionInstance()
	 */
	@Override
	protected Collection<Object> newCollectionInstance() {
		return new HashSet<Object>();
	}
}
