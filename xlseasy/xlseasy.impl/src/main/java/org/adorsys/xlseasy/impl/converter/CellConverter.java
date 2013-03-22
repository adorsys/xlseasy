package org.adorsys.xlseasy.impl.converter;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.ICellConverter;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.annotation.SpreadsheetConverterException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;

// TODO: Auto-generated Javadoc
/**
 * The Class CellConverter.
 *
 * @version $Id: $
 * @author Sandro Sonntag <info@adorsys.de>
 */
public abstract class CellConverter implements ICellConverter {
	
	
	/** The Constant REG. */
	private static final Map<Class<?>, ICellConverter> REG = new HashMap<Class<?>, ICellConverter>();

	/** The Constant TYPE2CONVERTER. */
	private static final Map<Class<?>, ICellConverter> TYPE2CONVERTER = new HashMap<Class<?>, ICellConverter>();
	
	static {
		registerDefaultType(DateCellConverter.class);
		registerDefaultType(CalendarCellConverter.class);
		registerDefaultType(BooleanCellConverter.class);
		registerDefaultType(ShortCellConverter.class);
		registerDefaultType(IntegerCellConverter.class);
		registerDefaultType(LongCellConverter.class);
		registerDefaultType(FloatCellConverter.class);
		registerDefaultType(DoubleCellConverter.class);
		registerDefaultType(StringCellConverter.class);
		registerDefaultType(EnumCellConverter.class);
		registerDefaultType(BigDecimaCellConverter.class);
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.ICellConverter#setHSSFCell(org.apache.poi.hssf.usermodel.HSSFCell, java.lang.Object, java.lang.Class)
	 */
	public abstract void setHSSFCell(Object cell, Object value, Class<?> objectType, ISheetSession<?, ?> session);
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.ICellConverter#getDataCell(org.apache.poi.hssf.usermodel.HSSFCell, java.lang.Class)
	 */
	public abstract Object getDataCell(Object cell, Class<?> objectType, ISheetSession<?, ?> session) throws SpreadsheetConverterException;
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.converter.ICellConverter#getConveterTypes()
	 */
	public abstract Class<?>[] getConveterTypes();
	
	/**
	 * Register default type.
	 *
	 * @param converterClazz the converter clazz
	 */
	private static void registerDefaultType(Class<? extends ICellConverter> converterClazz) {
		ICellConverter converter = getConverter(converterClazz);
		Class<?>[] conveterTypes = converter.getConveterTypes();
		for (int i = 0; i < conveterTypes.length; i++) {
			TYPE2CONVERTER.put(conveterTypes[i], converter);			
		}
	}
	
	/**
	 * Gets the converter.
	 *
	 * @param <C> the generic type
	 * @param clazz the clazz
	 * @return the converter
	 */
	@SuppressWarnings("unchecked")
	public static <C extends ICellConverter> C getConverter(Class<C> clazz) {
		C converter = (C) REG.get(clazz);
		if (converter == null) {
			synchronized (REG) {
				converter = (C) REG.get(clazz);
				if (converter == null) {
					try {
						converter = (C) clazz.newInstance();
						REG.put(clazz, converter);
					} catch (InstantiationException e) {
						throw new SheetSystemException(ErrorCodeSheet.CONVERTER_INIT_ERROR, e);
					} catch (IllegalAccessException e) {
						throw new SheetSystemException(ErrorCodeSheet.CONVERTER_INIT_ERROR, e);
					}
				}
			}
		}
		return converter;
	}
	
	/**
	 * Gets the string cell value.
	 *
	 * @param cell the cell
	 * @return the string cell value
	 */
	protected String getStringCellValue(HSSFCell cell) {
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			//converting numbers top string results typicly in a decimal format so special handling for non floating numbers
			DecimalFormat format = new DecimalFormat("0.##");
			String formated = format.format(cell.getNumericCellValue());
			return formated;
		} else {
			return StringUtils.trimToNull(cell.toString());
		}
	}
	
	/**
	 * Gets the double cell value.
	 *
	 * @param cell the cell
	 * @return the double cell value
	 */
	protected Double getDoubleCellValue(HSSFCell cell) {
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			return cell.getNumericCellValue();
		} else {
			try {
				double parseDouble = Double.parseDouble(cell.toString());
				return Double.valueOf(parseDouble);
			} catch (NumberFormatException e) {
				return null;
			}
		}
	}
	
	/**
	 * Gets the converter for type.
	 *
	 * @param clazz the clazz
	 * @return the converter for type
	 */
	public static ICellConverter getConverterForType(Class<?> clazz) {
		ICellConverter conv = TYPE2CONVERTER.get(clazz);
		if (conv == null) {
			Class<?> superclass = clazz.getSuperclass();
			if(superclass==null){
				Class<?>[] interfaces = clazz.getInterfaces();
				for (int i = 0; i < interfaces.length; i++) {
					superclass=interfaces[i];
					ICellConverter cellConverter = getConverterForType(superclass);
					if(cellConverter!=null) return cellConverter;
				}	
			} if (superclass != Object.class) {
				return getConverterForType(superclass);
			} 
			throw new SheetSystemException(ErrorCodeSheet.NO_CONVERTER_FOR_TYPE).addValue("class", clazz.getName());
			
		}
		return conv;
	}
}
