package org.adorsys.xlseasy.impl.proc;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.ICellConverter;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.Key;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetCellStyleObject;
import org.adorsys.xlseasy.annotation.SheetColumn;
import org.adorsys.xlseasy.annotation.SheetColumnObject;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.adorsys.xlseasy.annotation.filter.AnnotationUtil;
import org.adorsys.xlseasy.impl.converter.CellConverter;
import org.adorsys.xlseasy.impl.converter.CollectionTypeConverter;
import org.adorsys.xlseasy.impl.converter.GenericCollectionElementConverter;
import org.adorsys.xlseasy.impl.converter.SheetConverter;
import org.adorsys.xlseasy.utils.KeyAnnotationUtils;
import org.adorsys.xlseasy.utils.XlseasyUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * The Class ColumnDesc.
 */
public class ColumnDesc implements ColumnDescIF { 

	/** The anno sheet column. */
	private final SheetColumnObject annoSheetColumn;
	
	/** The anno sheet column style. */
	private final SheetCellStyleObject annoSheetColumnStyle;
	
	/** The anno sheet header style. */
	private final SheetCellStyleObject annoSheetHeaderStyle;

	/**
	 * e.g (Product.)productName
	 */
	private final String propertyName;

	/**
	 * e.g. "Product Name"
	 */
	private final String xlsColumnLabel;

	/** The type. */
	private final Class<?> type;

	/** The converter. */
	private final ICellConverter converter;
	
	/** The column index. */
	private final int columnIndex;

	/**
	 * Instantiates a new column desc.
	 *
	 * @param pd the pd
	 * @param sc the sc
	 * @param columnIndex the column index
	 * @param field the field
	 */
	@SuppressWarnings("unchecked")
	public ColumnDesc(PropertyDescriptor pd, SheetColumn sc, int columnIndex, Field field) {
		super();
		this.columnIndex = columnIndex;
		this.annoSheetColumn = new SheetColumnObject(sc);
		this.annoSheetColumnStyle = annoSheetColumn != null ? annoSheetColumn.columnStyle() : null;
		this.annoSheetHeaderStyle = annoSheetColumn != null ? annoSheetColumn.headerStyle() : null;
		this.propertyName = pd.getName();
		
		String columnName = annoSheetColumn != null ? annoSheetColumn
				.columnName()
				: "";
		if (!"".equals(columnName)) {
			/*
			 * "Product Name"
			 */
			this.xlsColumnLabel = columnName;
		} else {
			/*
			 * "Productname"
			 */
			this.xlsColumnLabel = convertPropertyNameToColumnName(propertyName);
		}
		this.type = pd.getPropertyType();
		if (annoSheetColumn.converter() != null && annoSheetColumn.converter() != Object.class) {
			if (CellConverter.class.isAssignableFrom(annoSheetColumn.converter())) {
				this.converter = CellConverter.getConverter((Class<CellConverter>)annoSheetColumn.converter());
			} else if (SheetConverter.class.isAssignableFrom(annoSheetColumn.converter())){
				this.converter = initSheetConverter();
			} else if (CollectionTypeConverter.class.isAssignableFrom(annoSheetColumn.converter())){
				if(!XlseasyUtils.isCollectionType(field)){
					throw new SheetSystemException(ErrorCodeSheet.WRONG_CONVERTER_CLASS_TYPE).addValue("type", annoSheetColumn.converter());
				}
				Class<?> rawType = XlseasyUtils.extractRawType(field);
				Class<?> elementType = XlseasyUtils.extractElementType(field);
				this.converter = getCollectionTypeConverter(rawType, elementType);
			} else {
				throw new SheetSystemException(ErrorCodeSheet.WRONG_CONVERTER_CLASS_TYPE).addValue("type", annoSheetColumn.converter());
			}
		} else if (type.getAnnotation(Sheet.class) != null) {
			this.converter = initSheetConverter();
		} else if (XlseasyUtils.isCollectionType(field)){
			XlseasyUtils.extractRawType(field);
			Class<?> rawType = XlseasyUtils.extractRawType(field);
			Class<?> elementType = XlseasyUtils.extractElementType(field);
			this.converter = getCollectionTypeConverter(rawType, elementType);
		} else {
			this.converter = CellConverter.getConverterForType(type);
		} 
	}
	
	/**
	 * Inits the sheet converter.
	 *
	 * @return the sheet converter
	 */
	private SheetConverter initSheetConverter(){
		Collection<Field> keyFields = AnnotationUtil.findFieldsByAnnotation(this.type, true, Key.class);
		if(keyFields.isEmpty()){
			throw new SheetSystemException(ErrorCodeSheet.REFERENCED_SHEET_DOES_NOT_PROVIDE_KEY_ANNOTATION);
		}
		return SheetConverter.getConverter(type, keyFields.iterator().next());
	}
	
	/**
	 * Gets the collection type converter.
	 *
	 * @param rawType the raw type
	 * @param elementType the element type
	 * @return the collection type converter
	 */
	private CollectionTypeConverter getCollectionTypeConverter(Class<?> rawType, Class<?> elementType){
		Field keyField = KeyAnnotationUtils.extractKeyField(elementType);
		if(keyField!=null){
			SheetConverter sheetConverter = SheetConverter.getConverter(elementType, keyField);
			return CollectionTypeConverter.getConverter(type, elementType,sheetConverter);
		} else {
			return CollectionTypeConverter.getConverter(type, elementType,
					GenericCollectionElementConverter.genericInstance());
		}
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#getPropertyName()
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#getXlsColumnLabel()
	 */
	public String getXlsColumnLabel() {
		return xlsColumnLabel;
	}
	
	/**
	 * Convert property name to column name.
	 *
	 * @param fieldName the field name
	 * @return the string
	 */
	private static String convertPropertyNameToColumnName(String fieldName) {
		StringBuilder sb = new StringBuilder(fieldName);
		sb.replace(0, 1, String.valueOf(Character.toUpperCase(fieldName
				.charAt(0))));
		fieldName = sb.toString();
		return fieldName;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#getConverter()
	 */
	public ICellConverter getConverter() {
		return converter;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#getAnnoSheetColumn()
	 */
	public SheetColumnObject getAnnoSheetColumn() {
		return annoSheetColumn;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#getAnnoSheetColumnStyle()
	 */
	public SheetCellStyleObject getAnnoSheetColumnStyle() {
		return annoSheetColumnStyle;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#getAnnoSheetHeaderStyle()
	 */
	public SheetCellStyleObject getAnnoSheetHeaderStyle() {
		return annoSheetHeaderStyle;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#getColumnIndex()
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#getType()
	 */
	public Class<?> getType() {
		return type;
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#copyCellValueToBean(java.lang.Object, org.apache.poi.hssf.usermodel.HSSFCell, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public void copyCellValueToBean(Object bean, HSSFCell cell, ISheetSession<?, ?> session) {
		try {
			PropertyUtils.setProperty(bean, propertyName, converter.getDataCell(cell, type, session));
		} catch (IllegalAccessException e) {
			throw new SheetSystemException(ErrorCodeSheet.STORE_BEAN_DATA_ERROR, e)
			.addValue("class", type.getName()).addValue("property", propertyName);
		} catch (InvocationTargetException e) {
			throw new SheetSystemException(ErrorCodeSheet.STORE_BEAN_DATA_ERROR, e)
			.addValue("class", type.getName()).addValue("property", propertyName);
		} catch (NoSuchMethodException e) {
			throw new SheetSystemException(ErrorCodeSheet.STORE_BEAN_DATA_ERROR, e)
			.addValue("class", type.getName()).addValue("property", propertyName);
		} catch (SpreadsheetConverterException e) {
			throw new SheetSystemException(e.getErrorCode(), e)
			.addValue("class", type.getName()).addValue("property", propertyName);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#copyBeanPropertyValueToCell(java.lang.Object, org.apache.poi.hssf.usermodel.HSSFCell, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public void copyBeanPropertyValueToCell(Object bean, HSSFCell cell, ISheetSession<?, ?> session) {
		try {
			converter.setHSSFCell(cell, PropertyUtils.getProperty(bean, propertyName), type, session);
		} catch (IllegalAccessException e) {
			throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e)
			.addValue("class", type.getName()).addValue("property", propertyName);
		} catch (InvocationTargetException e) {
			throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e)
			.addValue("class", type.getName()).addValue("property", propertyName);
		} catch (NoSuchMethodException e) {
			throw new SheetSystemException(ErrorCodeSheet.READ_BEAN_DATA_ERROR, e)
			.addValue("class", type.getName()).addValue("property", propertyName);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#setHeaderLabel(org.apache.poi.hssf.usermodel.HSSFCell, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public void setHeaderLabel(HSSFCell cell, ISheetSession<?, ?> session) {
		ICellConverter c = CellConverter.getConverterForType(String.class);
		c.setHSSFCell(cell, xlsColumnLabel, String.class, session);
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#formatDataCell(org.adorsys.xlseasy.impl.proc.SheetSession, org.apache.poi.hssf.usermodel.HSSFCell)
	 */
	public void formatDataCell(SheetSession<?, ?> session, HSSFCell cell) {
		if (annoSheetColumn.columnStyle() != null) {
			WorkbookStyle cachedStyle = session.getWorkbookStyle(annoSheetColumn, annoSheetColumn.columnStyle());
			cachedStyle.applyFormat(cell);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ColumnDescIF#formatHeaderCell(org.adorsys.xlseasy.impl.proc.SheetSession, org.apache.poi.hssf.usermodel.HSSFCell)
	 */
	public void formatHeaderCell(SheetSession<?, ?> session, HSSFCell cell) {
		if (annoSheetColumn.hidden()) {
			cell.getSheet().setColumnHidden(cell.getColumnIndex(), true);
		}
		if (annoSheetColumn.headerStyle() != null) {
			WorkbookStyle cachedStyle = session.getWorkbookStyle(annoSheetColumn, annoSheetColumn.headerStyle());
			cachedStyle.applyFormat(cell);
			if (StringUtils.isNotEmpty(annoSheetColumn.headerComment())) {
				// TODO set comment
			}
		}
	}
}