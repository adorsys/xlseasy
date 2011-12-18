package org.adorsys.xlseasy.annotation;

import org.apache.poi.hssf.usermodel.HSSFCell;


public interface ICellConverter {

	public abstract void setHSSFCell(HSSFCell cell, Object value,
			Class<?> objectType, ISheetSession<?, ?> session);

	public abstract Object getDataCell(HSSFCell cell, Class<?> objectType, ISheetSession<?, ?> session)
			throws SpreadsheetConverterException;

	public abstract Class<?>[] getConveterTypes();

}