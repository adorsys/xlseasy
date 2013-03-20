package org.adorsys.xlseasy.impl.proc;

import org.adorsys.xlseasy.annotation.ICellConverter;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetCellStyleObject;
import org.adorsys.xlseasy.annotation.SheetColumnObject;
import org.apache.poi.hssf.usermodel.HSSFCell;

public interface ColumnDescIF {

	public String getPropertyName();

	public String getXlsColumnLabel();

	public ICellConverter getConverter();

	public SheetColumnObject getAnnoSheetColumn();

	public SheetCellStyleObject getAnnoSheetColumnStyle();

	public SheetCellStyleObject getAnnoSheetHeaderStyle();

	public int getColumnIndex();

	public Class<?> getType();
	
	public void copyCellValueToBean(Object bean, HSSFCell cell, ISheetSession<?, ?> session);
	
	public void copyBeanPropertyValueToCell(Object bean, HSSFCell cell, ISheetSession<?, ?> session);
	
	public void setHeaderLabel(HSSFCell cell, ISheetSession<?, ?> session);
	
	public void formatDataCell(SheetSession<?, ?> session, HSSFCell cell);
	
	public void formatHeaderCell(SheetSession<?, ?> session, HSSFCell cell);
}