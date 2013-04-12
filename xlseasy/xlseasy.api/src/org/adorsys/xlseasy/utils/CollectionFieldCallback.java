package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.xlseasy.utils.ReflectionUtils.FieldCallback;

/**
 * The Class CollectionFieldCallback.
 */
public class CollectionFieldCallback implements FieldCallback {
	
	/** The fields. */
	private final List<Field> fields = new ArrayList<Field>();
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.utils.ReflectionUtils.FieldCallback#doWith(java.lang.reflect.Field)
	 */
	public void doWith(Field field) throws IllegalArgumentException,
			IllegalAccessException {
		fields.add(field);
	}

	/**
	 * Gets the fields.
	 *
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}
	
}
