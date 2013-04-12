package org.adorsys.xlseasy.boot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class WorkBookSheet.
 *
 * @param <T> the generic type
 */
public class WorkBookSheet<T> {

	/** The key field. */
	private final Field keyField;
	
	/** The field date styles. */
	private final Map<String, String> fieldDateStyles;
	
	/** The field order. */
	private final List<Field> fieldOrder = new ArrayList<Field>();
	
	/** The excluded fields. */
	private final Collection<String> excludedFields = new ArrayList<String>();
	
	/** The sheet's class. */
	private final Class<T> sheetKlass;
	
	/** The field. */
	private final Field field;
	
	/** The workbook cbe. */
	private WorkbookCbe workbookCbe;

	/**
	 * Instantiates a new work book sheet.
	 *
	 * @param field the field
	 * @param fieldOrder the field order
	 * @param excludedFields the excluded fields
	 * @param sheetKlass the sheet's class
	 * @param keyField the key field
	 * @param fieldDateStyles the field date styles
	 */
	public WorkBookSheet(Field field, List<Field> fieldOrder, 
			Collection<String> excludedFields, Class<T> sheetKlass, 
			Field keyField, Map<String, String> fieldDateStyles) {
		super();
		this.field = field;
		this.sheetKlass = sheetKlass;
		this.keyField = keyField;
		this.fieldOrder.addAll(fieldOrder);
		excludedFields.addAll(excludedFields);
		this.fieldDateStyles = fieldDateStyles;
	}

	/**
	 * Gets the sheet's class.
	 *
	 * @return the sheet's class
	 */
	public Class<T> getSheetKlass() {
		return sheetKlass;
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Gets the field order.
	 *
	 * @return the field order
	 */
	public List<Field> getFieldOrder() {
		return fieldOrder;
	}

	/**
	 * Gets the excluded fields.
	 *
	 * @return the excluded fields
	 */
	public Collection<String> getExcludedFields() {
		return excludedFields;
	}

	/**
	 * Gets the key field.
	 *
	 * @return the key field
	 */
	public Field getKeyField() {
		return keyField;
	}

	/**
	 * Gets the field date styles.
	 *
	 * @return the field date styles
	 */
	public Map<String, String> getFieldDateStyles() {
		return fieldDateStyles;
	}

	/**
	 * Gets the workbook cbe.
	 *
	 * @return the workbook cbe
	 */
	public WorkbookCbe getWorkbookCbe() {
		return workbookCbe;
	}
	
	/**
	 * Sets the workbook cbe.
	 *
	 * @param workbookCbe the new workbook cbe
	 */
	public void setWorkbookCbe(WorkbookCbe workbookCbe) {
		this.workbookCbe = workbookCbe;
	}

	/**
	 * Gets the field.
	 *
	 * @param fieldName the field name
	 * @return the field
	 */
	public Field getField(String fieldName){
		List<Field> fieldOrder2 = fieldOrder;
		for (Field field : fieldOrder2) {
			if(fieldName.equals(field.getName())) return field;
		}
		return null;
	}
}
