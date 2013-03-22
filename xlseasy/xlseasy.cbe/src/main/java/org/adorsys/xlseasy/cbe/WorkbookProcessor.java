package org.adorsys.xlseasy.cbe;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.xlseasy.annotation.HorizontalRecordSheetObject;
import org.adorsys.xlseasy.boot.WorkBookSheet;
import org.adorsys.xlseasy.boot.WorkbookCbe;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkbookProcessor.
 */
public class WorkbookProcessor {

	/**
	 * Process workbook.
	 *
	 * @param clazz the clazz
	 * @param workbookCbe the workbook cbe
	 * @return the list
	 */
	public static List<HorizontalRecordSheetDeclaration> processWorkbook(
			Class<?> clazz, WorkbookCbe workbookCbe) 
	{

		List<WorkBookSheet> workBookSheets = workbookCbe.getWorkBookSheets();			
		List<HorizontalRecordSheetDeclaration> result = new ArrayList<HorizontalRecordSheetDeclaration>(workBookSheets.size());

		for (WorkBookSheet workBookSheet : workBookSheets) {
			Field field = workBookSheet.getField();
			Class<?> sheetKlass = workBookSheet.getSheetKlass();

			String propertyName = field.getName();
			HorizontalRecordSheetObject horizontalRecordSheet = 
					new HorizontalRecordSheetObject(sheetKlass, propertyName);
			PropertyDescriptor propertyDescriptor;
			try {
				propertyDescriptor = new PropertyDescriptor(propertyName,
						clazz);
			} catch (IntrospectionException e) {
				throw new IllegalStateException(e);
			}
			HorizontalRecordSheetDeclaration dekl = new HorizontalRecordSheetDeclaration(
					propertyDescriptor, horizontalRecordSheet, workBookSheet);
			result.add(dekl);
		}

		return result;
	}

}