package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ICellConverter;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class SheetConverter implements ICellConverter {

	public Class<?>[] getConveterTypes() {
		return new Class<?>[]{};
	}

	public Object getDataCell(Object cellObject, Class<?> objectType, ISheetSession<?, ?> session)
			throws SpreadsheetConverterException {
		HSSFCell cell = (HSSFCell) cellObject;
		String  key = (String) CellConverter.getConverterForType(String.class).getDataCell(cell, objectType, session);
		Object objectByKey = session.getObjectByKey(objectType, key);
		return objectByKey;
	}

	public void setHSSFCell(Object cellObject, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		if (value != null) {
			HSSFCell cell = (HSSFCell) cellObject;
			KeyGenerator keyGenerator = new KeyGenerator(objectType);
			CellConverter.getConverterForType(String.class).setHSSFCell(cell, keyGenerator.getKey(value), objectType, session);
		}
	}

}
