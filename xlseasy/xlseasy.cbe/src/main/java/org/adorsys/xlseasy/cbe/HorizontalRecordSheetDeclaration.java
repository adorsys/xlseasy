package main.java.org.adorsys.xlseasy.cbe;

import java.beans.PropertyDescriptor;

import org.adorsys.xlseasy.annotation.HorizontalRecordSheetObject;
import org.adorsys.xlseasy.boot.WorkBookSheet;

/**
 * The Class HorizontalRecordSheetDeclaration.
 */
public class HorizontalRecordSheetDeclaration {

	/** The property descriptor. */
	private final PropertyDescriptor propertyDescriptor;
	
	/** The horizontal record sheet. */
	private final HorizontalRecordSheetObject horizontalRecordSheet;
	
	/** The workbook sheet. */
	private final WorkBookSheet workBookSheet;

	/**
	 * Instantiates a new horizontal record sheet declaration.
	 *
	 * @param propertyDescriptor the property descriptor
	 * @param horizontalRecordSheet the horizontal record sheet
	 * @param workBookSheet the work book sheet
	 */
	public HorizontalRecordSheetDeclaration(
			PropertyDescriptor propertyDescriptor,
			HorizontalRecordSheetObject horizontalRecordSheet, WorkBookSheet workBookSheet) {
		super();
		this.propertyDescriptor = propertyDescriptor;
		this.horizontalRecordSheet = horizontalRecordSheet;
		this.workBookSheet = workBookSheet;
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
	 * Gets the horizontal record sheet.
	 *
	 * @return the horizontal record sheet
	 */
	public HorizontalRecordSheetObject getHorizontalRecordSheet() {
		return horizontalRecordSheet;
	}

	/**
	 * Gets the workbook sheet.
	 *
	 * @return the workbook sheet
	 */
	public WorkBookSheet getWorkBookSheet() {
		return workBookSheet;
	}

}
