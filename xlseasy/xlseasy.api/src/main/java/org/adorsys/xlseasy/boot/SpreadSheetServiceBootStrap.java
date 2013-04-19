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

public class SpreadSheetServiceBootStrap {

	private KeyFieldInspector keyFieldInspector = new NullKeyFieldInspector();
	private DateStylesInspector dateStylesInspector = new NullDateStylesInspector();
	private ExcludedFieldInspector excludedFieldInspector = new NullExcludedFieldInspector();
	private Class<?> workbookKlass;

	public KeyFieldInspector getKeyFieldInspector() {
		return keyFieldInspector;
	}

	public void setKeyFieldInspector(KeyFieldInspector keyFieldInspector) {
		this.keyFieldInspector = keyFieldInspector;
	}

	public DateStylesInspector getDateStylesInspector() {
		return dateStylesInspector;
	}

	public void setDateStylesInspector(DateStylesInspector dateStylesInspector) {
		this.dateStylesInspector = dateStylesInspector;
	}

	public ExcludedFieldInspector getExcludedFieldInspector() {
		return excludedFieldInspector;
	}

	public void setExcludedFieldInspector(
			ExcludedFieldInspector excludedFieldInspector) {
		this.excludedFieldInspector = excludedFieldInspector;
	}

	public Class<?> getWorkbookKlass() {
		return workbookKlass;
	}

	public void setWorkbookKlass(Class<?> workbookKlass) {
		this.workbookKlass = workbookKlass;
	}

	@SuppressWarnings("unchecked")
	public SpreadsheetService createSpreadService() {
		WorkbookCbe workbookCbe = buildWorkbookCbe();
		Class<? extends SpreadsheetService> loadedClass;
		try {
			loadedClass = (Class<? extends SpreadsheetService>) SpreadsheetService.class
					.getClassLoader()
					.loadClass(
							"org.adorsys.xlseasy.cbe.SpreadsheetServiceCbeImpl");
		} catch (ClassNotFoundException e) {
			throw new SheetSystemException(
					ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION, e);
		}
		Constructor<? extends SpreadsheetService> constructor = XlseasyUtils
				.getConstructor(loadedClass, WorkbookCbe.class);

		return XlseasyUtils.newInstance(constructor, workbookCbe);
	}

	@SuppressWarnings("rawtypes")
	private WorkbookCbe buildWorkbookCbe() {
		List<WorkBookSheet> workBookSheets = new ArrayList<WorkBookSheet>();
		Map<String, DependencyEntry> sheetClasses = new HashMap<String, DependencyEntry>();
		Map<Class<?>, DependencyEntry> extent = new HashMap<Class<?>, DependencyEntry>();
		List<Field> workBookFields = XlseasyUtils
				.readWorkbookFields(workbookKlass);
		for (Field field : workBookFields) {
			Class<?> sheetKlass = XlseasyUtils.extractElementType(field);
			DependencyEntry dependencyEntry = extent.get(sheetKlass);
			if (dependencyEntry == null) {
				Collection<String> excludedFieldNames = excludedFieldInspector
						.getExcludedFieldNames(sheetKlass);
				Field keyField = keyFieldInspector.findKeyField(sheetKlass);
				Map<String, String> fieldDateStyles = dateStylesInspector
						.inspectDateStyles(sheetKlass);
				dependencyEntry = new DependencyEntry(field, sheetKlass,
						extent, excludedFieldNames, keyField, fieldDateStyles);
			} else {
				dependencyEntry.addField(field);
			}
			sheetClasses.put(field.getName(), dependencyEntry);
		}
		for (Field field : workBookFields) {
			DependencyEntry dependencyEntry = sheetClasses.get(field.getName());
			dependencyEntry.processDependencies();
		}

		ArrayList<DependencyEntry> arrayList = new ArrayList<DependencyEntry>(
				sheetClasses.values());
		Collections.sort(arrayList);
		Collections.reverse(arrayList);
		for (DependencyEntry dependencyEntry : arrayList) {
			workBookSheets.addAll(dependencyEntry.getWorkBookSheets());
		}
		return new WorkbookCbe(workBookSheets);
	}
}
