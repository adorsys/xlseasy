package org.adorsys.xlseasy.impl.proc;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.FreezePaneObject;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetColumn;
import org.adorsys.xlseasy.annotation.SheetFormatter;
import org.adorsys.xlseasy.annotation.SheetObject;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.annotation.filter.AnnotationUtil;
import org.adorsys.xlseasy.impl.converter.KeyGenerator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.util.CellRangeAddressList;

@SuppressWarnings({"unused", "unchecked"})
public class SheetDesc<T, WT> implements SheetDescIF<T, WT>{

    private static final long serialVersionUID = 1L;

    private final Map<String, ColumnDesc> xlsColumnName2desc = new HashMap<String, ColumnDesc>();
    private final Map<String, ColumnDesc> propertyName2desc = new HashMap<String, ColumnDesc>();
    private final List<ColumnDesc> columnOrder = new ArrayList<ColumnDesc>();

    private final Class<T> recordClass;
    private final String label;
    private final String workbookProperty;
    private final SheetObject sheet;
    private final WorkbookDesc<WT> workbook;
    private final KeyGenerator keyGenerator;
    private final int sheetIndex;

    /**
     * @param recordClass
     */
    public SheetDesc(WorkbookDesc<WT> workbook, Class<T> recordClass, String label, String workbookProperty, int sheetIndex) {
        super();
        this.recordClass = recordClass;
        this.workbook = workbook;
        this.sheetIndex = sheetIndex;

        Collection<Annotation> sheet = AnnotationUtil.findClassAnnotations(recordClass, true, Sheet.class);

        // Ordered columns
        if (sheet.size() > 0) {
            this.sheet = new SheetObject((Sheet) sheet.iterator().next());

            if (label == null) {
                label = StringUtils.trimToNull(this.sheet.name());
            }
        } else {
            throw new SheetSystemException(ErrorCodeSheet.CLASS_HAS_NO_SHEET_ANNOTATION).addValue("class", recordClass);
        }
        this.workbookProperty = workbookProperty;
        if (label != null) {
            this.label = label;
        } else if (workbookProperty != null) {
            this.label = workbookProperty;
        } else {
            this.label = recordClass.getCanonicalName();
        }
        this.keyGenerator = new KeyGenerator(recordClass);
        initColumnDescs();
    }

    public List<ColumnDesc> getColumnDescs() {
        return Collections.unmodifiableList(columnOrder);
    }

    private void initColumnDescs() {
        Map<PropertyDescriptor, Map<Class<?>, Annotation>> propertyDescriptorAnnotations = AnnotationUtil
                .findBeanPropertyDescriptorAnnotations(recordClass, true, SheetColumn.class);
        Map<String, PropertyDescriptor> key2PropertyDescriptor = AnnotationUtil.extractPropertyKey2PropertyDescriptor(propertyDescriptorAnnotations.keySet());

        // Ordered columns
        if (sheet.columnOrder().length > 0) {
            String[] value = sheet.columnOrder();
            for (int i = 0; i < value.length; i++) {
                String prop = value[i];
                if (!key2PropertyDescriptor.containsKey(prop)) {
                    throw new SheetSystemException(ErrorCodeSheet.COLUMN_DEFINITION_FROM_COLUMN_ORDER_NOT_FOUND)
                            .addValue("property", prop).addValue("recordClass", recordClass);
                }
                addColumn(propertyDescriptorAnnotations,
                        key2PropertyDescriptor, prop, i);
            }
        } else {
            // alphabetical sort
            TreeSet<String> sortedProps = new TreeSet<String>(key2PropertyDescriptor.keySet());
            int columnIndex = 0;
            for (String prop : sortedProps) {
                addColumn(propertyDescriptorAnnotations,
                        key2PropertyDescriptor, prop, columnIndex);
                columnIndex++;
            }
        }
    }

    private void addColumn(
            Map<PropertyDescriptor, Map<Class<?>, Annotation>> propertyDescriptorAnnotations,
            Map<String, PropertyDescriptor> key2PropertyDescriptor, String prop, int columnIndex) {
        PropertyDescriptor pd = key2PropertyDescriptor.get(prop);
        
        Map<Class<?>, Annotation> map = propertyDescriptorAnnotations.get(pd);
        Field field = AnnotationUtil.findField(recordClass, pd.getName());
        if(field==null) throw new SheetSystemException(ErrorCodeSheet.FIELD_WITH_NAME_NOT_FOUND).addValue("fieldName", pd.getName());
        ColumnDesc columnDesc = new ColumnDesc(pd, (SheetColumn) map.get(SheetColumn.class), columnIndex, field);
        columnOrder.add(columnDesc);
        xlsColumnName2desc.put(columnDesc.getXlsColumnLabel(), columnDesc);
        propertyName2desc.put(columnDesc.getPropertyName(), columnDesc);
    }

    public ColumnDesc getColumnDescForXlsColumnName(String xlsColumnName) {
        return xlsColumnName2desc.get(xlsColumnName);
    }

    public ColumnDesc getColumnDescForPropertyName(String propertyName) {
        return propertyName2desc.get(propertyName);
    }

    public String getLabel() {
        return label;
    }

    public List<T> getSheetData(Object workbookObj) {
        try {
            return (List<T>) PropertyUtils.getProperty(workbookObj, workbookProperty);
        } catch (IllegalAccessException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        } catch (InvocationTargetException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        } catch (NoSuchMethodException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        }
    }

    public void setSheetData(Object workbookObj, List<T> records) {
        try {
            PropertyUtils.setProperty(workbookObj, workbookProperty, records);
        } catch (IllegalAccessException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        } catch (InvocationTargetException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        } catch (NoSuchMethodException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        }
    }

    /**
     * Loads the headers found in the sheet.
     *
     * @param sheet
     * @return
     */
    private List<ColumnDesc> loadXlsHeader(HSSFSheet sheet) {
        List<ColumnDesc> columnDescs = new ArrayList<ColumnDesc>();
        HSSFRow row = sheet.getRow(0);
        Iterator<?> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            HSSFCell cell = (HSSFCell) cellIterator.next();
            String headerValue = cell.getRichStringCellValue().getString();
            ColumnDesc columnDesc = xlsColumnName2desc.get(headerValue);
            columnDescs.add(columnDesc);
        }
        return columnDescs;
    }

    public List<T> loadAndSetBeanRecords(HSSFSheet sheet, Object workbook, ISheetSession<?, ?> session) {
        // if not loaded, loads it!
        List<T> sheetData = loadBeanRecords(sheet, session);
        setSheetData(workbook, sheetData);
        return sheetData;
    }

    public List<T> loadBeanRecords(HSSFSheet sheet, ISheetSession<?, ?> session) {
        List<T> records = new ArrayList<T>();
        List<ColumnDesc> loadXlsHeader = loadXlsHeader(sheet);
        int loadXlsHeaderSize = loadXlsHeader.size();
        Iterator<?> rowIterator = sheet.rowIterator();
        if (rowIterator.hasNext())
            // skips header
            rowIterator.next();

        while (rowIterator.hasNext()) {
            HSSFRow row = (HSSFRow) rowIterator.next();
            T record = newRecordInstance();
            Iterator<?> cellIterator = row.cellIterator();
//			for (ColumnDesc columnDesc : loadXlsHeader) {
//				if (!cellIterator.hasNext()) {
//					break;
//				}
//				HSSFCell cell = (HSSFCell) cellIterator.next();
//				if (columnDesc != null) {
//					columnDesc.copyCellValueToBean(record, cell);
//				}
//			}
            while (cellIterator.hasNext()) {
                HSSFCell cell = (HSSFCell) cellIterator.next();
                int index = cell.getColumnIndex();
                if (loadXlsHeaderSize <= index) {
                    break;// No additional column descriptor, so no need to continue.
                }
                ColumnDesc columnDesc = loadXlsHeader.get(index);
                if (columnDesc != null) {
                    //if no columndesc for index then skip this column
                    columnDesc.copyCellValueToBean(record, cell, session);
                }
            }
            records.add(record);
            session.setObjectByKey(recordClass, keyGenerator.getKey(record), record);
        }
        return records;
    }

    public T newRecordInstance() {
        try {
            return recordClass.newInstance();
        } catch (InstantiationException e) {
            throw new SheetSystemException(ErrorCodeSheet.INSTANCIATE_RECORD_BEAN_FAILED, e).addValue("class",
                    recordClass.getClass().getName());
        } catch (IllegalAccessException e) {
            throw new SheetSystemException(ErrorCodeSheet.INSTANCIATE_RECORD_BEAN_FAILED, e).addValue("class",
                    recordClass.getClass().getName());
        }
    }

    public SheetObject getSheet() {
        return sheet;
    }

    public void createSheet(Collection<?> sheetData, SheetSession<?, ?> session) {
        HSSFSheet sheet = session.getWorkbook().createSheet(getLabel());
        createData(session, sheet, sheetData);
        createHeader(session, sheet);
        formatSheet(sheet);
        addConstraints(sheet);
    }

    private void formatSheet(HSSFSheet hssfSheet) {
        if (sheet != null) {
            Class<? extends SheetFormatter> formatter = sheet.formatter();
            try {
                SheetFormatter formatterInstance = formatter.newInstance();
                formatterInstance.format(hssfSheet);
            } catch (InstantiationException e) {
                throw new SheetSystemException(ErrorCodeSheet.FORMATTER_INSTANCIATION_EXCEPTION, e);
            } catch (IllegalAccessException e) {
                throw new SheetSystemException(ErrorCodeSheet.FORMATTER_INSTANCIATION_EXCEPTION, e);
            }
        }

    }

    // Creates a header into the sheet
    protected void createHeader(SheetSession<?, ?> session, HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        List<ColumnDesc> columnDescs = getColumnDescs();
        for (int i = 0; i < columnDescs.size(); i++) {
            HSSFCell cell = row.createCell(i);
            ColumnDesc columnDesc = columnDescs.get(i);
            columnDesc.setHeaderLabel(cell, session);
            columnDesc.formatHeaderCell(session, cell);
            if (getSheet() != null && getSheet().autoSizeColumns()) {
                sheet.autoSizeColumn((short) i);
                // sets header margin
                sheet.setMargin(HSSFSheet.HeaderMargin, 0.25);
            }
        }
        FreezePaneObject freezePane = getSheet().freezePane();
        sheet.createFreezePane(freezePane.colSplit(), freezePane.rowSplit(), freezePane.leftmostColumn(),
                freezePane.topRow());
    }

    // Saves data into the sheet
    protected void createData(SheetSession<?, ?> session, HSSFSheet sheet,
                              Collection<?> sheetData) {
        if (sheetData != null) {
            int i = 0;
            for (Object object : sheetData) {
                HSSFRow row = sheet.createRow(i + 1);
                fillRow(session, row, object);
                i++;
            }
        }
    }

    public Class<T> getRecordClass() {
        return recordClass;
    }

    protected void addConstraints(HSSFSheet sheet) {
        int index = 0;
        for (ColumnDesc c : columnOrder) {
            Sheet sheetAnnotation = c.getType().getAnnotation(Sheet.class);
            if (sheetAnnotation != null) {
                SheetDesc<?, WT> sheetDesc = (SheetDesc<?, WT>) workbook.getSheet(c.getType());
                String keyColumnName = sheetDesc.keyGenerator.getKeyColumnName();
                if (keyColumnName != null) {
                    ColumnDesc refrerencedKeyColumn = sheetDesc.getColumnDescForPropertyName(keyColumnName);
                    String from = new CellReference(sheetDesc.getLabel(), 1, refrerencedKeyColumn.getColumnIndex(), true, true).formatAsString();
                    String to = new CellReference(65000, refrerencedKeyColumn.getColumnIndex()).formatAsString();
                    String validationRange = from + ":" + to;
                    DVConstraint createFormulaListConstraint = DVConstraint.createFormulaListConstraint(validationRange);

                    CellRangeAddressList constraintRange = new CellRangeAddressList(1, 65000, index, index);
                    HSSFDataValidation hssfDataValidation = new HSSFDataValidation(constraintRange, createFormulaListConstraint);
                    hssfDataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
                    hssfDataValidation.createErrorBox("Constraint violation", "Please select a valid value!");
                    sheet.addValidationData(hssfDataValidation);
                }
            }
            index++;
        }
    }

    // Fills data into sheet's rows with Bean properties and format
    private void fillRow(SheetSession<?, ?> session, HSSFRow row, Object bean) {
        for (int i = 0; i < columnOrder.size(); i++) {
            ColumnDesc columnDesc = columnOrder.get(i);
            HSSFCell cell = row.createCell(i);
            columnDesc.copyBeanPropertyValueToCell(bean, cell, session);
            columnDesc.formatDataCell(session, cell);
        }
    }

}
