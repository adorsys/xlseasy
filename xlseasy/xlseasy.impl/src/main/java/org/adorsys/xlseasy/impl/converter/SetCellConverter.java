package org.adorsys.xlseasy.impl.converter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SetCellConverter extends CollectionTypeConverter {
			
	public SetCellConverter(Class<?> typeParameter,CollectionElementConverter collectionElementConverter) {
		super(typeParameter, collectionElementConverter);
	}

	public Class<?>[] getConveterTypes() {
		return new Class<?>[] {Set.class} ;
	}

	@Override
	protected Collection<Object> newCollectionInstance() {
		return new HashSet<Object>();
	}
}