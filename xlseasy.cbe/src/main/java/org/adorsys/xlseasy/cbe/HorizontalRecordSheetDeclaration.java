package org.adorsys.xlseasy.cbe;

import java.beans.PropertyDescriptor;

import org.adorsys.xlseasy.annotation.HorizontalRecordSheetObject;

public class HorizontalRecordSheetDeclaration {

	private PropertyDescriptor propertyDescriptor;
	
	private HorizontalRecordSheetObject horizontalRecordSheet;
	
	private Class<?> recordKlass;

	public HorizontalRecordSheetDeclaration(
			PropertyDescriptor propertyDescriptor,
			HorizontalRecordSheetObject horizontalRecordSheet, Class<?> recordKlass) {
		super();
		this.propertyDescriptor = propertyDescriptor;
		this.horizontalRecordSheet = horizontalRecordSheet;
		this.recordKlass = recordKlass;
	}

	public PropertyDescriptor getPropertyDescriptor() {
		return propertyDescriptor;
	}

	public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
		this.propertyDescriptor = propertyDescriptor;
	}

	public HorizontalRecordSheetObject getHorizontalRecordSheet() {
		return horizontalRecordSheet;
	}

	public void setHorizontalRecordSheet(HorizontalRecordSheetObject horizontalRecordSheet) {
		this.horizontalRecordSheet = horizontalRecordSheet;
	}

	public Class<?> getRecordKlass() {
		return recordKlass;
	}

	public void setRecordKlass(Class<?> recordKlass) {
		this.recordKlass = recordKlass;
	}
}
