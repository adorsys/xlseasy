package org.adorsys.xlseasy.cbe;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.xlseasy.annotation.HorizontalRecordSheetObject;
import org.adorsys.xlseasy.utils.CollectionFieldCallback;
import org.adorsys.xlseasy.utils.CollectionFieldFilter;
import org.adorsys.xlseasy.utils.CompositeFieldFilter;
import org.adorsys.xlseasy.utils.ExcludeStaticFieldFilter;
import org.adorsys.xlseasy.utils.ReflectionUtils;
import org.adorsys.xlseasy.utils.XlseasyUtils;

public class WorkbookProcessor {

	public static List<HorizontalRecordSheetDeclaration> processWorkbook(
			Class<?> clazz) 
	{

		List<Field> fields = XlseasyUtils.readWorkbookFields(clazz);
		List<HorizontalRecordSheetDeclaration> result = new ArrayList<HorizontalRecordSheetDeclaration>();

		for (Field field : fields) {
			Class<?> sheetKlass = XlseasyUtils.extractElementType(field);
			if(sheetKlass==null) continue;

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
					propertyDescriptor, horizontalRecordSheet, sheetKlass);
			result.add(dekl);
		}

		return result;
	}

}