package org.adorsys.xlseasy.cbe;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.ICellConverter;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetCellStyleObject;
import org.adorsys.xlseasy.annotation.SheetColumnObject;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.adorsys.xlseasy.boot.WorkBookSheet;
import org.adorsys.xlseasy.impl.converter.CellConverter;
import org.adorsys.xlseasy.impl.converter.CollectionTypeConverter;
import org.adorsys.xlseasy.impl.converter.GenericCollectionElementConverter;
import org.adorsys.xlseasy.impl.converter.SheetConverter;
import org.adorsys.xlseasy.impl.proc.ColumnDescIF;
import org.adorsys.xlseasy.impl.proc.SheetSession;
import org.adorsys.xlseasy.impl.proc.WorkbookStyle;
import org.adorsys.xlseasy.utils.XlseasyUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class ColumnDescCbe implements ColumnDescIF {


	private final SheetColumnObject annoSheetColumn;
	private final SheetCellStyleObject annoSheetColumnStyle;
	private final SheetCellStyleObject annoSheetHeaderStyle;

	/**
	 * e.g (Product.) productName
	 */
	private final String propertyName;

	/**
	 * e.g. "Product Name"
	 */
	private final String xlsColumnLabel;

	private final Class<?> type;

	private final ICellConverter converter;
	
	private final int columnIndex;
	
	private final WorkBookSheet workBookSheet;

	/**
	 * Instantiates a new column desc cbe.
	 *
	 * @param pd the property descriptor
	 * @param sc the sheet column object
	 * @param columnIndex the column index
	 * @param field the field
	 * @param workBookSheet the workbook sheet
	 */
	@SuppressWarnings("unchecked")
	public ColumnDescCbe(PropertyDescriptor pd, SheetColumnObject sc, 
			int columnIndex, Field field, WorkBookSheet workBookSheet) {
		super();
		this.columnIndex = columnIndex;
		this.annoSheetColumn = sc;
		this.annoSheetColumnStyle = annoSheetColumn != null ? annoSheetColumn.columnStyle() : null;
		this.annoSheetHeaderStyle = annoSheetColumn != null ? annoSheetColumn.headerStyle() : null;
		this.propertyName = pd.getName();
		this.workBookSheet = workBookSheet;
		
		this.xlsColumnLabel = annoSheetColumn.columnName();

		this.type = pd.getPropertyType();

		if (annoSheetColumn.converter() != null && annoSheetColumn.converter() != Object.class) {
			if (CellConverter.class.isAssignableFrom(annoSheetColumn.converter())) {
				this.converter = CellConverter.getConverter((Class<CellConverter>)annoSheetColumn.converter());
			} else if (SheetConverter.class.isAssignableFrom(annoSheetColumn.converter())){
				this.converter = SheetConverter.getConverter(type, workBookSheet.getWorkbookCbe().getWorkBookSheet(type).getKeyField());
			} else if (CollectionTypeConverter.class.isAssignableFrom(annoSheetColumn.converter())){
				if(!XlseasyUtils.isCollectionType(field)){
					throw new SheetSystemException(ErrorCodeSheet.WRONG_CONVERTER_CLASS_TYPE).addValue("type", annoSheetColumn.converter());
				}
				Class<?> rawType = XlseasyUtils.extractRawType(field);
				Class<?> elementType = XlseasyUtils.extractElementType(field);
				this.converter = getCollectionTypeConverter(rawType, elementType, workBookSheet);
			} else {
				throw new SheetSystemException(ErrorCodeSheet.WRONG_CONVERTER_CLASS_TYPE).addValue("type", annoSheetColumn.converter());
			}
		} else if (workBookSheet.getWorkbookCbe().getWorkBookSheet(type)!=null &&  
				workBookSheet.getWorkbookCbe().getWorkBookSheet(type).getKeyField()!=null) 
		{
			this.converter = SheetConverter.getConverter(type, workBookSheet.getWorkbookCbe().getWorkBookSheet(type).getKeyField());
		} else if (XlseasyUtils.isCollectionType(field)){
			XlseasyUtils.extractRawType(field);
			Class<?> rawType = XlseasyUtils.extractRawType(field);
			Class<?> elementType = XlseasyUtils.extractElementType(field);
			this.converter = getCollectionTypeConverter(rawType, elementType, workBookSheet);
		} else {
			this.converter = CellConverter.getConverterForType(type);
		} 
	}
	
	private CollectionTypeConverter getCollectionTypeConverter(Class<?> rawType, 
			Class<?> elementType, WorkBookSheet workBookSheet){
		WorkBookSheet referencedBookSheet = workBookSheet.getWorkbookCbe().getWorkBookSheet(elementType);
		if(referencedBookSheet!=null && referencedBookSheet.getKeyField()!=null){
			SheetConverter sheetConverter = SheetConverter.getConverter(elementType, referencedBookSheet.getKeyField());
			return CollectionTypeConverter.getConverter(type, elementType,sheetConverter);
		} else {
			return CollectionTypeConverter.getConverter(type, elementType,
					GenericCollectionElementConverter.genericInstance());
		}
	}
	
	/**
	 * Gets the property's name
	*/
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * Gets the column's label
	*/
	public String getXlsColumnLabel() {
		return xlsColumnLabel;
	}

	public ICellConverter getConverter() {
		return converter;
	}

	/**
	 * Gets the annotation sheet column
	*/
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
	 * Gets the column index
	*/
	public int getColumnIndex() {
		return columnIndex;
	}

	public Class<?> getType() {
		return type;
	}
	
	/**
	 * Copies the cell's value to Bean
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
	
	/**
	 * Copies Bean property's value to cell
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
				// TODO set comment
			}
		}
	}
}
