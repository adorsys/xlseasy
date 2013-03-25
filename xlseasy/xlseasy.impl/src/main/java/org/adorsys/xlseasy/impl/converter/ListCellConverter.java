package org.adorsys.xlseasy.impl.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Class ListCellConverter.
 */
public class ListCellConverter extends CollectionTypeConverter {
			
	/**
	 * Instantiates a new list cell converter.
	 *
	 * @param typeParameter the type parameter
	 * @param collectionElementConverter the collection element converter
	 */
	public ListCellConverter(Class<?> typeParameter,CollectionElementConverter collectionElementConverter) {
		super(typeParameter, collectionElementConverter);
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.annotation.ICellConverter#getConveterTypes()
	 */
	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {List.class} ;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.CollectionTypeConverter#newCollectionInstance()
	 */
	@Override
	protected Collection<Object> newCollectionInstance() {
		return new ArrayList<Object>();
	}
}
