package org.adorsys.xlseasy.annotation;

import java.io.Serializable;

public class HorizontalRecordSheetObject implements Serializable {

	private static final long serialVersionUID = -2320848865868612685L;

	public HorizontalRecordSheetObject() {
	}

	public HorizontalRecordSheetObject(Class<?> recordClass, String label) {
		super();
		this.recordClass = recordClass;
		this.label = label;
	}

	Class<?> recordClass;
	public Class<?> recordClass(){
		return recordClass;
	}
	
	/**
	 * Returns the mapped sheet name.
	 * 
	 * @return the sheet name
	 */
	String label = "";
	public String label() {
		return label;
	}
	
}
