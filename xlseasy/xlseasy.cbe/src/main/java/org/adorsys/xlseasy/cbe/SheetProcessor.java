package org.adorsys.xlseasy.cbe;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adorsys.xlseasy.annotation.SheetCellStyleObject;
import org.adorsys.xlseasy.annotation.SheetColumnObject;
import org.adorsys.xlseasy.boot.WorkBookSheet;
import org.adorsys.xlseasy.impl.converter.SheetConverter;

public class SheetProcessor {

	public static <T> List<SheetColumDeclaration> processSheet(
			WorkBookSheet<T> workBookSheet, WorkbookDescCbe<?> workbookDescJpa) {

		List<Field> fields = workBookSheet.getFieldOrder();
		List<SheetColumDeclaration> result = new ArrayList<SheetColumDeclaration>();
		Map<String, String> fieldDateStyles = workBookSheet
				.getFieldDateStyles();

		Class<T> sheetKlass = workBookSheet.getSheetKlass();
		SheetCellStyleObject headerStyle = SheetCellStyleObject.newInstance(
				null, true);

		for (Field field : fields) {
			String fieldName = field.getName();
			String dateStyle = fieldDateStyles.get(fieldName);
			SheetCellStyleObject columnStyle = SheetCellStyleObject
					.newInstance(dateStyle, false);

			SheetColumnObject sheetColumn;

			// Checks if this column holds the reference to another sheet
			if (workbookDescJpa != null
					&& workbookDescJpa.isSheet(field.getType())) {
				sheetColumn = new SheetColumnObject(fieldName,
						SheetConverter.class, headerStyle, columnStyle);
			} else {
				sheetColumn = new SheetColumnObject(fieldName, null,
						headerStyle, columnStyle);
			}

			PropertyDescriptor propertyDescriptor;

			try {
				propertyDescriptor = new PropertyDescriptor(fieldName,
						sheetKlass);
			} catch (IntrospectionException e) {
				throw new IllegalStateException(e);
			}
			SheetColumDeclaration sheetColumDeclaration = new SheetColumDeclaration(
					propertyDescriptor, sheetColumn, field.getType());
			result.add(sheetColumDeclaration);
		}

		return result;
	}
}
