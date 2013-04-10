package org.adorsys.xlseasy.annotation;

import java.io.Serializable;

/**
 * The Class HorizontalRecordSheetObject.
 */
public class HorizontalRecordSheetObject implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2320848865868612685L;

	/**
	 * Instantiates a new horizontal record sheet object.
	 */
	public HorizontalRecordSheetObject() {
	}

	/**
	 * Instantiates a new horizontal record sheet object.
	 *
	 * @param recordClass the record class
	 * @param label the label
	 */
	public HorizontalRecordSheetObject(Class<?> recordClass, String label) {
		super();
		this.recordClass = recordClass;
		this.label = label;
	}

	/** The record class. */
	Class<?> recordClass;
	
	/**
	 * Record class.
	 *
	 * @return the class
	 */
	public Class<?> recordClass(){
		return recordClass;
	}
	
	/** Returns the mapped sheet name. */
	String label = "";
	
	/**
	 * Label.
	 *
	 * @return the string
	 */
	public String label() {
		return label;
	}
	
}
