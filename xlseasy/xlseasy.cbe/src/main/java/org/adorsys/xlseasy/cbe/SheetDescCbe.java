package org.adorsys.xlseasy.cbe;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.FreezePaneObject;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetColumnObject;
import org.adorsys.xlseasy.annotation.SheetFormatter;
import org.adorsys.xlseasy.annotation.SheetObject;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.boot.WorkBookSheet;
import org.adorsys.xlseasy.impl.converter.KeyGenerator;
import org.adorsys.xlseasy.impl.proc.SheetDescIF;
import org.adorsys.xlseasy.impl.proc.SheetSession;
import org.adorsys.xlseasy.utils.ReflectionUtils;
import org.adorsys.xlseasy.utils.XlseasyUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.util.CellRangeAddressList;

/**
 * The Class SheetDescCbe.
 *
 * @param <T> the generic type
 * @param <WT> the generic type
 */
public class SheetDescCbe<T, WT> implements SheetDescIF<T, WT>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6166184265312522317L;
	
	/** The xls column name2desc. */
	private final Map<String, ColumnDescCbe> xlsColumnName2desc = new HashMap<String, ColumnDescCbe>();
    
    /** The property name2desc. */
    private final Map<String, ColumnDescCbe> propertyName2desc = new HashMap<String, ColumnDescCbe>();
    
    /** The column order. */
    private final List<ColumnDescCbe> columnOrder = new ArrayList<ColumnDescCbe>();

    /** The sheet. */
    private final SheetObject sheet;
    
    /** The workbook. */
    private final WorkbookDescCbe<WT> workbook;
    
    /** The key generator. */
    private final KeyGenerator keyGenerator;
    
    /** The sheet index. */
    @SuppressWarnings("unused")
	private final int sheetIndex;
    
    /** The work book sheet. */
    private WorkBookSheet<T> workBookSheet;

    /**
     * Instantiates a new sheet desc cbe.
     *
     * @param workbook the workbook
     * @param workBookSheet the work book sheet
     * @param workbookProperty the workbook property
     * @param sheetIndex the sheet index
     */
    public SheetDescCbe(WorkbookDescCbe<WT> workbook, WorkBookSheet<T> workBookSheet, String workbookProperty, int sheetIndex) {
        super();
        this.workbook = workbook;
        this.sheetIndex = sheetIndex;
        this.workBookSheet = workBookSheet;

        this.sheet = new SheetObject(workBookSheet.getField().getName());
        if(workBookSheet.getKeyField()!=null){
        	this.keyGenerator = new KeyGenerator(workBookSheet.getSheetKlass(), workBookSheet.getKeyField());
        } else {
        	this.keyGenerator = new KeyGenerator(workBookSheet.getSheetKlass());
        }
        
        initColumnDescs();
    }

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#getColumnDescs()
     */
    public List<ColumnDescCbe> getColumnDescs(){
        return Collections.unmodifiableList(columnOrder);
    }

    /**
     * Inits the column descs.
     */
    private void initColumnDescs() {
    	List<SheetColumDeclaration> sheetColumDeclarations = SheetProcessor.processSheet(workBookSheet, workbook);
    	int columnIndex = 0;
    	for (SheetColumDeclaration sheetColumDeclaration : sheetColumDeclarations) {
    		addColumn(sheetColumDeclaration, columnIndex, workBookSheet);
    		columnIndex++;
		}
    }
    
    /**
     * Adds the column.
     *
     * @param sheetColumDeclaration the sheet colum declaration
     * @param columnIndex the column index
     * @param workBookSheet the work book sheet
     */
    private void addColumn(SheetColumDeclaration sheetColumDeclaration,
    		int columnIndex, WorkBookSheet<T> workBookSheet) {
        PropertyDescriptor pd = sheetColumDeclaration.getPropertyDescriptor();
        SheetColumnObject sheetColumn = sheetColumDeclaration.getSheetColumn();
        Field field = workBookSheet.getField(pd.getName());
        if(field==null) throw new SheetSystemException(ErrorCodeSheet.FIELD_WITH_NAME_NOT_FOUND).addValue("fieldName", pd.getName());
        ColumnDescCbe columnDesc = new ColumnDescCbe(pd, sheetColumn, columnIndex,field, workBookSheet);
        columnOrder.add(columnDesc);
        xlsColumnName2desc.put(columnDesc.getXlsColumnLabel(), columnDesc);
        propertyName2desc.put(columnDesc.getPropertyName(), columnDesc);
    }

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#getColumnDescForXlsColumnName(java.lang.String)
     */
    public ColumnDescCbe getColumnDescForXlsColumnName(String xlsColumnName) {
        return xlsColumnName2desc.get(xlsColumnName);
    }

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#getColumnDescForPropertyName(java.lang.String)
     */
    public ColumnDescCbe getColumnDescForPropertyName(String propertyName) {
        return propertyName2desc.get(propertyName);
    }

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#getLabel()
     */
    public String getLabel() {
        return workBookSheet.getField().getName();
    }

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#getSheetData(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public List<T> getSheetData(Object workbookObj) {
        try {
            return new ArrayList<T>((Collection<T>) PropertyUtils.getProperty(workbookObj, workBookSheet.getField().getName()));
        } catch (IllegalAccessException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        } catch (InvocationTargetException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        } catch (NoSuchMethodException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        }
    }

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#setSheetData(java.lang.Object, java.util.List)
     */
    public void setSheetData(Object workbookObj, List<T> records) {
        try {
        	// if the collection property is not null, we can add ellement to the collection
        	// without having to infer the type of the collection. 
        	Collection<T> data = (Collection<T>)PropertyUtils.getProperty(workbookObj, workBookSheet.getField().getName());
        	if (data!=null){
        		data.clear();
        		data.addAll(records);
        	} else {
        		Field field = ReflectionUtils.findField(workbookObj.getClass(), workBookSheet.getField().getName());
        		Class<?> extractRawType = XlseasyUtils.extractRawType(field);
        		if(extractRawType.equals(Collection.class) || extractRawType.equals(List.class)){
            		PropertyUtils.setProperty(workbookObj, workBookSheet.getField().getName(), records);        			
        		} else if(extractRawType.equals(Set.class)){
            		PropertyUtils.setProperty(workbookObj, workBookSheet.getField().getName(), new HashSet<T>(records));        			
        		} else {
        			throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR).addValue(
        					"detail", "Property " +workBookSheet.getField().getName()+ 
        					" must be preinstantiated or from type, Set, List or Collection but not from a concrete type." );
        		}        		
        	}
        } catch (IllegalAccessException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        } catch (InvocationTargetException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        } catch (NoSuchMethodException e) {
            throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e);
        }
    }

    /**
     * load the headers found in the sheet.
     *
     * @param sheet the sheet
     * @return the list
     */
    private List<ColumnDescCbe> loadXlsHeader(HSSFSheet sheet) {
        List<ColumnDescCbe> columnDescs = new ArrayList<ColumnDescCbe>();
        HSSFRow row = sheet.getRow(0);
        Iterator<?> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            HSSFCell cell = (HSSFCell) cellIterator.next();
            String headerValue = cell.getRichStringCellValue().getString();
            ColumnDescCbe columnDesc = xlsColumnName2desc.get(headerValue);
            columnDescs.add(columnDesc);
        }
        return columnDescs;
    }

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#loadAndSetBeanRecords(org.apache.poi.hssf.usermodel.HSSFSheet, java.lang.Object, org.adorsys.xlseasy.annotation.ISheetSession)
     */
    public List<T> loadAndSetBeanRecords(HSSFSheet sheet, Object workbook, ISheetSession<?, ?> session) {
        //if not loaded, load it!
        List<T> sheetData = loadBeanRecords(sheet, session);
        setSheetData(workbook, sheetData);
        return sheetData;
    }

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#loadBeanRecords(org.apache.poi.hssf.usermodel.HSSFSheet, org.adorsys.xlseasy.annotation.ISheetSession)
     */
    public List<T> loadBeanRecords(HSSFSheet sheet, ISheetSession<?, ?> session) {
        List<T> records = new ArrayList<T>();
        List<ColumnDescCbe> loadXlsHeader = loadXlsHeader(sheet);
        int loadXlsHeaderSize = loadXlsHeader.size();
        Iterator<?> rowIterator = sheet.rowIterator();
        if (rowIterator.hasNext())
            //skipp header
            rowIterator.next();

        while (rowIterator.hasNext()) {
            HSSFRow row = (HSSFRow) rowIterator.next();
            T record = newRecordInstance();
            Iterator<?> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                HSSFCell cell = (HSSFCell) cellIterator.next();
                int index = cell.getColumnIndex();
                if (loadXlsHeaderSize <= index) {
                    break;// No additional column descriptor, so no need to continue.
                }
                ColumnDescCbe columnDesc = loadXlsHeader.get(index);
                if (columnDesc != null) {
                    //if no columndesc for index then skip this column
                    columnDesc.copyCellValueToBean(record, cell, session);
                }
            }
            records.add(record);
            session.setObjectByKey(workBookSheet.getSheetKlass(), keyGenerator.getKey(record), record);
        }
        return records;
    }

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#newRecordInstance()
     */
    public T newRecordInstance() {
    	return XlseasyUtils.newInstance(workBookSheet.getSheetKlass());
    }

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#getSheet()
     */
    public SheetObject getSheet() {
        return sheet;
    }

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#createSheet(java.util.Collection, org.adorsys.xlseasy.impl.proc.SheetSession)
     */
    public void createSheet(Collection<?> sheetData, SheetSession<?, ?> session) {
        HSSFSheet sheet = session.getWorkbook().createSheet(getLabel());
        createData(session, sheet, sheetData);
        createHeader(session, sheet);
        formatSheet(sheet);
        addConstraints(sheet);
    }

    /**
     * Format sheet.
     *
     * @param hssfSheet the hssf sheet
     */
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

    /**
     * Creates the header.
     *
     * @param session the session
     * @param sheet the sheet
     */
    protected void createHeader(SheetSession<?, ?> session, HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        List<ColumnDescCbe> columnDescs = getColumnDescs();
        for (int i = 0; i < columnDescs.size(); i++) {
            HSSFCell cell = row.createCell(i);
            ColumnDescCbe columnDesc = columnDescs.get(i);
            columnDesc.setHeaderLabel(cell, session);
            columnDesc.formatHeaderCell(session, cell);
            if (getSheet() != null && getSheet().autoSizeColumns()) {
                sheet.autoSizeColumn((short) i);
            }
        }
        FreezePaneObject freezePane = getSheet().freezePane();
        sheet.createFreezePane(freezePane.colSplit(), freezePane.rowSplit(), freezePane.leftmostColumn(),
                freezePane.topRow());
    }

    /**
     * Creates the data.
     *
     * @param session the session
     * @param sheet the sheet
     * @param sheetData the sheet data
     */
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

    /* (non-Javadoc)
     * @see org.adorsys.xlseasy.impl.proc.SheetDescIF#getRecordClass()
     */
    public Class<T> getRecordClass() {
        return workBookSheet.getSheetKlass();
    }

    /**
     * Adds the constraints.
     *
     * @param sheet the sheet
     */
    protected void addConstraints(HSSFSheet sheet) {
        int index = 0;
        for (ColumnDescCbe c : columnOrder) {
//            Sheet sheetAnnotation = c.getType().getAnnotation(Sheet.class);
            WorkBookSheet referencedSheet = workBookSheet.getWorkbookCbe().getWorkBookSheet(c.getType());//.getKeyField()
            if (referencedSheet != null) {
                SheetDescCbe<?, WT> sheetDesc = workbook.getSheet(c.getType());
                String keyColumnName = sheetDesc.keyGenerator.getKeyColumnName();
                if (keyColumnName != null) {
                    ColumnDescCbe refrerencedKeyColumn = sheetDesc.getColumnDescForPropertyName(keyColumnName);
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

    /**
     * Fill row.
     *
     * @param session the session
     * @param row the row
     * @param bean the bean
     */
    private void fillRow(SheetSession<?, ?> session, HSSFRow row, Object bean) {
        for (int i = 0; i < columnOrder.size(); i++) {
            ColumnDescCbe columnDesc = columnOrder.get(i);
            HSSFCell cell = row.createCell(i);
            columnDesc.copyBeanPropertyValueToCell(bean, cell, session);
            columnDesc.formatDataCell(session, cell);
        }
    }
}
