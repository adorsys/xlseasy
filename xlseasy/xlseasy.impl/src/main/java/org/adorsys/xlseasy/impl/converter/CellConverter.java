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

/**
 * The Class CellConverter.
 *
 * @author Sandro Sonntag
 */
public abstract class CellConverter implements ICellConverter {

	private static final Map<Class<?>, ICellConverter> REG = new HashMap<Class<?>, ICellConverter>();

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

	/**
	 * Sets the cell's value.
	 * */
	public abstract void setHSSFCell(Object cell, Object value,
			Class<?> objectType, ISheetSession<?, ?> session);

	/**
	 * Gets the cell's value.
	 * */
	public abstract Object getDataCell(Object cell, Class<?> objectType,
			ISheetSession<?, ?> session) throws SpreadsheetConverterException;

	/**
	 * Gets the converter's type. Must be implemented by extended classes.
	 * */
	public abstract Class<?>[] getConveterTypes();

	private static void registerDefaultType(
			Class<? extends ICellConverter> converterClazz) {
		ICellConverter converter = getConverter(converterClazz);
		Class<?>[] conveterTypes = converter.getConveterTypes();
		for (int i = 0; i < conveterTypes.length; i++) {
			TYPE2CONVERTER.put(conveterTypes[i], converter);
		}
	}

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
						throw new SheetSystemException(
								ErrorCodeSheet.CONVERTER_INIT_ERROR, e);
					} catch (IllegalAccessException e) {
						throw new SheetSystemException(
								ErrorCodeSheet.CONVERTER_INIT_ERROR, e);
					}
				}
			}
		}
		return converter;
	}

	/**
	 * Get's the cell's value as String.
	 * */
	protected String getStringCellValue(HSSFCell cell) {
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			// converting numbers top string results typicly in a decimal format
			// so special handling for non floating numbers
			DecimalFormat format = new DecimalFormat("0.##");
			String formated = format.format(cell.getNumericCellValue());
			return formated;
		} else {
			return StringUtils.trimToNull(cell.toString());
		}
	}

	/**
	 * Get's the cell's value as Double.
	 * */
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

	public static ICellConverter getConverterForType(Class<?> clazz) {
		ICellConverter conv = TYPE2CONVERTER.get(clazz);
		if (conv == null) {
			Class<?> superclass = clazz.getSuperclass();
			if (superclass == null) {
				Class<?>[] interfaces = clazz.getInterfaces();
				for (int i = 0; i < interfaces.length; i++) {
					superclass = interfaces[i];
					ICellConverter cellConverter = getConverterForType(superclass);
					if (cellConverter != null)
						return cellConverter;
				}
			}
			if (superclass != Object.class) {
				return getConverterForType(superclass);
			}
			throw new SheetSystemException(ErrorCodeSheet.NO_CONVERTER_FOR_TYPE)
					.addValue("class", clazz.getName());

		}
		return conv;
	}
}