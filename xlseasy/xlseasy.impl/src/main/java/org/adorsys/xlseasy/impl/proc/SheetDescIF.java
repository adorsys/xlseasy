package org.adorsys.xlseasy.impl.proc;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetObject;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * The Interface SheetDescIF.
 *
 * @param <T> the generic type
 * @param <WT> the generic type
 */
public interface SheetDescIF<T, WT> extends Serializable {

    /**
     * Gets the column descs.
     *
     * @return the column descs
     */
    public List<? extends ColumnDescIF> getColumnDescs();

    /**
     * Gets the column desc for xls column name.
     *
     * @param xlsColumnName the xls column name
     * @return the column desc for xls column name
     */
    public ColumnDescIF getColumnDescForXlsColumnName(String xlsColumnName);

    /**
     * Gets the column desc for property name.
     *
     * @param propertyName the property name
     * @return the column desc for property name
     */
    public ColumnDescIF getColumnDescForPropertyName(String propertyName);

    /**
     * Gets the label.
     *
     * @return the label
     */
    public String getLabel();

    /**
     * Gets the sheet data.
     *
     * @param workbookObj the workbook obj
     * @return the sheet data
     */
    public List<T> getSheetData(Object workbookObj);

    /**
     * Sets the sheet data.
     *
     * @param workbookObj the workbook obj
     * @param records the records
     */
    public void setSheetData(Object workbookObj, List<T> records);

    /**
     * Load and set bean records.
     *
     * @param sheet the sheet
     * @param workbook the workbook
     * @param session the session
     * @return the list
     */
    public List<T> loadAndSetBeanRecords(HSSFSheet sheet, Object workbook, ISheetSession<?, ?> session);

    /**
     * Load bean records.
     *
     * @param sheet the sheet
     * @param session the session
     * @return the list
     */
    public List<T> loadBeanRecords(HSSFSheet sheet, ISheetSession<?, ?> session);

    /**
     * New record instance.
     *
     * @return the t
     */
    public T newRecordInstance();

    /**
     * Gets the sheet.
     *
     * @return the sheet
     */
    public SheetObject getSheet();

    /**
     * Creates the sheet.
     *
     * @param sheetData the sheet data
     * @param session the session
     */
    public void createSheet(Collection<?> sheetData, SheetSession<?, ?> session);

    /**
     * Gets the record class.
     *
     * @return the record class
     */
    public Class<T> getRecordClass();
}
