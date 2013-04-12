package org.adorsys.xlseasy.cbe;

import java.beans.PropertyDescriptor;

import org.adorsys.xlseasy.annotation.SheetColumnObject;

public class SheetColumDeclaration {


	private PropertyDescriptor propertyDescriptor;
	
	private SheetColumnObject sheetColumn;
	
	private Class<?> fieldKlass;

	public SheetColumDeclaration(PropertyDescriptor propertyDescriptor,
			SheetColumnObject sheetColumn, Class<?> fieldKlass) {
		super();
		this.propertyDescriptor = propertyDescriptor;
		this.sheetColumn = sheetColumn;
		this.fieldKlass = fieldKlass;
	}

	public PropertyDescriptor getPropertyDescriptor() {
		return propertyDescriptor;
	}

	public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
		this.propertyDescriptor = propertyDescriptor;
	}

	public SheetColumnObject getSheetColumn() {
		return sheetColumn;
	}

	public void setSheetColumn(SheetColumnObject sheetColumn) {
		this.sheetColumn = sheetColumn;
	}

	public Class<?> getFieldKlass() {
		return fieldKlass;
	}

	public void setFieldKlass(Class<?> fieldKlass) {
		this.fieldKlass = fieldKlass;
	}
	
	
}
