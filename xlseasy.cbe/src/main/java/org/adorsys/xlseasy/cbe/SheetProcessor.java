package org.adorsys.xlseasy.cbe;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.adorsys.xlseasy.annotation.SheetCellStyleObject;
import org.adorsys.xlseasy.annotation.SheetColumnObject;
import org.adorsys.xlseasy.impl.converter.SheetConverter;
import org.adorsys.xlseasy.utils.CollectionFieldCallback;
import org.adorsys.xlseasy.utils.CompositeFieldFilter;
import org.adorsys.xlseasy.utils.ExcludeByFieldNameFilter;
import org.adorsys.xlseasy.utils.ExcludeStaticFieldFilter;
import org.adorsys.xlseasy.utils.ReflectionUtils;

public class SheetProcessor {

	public static List<SheetColumDeclaration> 
		processSheet(Class<?> clazz, Collection<String> excludedFields, 
				Map<String, String> fieldDateStyles, WorkbookDescCbe<?> workbookDescJpa) 
	{
		
		// just gather all fields that are not excluded or static.
		CollectionFieldCallback collectingFieldCallback = new CollectionFieldCallback();
		ReflectionUtils.doWithFields(clazz, collectingFieldCallback,
				new CompositeFieldFilter(
						new ExcludeByFieldNameFilter(excludedFields), 
						new ExcludeStaticFieldFilter()));
		
		List<Field> fields = collectingFieldCallback.getFields();
		List<SheetColumDeclaration> result = new ArrayList<SheetColumDeclaration>();
		
		SheetCellStyleObject headerStyle = SheetCellStyleObject.newInstance(null, true);
		for (Field field : fields) {
			String fieldName = field.getName();
			String dateStyle = fieldDateStyles.get(fieldName);
			SheetCellStyleObject columnStyle = SheetCellStyleObject.newInstance(dateStyle, false);
			
			SheetColumnObject sheetColumn;
			// check if this column holds the reference to another sheet
			if(workbookDescJpa!=null && workbookDescJpa.isSheet(field.getType())){
				sheetColumn = new SheetColumnObject(fieldName, SheetConverter.class, headerStyle, columnStyle);
			} else {
				sheetColumn = new SheetColumnObject(fieldName, null, headerStyle, columnStyle);
			}
			PropertyDescriptor propertyDescriptor;
			try {
				propertyDescriptor = new PropertyDescriptor(fieldName, clazz);
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
