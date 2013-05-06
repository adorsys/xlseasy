package org.adorsys.xlseasy.cbe;

import java.beans.PropertyDescriptor;

import org.adorsys.xlseasy.annotation.HorizontalRecordSheetObject;
import org.adorsys.xlseasy.boot.WorkBookSheet;

@SuppressWarnings("rawtypes")
public class HorizontalRecordSheetDeclaration {

	private final PropertyDescriptor propertyDescriptor;

	private final HorizontalRecordSheetObject horizontalRecordSheet;

	private final WorkBookSheet workBookSheet;

	public HorizontalRecordSheetDeclaration(
			PropertyDescriptor propertyDescriptor,
			HorizontalRecordSheetObject horizontalRecordSheet,
			WorkBookSheet workBookSheet) {
		super();
		this.propertyDescriptor = propertyDescriptor;
		this.horizontalRecordSheet = horizontalRecordSheet;
		this.workBookSheet = workBookSheet;
	}

	public PropertyDescriptor getPropertyDescriptor() {
		return propertyDescriptor;
	}

	public HorizontalRecordSheetObject getHorizontalRecordSheet() {
		return horizontalRecordSheet;
	}

	public WorkBookSheet getWorkBookSheet() {
		return workBookSheet;
	}

}
