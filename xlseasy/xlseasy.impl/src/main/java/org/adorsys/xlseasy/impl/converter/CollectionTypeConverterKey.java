package org.adorsys.xlseasy.impl.converter;

// TODO: Auto-generated Javadoc
/**
 * The Class CollectionTypeConverterKey.
 */
public class CollectionTypeConverterKey {
	
	/** The raw type. */
	private final Class<?> rawType;
	
	/** The type parameter. */
	private final Class<?> typeParameter;
	
	/** The element converter. */
	private final CollectionElementConverter elementConverter;
	
	/**
	 * Instantiates a new collection type converter key.
	 *
	 * @param rawType the raw type
	 * @param typeParameter the type parameter
	 * @param elementConverter the element converter
	 */
	public CollectionTypeConverterKey(Class<?> rawType,
			Class<?> typeParameter, CollectionElementConverter elementConverter) {
		this.rawType = rawType;
		this.typeParameter = typeParameter;
		this.elementConverter = elementConverter;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
