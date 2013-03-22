package org.adorsys.xlseasy.cbe;

import java.beans.PropertyDescriptor;

import org.adorsys.xlseasy.annotation.SheetColumnObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SheetColumDeclaration.
 */
public class SheetColumDeclaration {

	/** The property descriptor. */
	private PropertyDescriptor propertyDescriptor;
	
	/** The sheet column. */
	private SheetColumnObject sheetColumn;
	
	/** The field klass. */
	private Class<?> fieldKlass;

	/**
	 * Instantiates a new sheet colum declaration.
	 *
	 * @param propertyDescriptor the property descriptor
	 * @param sheetColumn the sheet column
	 * @param fieldKlass the field klass
	 */
	public SheetColumDeclaration(PropertyDescriptor propertyDescriptor,
			SheetColumnObject sheetColumn, Class<?> fieldKlass) {
		super();
		this.propertyDescriptor = propertyDescriptor;
		this.sheetColumn = sheetColumn;
		this.fieldKlass = fieldKlass;
	}

	/**
	 * Gets the property descriptor.
	 *
	 * @return the property descriptor
	 */
	public PropertyDescriptor getPropertyDescriptor() {
		return propertyDescriptor;
	}

	/**
	 * Sets the property descriptor.
	 *
	 * @param propertyDescriptor the new property descriptor
	 */
	public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
		this.propertyDescriptor = propertyDescriptor;
	}

	/**
	 * Gets the sheet column.
	 *
	 * @return the sheet column
	 */
	public SheetColumnObject getSheetColumn() {
		return sheetColumn;
	}

	/**
	 * Sets the sheet column.
	 *
	 * @param sheetColumn the new sheet column
	 */
	public void setSheetColumn(SheetColumnObject sheetColumn) {
		this.sheetColumn = sheetColumn;
	}

	/**
	 * Gets the field klass.
	 *
	 * @return the field klass
	 */
	public Class<?> getFieldKlass() {
		return fieldKlass;
	}

	/**
	 * Sets the field klass.
	 *
	 * @param fieldKlass the new field klass
	 */
	public void setFieldKlass(Class<?> fieldKlass) {
		this.fieldKlass = fieldKlass;
	}
	
	
}
