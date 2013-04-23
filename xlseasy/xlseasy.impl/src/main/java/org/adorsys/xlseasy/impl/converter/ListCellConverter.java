package org.adorsys.xlseasy.impl.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListCellConverter extends CollectionTypeConverter {
			
	public ListCellConverter(Class<?> typeParameter,CollectionElementConverter collectionElementConverter) {
		super(typeParameter, collectionElementConverter);
	}

	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {List.class} ;
	}

	@Override
	protected Collection<Object> newCollectionInstance() {
		return new ArrayList<Object>();
	}
}