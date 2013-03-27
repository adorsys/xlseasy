package org.adorsys.xlseasy.impl.converter;

import java.io.Serializable;

/**
 * The Class SheetConverterKey.
 */
public class SheetConverterKey implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2478435572819302130L;

	/** The element type. */
	private final Class<?> elementType;
	
	/** The key field name. */
	private final String keyFieldName;
	
	/**
	 * Instantiates a new sheet converter key.
	 *
	 * @param elementType the element type
	 * @param keyFieldName the key field name
	 */
	public SheetConverterKey(Class<?> elementType, String keyFieldName) {
		super();
		this.elementType = elementType;
		this.keyFieldName = keyFieldName;
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
		SheetConverterKey other = (SheetConverterKey) obj;
		return elementType==other.elementType && keyFieldName.equals(other.keyFieldName);
	}
}
