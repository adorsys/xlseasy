package org.adorsys.xlseasy.impl.converter;

public class CollectionTypeConverterKey {
	
	private final Class<?> rawType;
	private final Class<?> typeParameter;
	private final CollectionElementConverter elementConverter;
	
	public CollectionTypeConverterKey(Class<?> rawType,
			Class<?> typeParameter, CollectionElementConverter elementConverter) {
		this.rawType = rawType;
		this.typeParameter = typeParameter;
		this.elementConverter = elementConverter;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CollectionTypeConverterKey other = (CollectionTypeConverterKey) obj;
		return rawType==other.rawType && typeParameter==other.typeParameter &&
				this.elementConverter.equals(other.elementConverter);
	}
}