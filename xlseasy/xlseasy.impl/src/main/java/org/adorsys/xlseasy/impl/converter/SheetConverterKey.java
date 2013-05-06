package org.adorsys.xlseasy.impl.converter;

import java.io.Serializable;

/**
 * The Class SheetConverterKey.
 */
public class SheetConverterKey implements Serializable {

	private static final long serialVersionUID = -2478435572819302130L;

	private final Class<?> elementType;
	private final String keyFieldName;
	
	public SheetConverterKey(Class<?> elementType, String keyFieldName) {
		super();
		this.elementType = elementType;
		this.keyFieldName = keyFieldName;
	}

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