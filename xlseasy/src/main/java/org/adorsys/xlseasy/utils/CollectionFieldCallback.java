package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.xlseasy.utils.ReflectionUtils.FieldCallback;


public class CollectionFieldCallback implements FieldCallback {
	
	private final List<Field> fields = new ArrayList<Field>();
	
	public void doWith(Field field) throws IllegalArgumentException,
			IllegalAccessException {
		fields.add(field);
	}

	public List<Field> getFields() {
		return fields;
	}
	
}
