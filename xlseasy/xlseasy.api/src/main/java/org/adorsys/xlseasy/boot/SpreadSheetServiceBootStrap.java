package org.adorsys.xlseasy.boot;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.annotation.SpreadsheetService;
import org.adorsys.xlseasy.utils.DependencyEntry;
import org.adorsys.xlseasy.utils.XlseasyUtils;

/**
 * The Class SpreadSheetServiceBootStrap.
 */
public class SpreadSheetServiceBootStrap {

	/** The key field inspector. */
	private KeyFieldInspector keyFieldInspector = new NullKeyFieldInspector();
	
	/** The date styles inspector. */
	private DateStylesInspector dateStylesInspector = new NullDateStylesInspector();
	
	/** The excluded field inspector. */
	private ExcludedFieldInspector excludedFieldInspector = new NullExcludedFieldInspector();
	
	/** The workbook klass. */
	private Class<?> workbookKlass;
	
	/**
	 * Gets the key field inspector.
	 *
	 * @return the key field inspector
	 */
	public KeyFieldInspector getKeyFieldInspector() {
		return keyFieldInspector;
	}


	/**
	 * Sets the key field inspector.
	 *
	 * @param keyFieldInspector the new key field inspector
	 */
	public void setKeyFieldInspector(KeyFieldInspector keyFieldInspector) {
		this.keyFieldInspector = keyFieldInspector;
	}


	/**
	 * Gets the date styles inspector.
	 *
	 * @return the date styles inspector
	 */
	public DateStylesInspector getDateStylesInspector() {
		return dateStylesInspector;
	}


	/**
	 * Sets the date styles inspector.
	 *
	 * @param dateStylesInspector the new date styles inspector
	 */
	public void setDateStylesInspector(DateStylesInspector dateStylesInspector) {
		this.dateStylesInspector = dateStylesInspector;
	}


	/**
	 * Gets the excluded field inspector.
	 *
	 * @return the excluded field inspector
	 */
	public ExcludedFieldInspector getExcludedFieldInspector() {
		return excludedFieldInspector;
	}


	/**
	 * Sets the excluded field inspector.
	 *
	 * @param excludedFieldInspector the new excluded field inspector
	 */
	public void setExcludedFieldInspector(
			ExcludedFieldInspector excludedFieldInspector) {
		this.excludedFieldInspector = excludedFieldInspector;
	}


	/**
	 * Gets the workbook klass.
	 *
	 * @return the workbook klass
	 */
	public Class<?> getWorkbookKlass() {
		return workbookKlass;
	}


	/**
	 * Sets the workbook klass.
	 *
	 * @param workbookKlass the new workbook klass
	 */
	public void setWorkbookKlass(Class<?> workbookKlass) {
		this.workbookKlass = workbookKlass;
	}


	/**
	 * Creates the spread service.
	 *
	 * @return the spreadsheet service
	 */
	@SuppressWarnings("unchecked")
	public SpreadsheetService createSpreadService() {
		WorkbookCbe workbookCbe = buildWorkbookCbe();
		Class<? extends SpreadsheetService> loadedClass;
		try {
			loadedClass = (Class<? extends SpreadsheetService>) SpreadsheetService.class.getClassLoader().
					loadClass("org.adorsys.xlseasy.cbe.SpreadsheetServiceCbeImpl");
		} catch (ClassNotFoundException e) {
			throw new SheetSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION,e);
		}
		Constructor<? extends SpreadsheetService> constructor = XlseasyUtils.getConstructor(loadedClass, WorkbookCbe.class);

		return XlseasyUtils.newInstance(constructor, workbookCbe);
	}
	
	
	/**
	 * Builds the workbook cbe.
	 *
	 * @return the workbook cbe
	 */
	@SuppressWarnings("rawtypes")
	private WorkbookCbe buildWorkbookCbe(){
		List<WorkBookSheet> workBookSheets = new ArrayList<WorkBookSheet>();
		Map<String, DependencyEntry> sheetClasses = new HashMap<String, DependencyEntry>();
		Map<Class<?>, DependencyEntry> extent = new HashMap<Class<?>, DependencyEntry>();
		List<Field> workBookFields = XlseasyUtils.readWorkbookFields(workbookKlass);
		for (Field field : workBookFields) {
			Class<?> sheetKlass = XlseasyUtils.extractElementType(field);
			DependencyEntry dependencyEntry = extent.get(sheetKlass);
			if(dependencyEntry==null){
				Collection<String> excludedFieldNames = excludedFieldInspector.getExcludedFieldNames(sheetKlass);
				Field keyField = keyFieldInspector.findKeyField(sheetKlass);
				Map<String, String> fieldDateStyles = dateStylesInspector.inspectDateStyles(sheetKlass);
				dependencyEntry = new DependencyEntry(field, 
						sheetKlass, extent, excludedFieldNames, keyField, fieldDateStyles);
			} else {
				dependencyEntry.addField(field);
			}
			sheetClasses.put(field.getName(), dependencyEntry);
		}
		for (Field field : workBookFields) {
			DependencyEntry dependencyEntry = sheetClasses.get(field.getName());
			dependencyEntry.processDependencies();
		}
		
		ArrayList<DependencyEntry> arrayList = new ArrayList<DependencyEntry>(sheetClasses.values());
		Collections.sort(arrayList);
		Collections.reverse(arrayList);
		for (DependencyEntry dependencyEntry : arrayList) {
			workBookSheets.addAll(dependencyEntry.getWorkBookSheets());
		}
		return new WorkbookCbe(workBookSheets);
	}
}