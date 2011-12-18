package org.adorsys.xlseasy.impl.converter;

import org.adorsys.xlseasy.annotation.ICellConverter;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class SheetConverter implements ICellConverter {

	public Class<?>[] getConveterTypes() {
		return new Class<?>[]{};
	}

	public Object getDataCell(HSSFCell cell, Class<?> objectType, ISheetSession<?, ?> session)
			throws SpreadsheetConverterException {
		String  key = (String) CellConverter.getConverterForType(String.class).getDataCell(cell, objectType, session);
		Object objectByKey = session.getObjectByKey(objectType, key);
		return objectByKey;
	}

	public void setHSSFCell(HSSFCell cell, Object value, Class<?> objectType, ISheetSession<?, ?> session) {
		if (value != null) {
			KeyGenerator keyGenerator = new KeyGenerator(objectType);
			CellConverter.getConverterForType(String.class).setHSSFCell(cell, keyGenerator.getKey(value), objectType, session);
		}
	}

}
