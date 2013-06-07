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

@SuppressWarnings("unchecked")
public class ColumnDesc implements ColumnDescIF { 

	private final SheetColumnObject annoSheetColumn;
	private final SheetCellStyleObject annoSheetColumnStyle;
	private final SheetCellStyleObject annoSheetHeaderStyle;

	/**
	 * The column's property name
	 * 
	 * e.g (Product.) productName
	 */
	private final String propertyName;

	/**
	 * The column's label
	 * 
	 * e.g. "Product Name"
	 */
	private final String xlsColumnLabel;

	private final Class<?> type;
	
	private final ICellConverter converter;

	/** The column's index. */
	private final int columnIndex;

	
	/**
	 * Instantiates a new column desc.
	 *
	 * @param pd the property's descriptor
	 * @param sc the sheet's column
	 * @param columnIndex the column's index
	 * @param field the field
	 */
	public ColumnDesc(PropertyDescriptor pd, SheetColumn sc, int columnIndex, Field field) {
		super();
		this.columnIndex = columnIndex;
		this.annoSheetColumn = new SheetColumnObject(sc);
		this.annoSheetColumnStyle = (annoSheetColumn != null) ? annoSheetColumn.columnStyle() : null;
		this.annoSheetHeaderStyle = (annoSheetColumn != null) ? annoSheetColumn.headerStyle() : null;
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
	
	private SheetConverter initSheetConverter(){
		Collection<Field> keyFields = AnnotationUtil.findFieldsByAnnotation(this.type, true, Key.class);
		if(keyFields.isEmpty()){
			throw new SheetSystemException(ErrorCodeSheet.REFERENCED_SHEET_DOES_NOT_PROVIDE_KEY_ANNOTATION);
		}
		return SheetConverter.getConverter(type, keyFields.iterator().next());
	}
	
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

	/**
	 * Gets the column's property name.
	 * 
	 * @return the column's property name
	 * */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * Gets the column's label.
	 * 
	 * @return the column's label
	 * */
	public String getXlsColumnLabel() {
		return xlsColumnLabel;
	}
	
	private static String convertPropertyNameToColumnName(String fieldName) {
		StringBuilder sb = new StringBuilder(fieldName);
		sb.replace(0, 1, String.valueOf(Character.toUpperCase(fieldName
				.charAt(0))));
		fieldName = sb.toString();
		return fieldName;
	}

	public ICellConverter getConverter() {
		return converter;
	}

	public SheetColumnObject getAnnoSheetColumn() {
		return annoSheetColumn;
	}

	public SheetCellStyleObject getAnnoSheetColumnStyle() {
		return annoSheetColumnStyle;
	}

	public SheetCellStyleObject getAnnoSheetHeaderStyle() {
		return annoSheetHeaderStyle;
	}

	/**
	 * Gets the column's index.
	 * 
	 * @return the column's index
	 * */
	public int getColumnIndex() {
		return columnIndex;
	}

	public Class<?> getType() {
		return type;
	}
	
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
	
	public void setHeaderLabel(HSSFCell cell, ISheetSession<?, ?> session) {
		ICellConverter c = CellConverter.getConverterForType(String.class);
		c.setHSSFCell(cell, xlsColumnLabel, String.class, session);
	}
	
	public void formatDataCell(SheetSession<?, ?> session, HSSFCell cell) {
		if (annoSheetColumn.columnStyle() != null) {
			WorkbookStyle cachedStyle = session.getWorkbookStyle(annoSheetColumn, annoSheetColumn.columnStyle());
			cachedStyle.applyFormat(cell);
		}
		
	}
	
	public void formatHeaderCell(SheetSession<?, ?> session, HSSFCell cell) {
		if (annoSheetColumn.hidden()) {
			cell.getSheet().setColumnHidden(cell.getColumnIndex(), true);
		}
		if (annoSheetColumn.headerStyle() != null) {
			WorkbookStyle cachedStyle = session.getWorkbookStyle(annoSheetColumn, annoSheetColumn.headerStyle());
			cachedStyle.applyFormat(cell);
			if (StringUtils.isNotEmpty(annoSheetColumn.headerComment())) {
				// TODO sets comment
			}
		}
	}
}