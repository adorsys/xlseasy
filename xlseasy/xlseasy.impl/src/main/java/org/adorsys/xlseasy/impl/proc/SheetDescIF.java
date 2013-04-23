package org.adorsys.xlseasy.impl.proc;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetObject;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface SheetDescIF<T, WT> extends Serializable {

    public List<? extends ColumnDescIF> getColumnDescs();

    public ColumnDescIF getColumnDescForXlsColumnName(String xlsColumnName);

    public ColumnDescIF getColumnDescForPropertyName(String propertyName);

    public String getLabel();

    public List<T> getSheetData(Object workbookObj);

    public void setSheetData(Object workbookObj, List<T> records);

    public List<T> loadAndSetBeanRecords(HSSFSheet sheet, Object workbook, ISheetSession<?, ?> session);

    public List<T> loadBeanRecords(HSSFSheet sheet, ISheetSession<?, ?> session);

    public T newRecordInstance();

    public SheetObject getSheet();

    public void createSheet(Collection<?> sheetData, SheetSession<?, ?> session);

    public Class<T> getRecordClass();
}