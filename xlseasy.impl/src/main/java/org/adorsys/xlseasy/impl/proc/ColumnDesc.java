package org.adorsys.xlseasy.impl.proc;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.ICellConverter;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.adorsys.xlseasy.impl.converter.CellConverter;
import org.adorsys.xlseasy.impl.converter.SheetConverter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;

public class ColumnDesc {

	private final SheetColumn annoSheetColumn;
	private final SheetCellStyle annoSheetColumnStyle;
	private final SheetCellStyle annoSheetHeaderStyle;

	/**
	 * e.g (Product.)productName
	 */
	private final String propertyName;

	/**
	 * e.g. "Product Name"
	 */
	private final String xlsColumnLabel;

	private final Class<?> type;

	private final ICellConverter converter;
	
	private final int columnIndex;

	/**
	 * @param annoSheetColumn
	 * @param annoSheetHeaderStyle
	 * @param propertyName
	 * @param xlsColumnLabel
	 * @param columnIndex
	 * @param type
	 * @param converter
	 */
	@SuppressWarnings("unchecked")
	public ColumnDesc(PropertyDescriptor pd, SheetColumn sc, int columnIndex) {
		super();
		this.columnIndex = columnIndex;
		this.annoSheetColumn = sc;
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
			if (!CellConverter.class.isAssignableFrom(annoSheetColumn.converter())) {
				throw new SheetSystemException(ErrorCodeSheet.WRONG_CONVERTER_CLASS_TYPE).addValue("type", annoSheetColumn.converter());
			}
			this.converter = CellConverter.getConverter((Class<CellConverter>)annoSheetColumn.converter());
		} else if (type.getAnnotation(Sheet.class) != null) {
			this.converter = CellConverter.getConverter(SheetConverter.class);
		} else {
			this.converter = CellConverter.getConverterForType(type);
		} 
	}

	public String getPropertyName() {
		return propertyName;
	}

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

	public SheetColumn getAnnoSheetColumn() {
		return annoSheetColumn;
	}

	public SheetCellStyle getAnnoSheetColumnStyle() {
		return annoSheetColumnStyle;
	}

	public SheetCellStyle getAnnoSheetHeaderStyle() {
		return annoSheetHeaderStyle;
	}

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
				// TODO set comment
			}
		}
	}
}