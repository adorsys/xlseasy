package org.adorsys.xlseasy.cbe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.annotation.Workbook;
import org.adorsys.xlseasy.annotation.filter.AnnotationUtil;
import org.adorsys.xlseasy.boot.WorkBookSheet;
import org.adorsys.xlseasy.boot.WorkbookCbe;
import org.adorsys.xlseasy.impl.proc.WorkbookDescIF;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class WorkbookDescCbe<T> implements WorkbookDescIF<T> {

	private static final long serialVersionUID = 4879285592072189005L;

	private final Map<String, SheetDescCbe<?, T>> label2sheetDesc = new HashMap<String, SheetDescCbe<?, T>>();
	private final List<SheetDescCbe<?, T>> orderedSheets = new ArrayList<SheetDescCbe<?, T>>();
	private Class<T> workbookClass;

	private final WorkbookCbe workbookCbe;

	// Maintains a premature list of classes, so we can discover foreign key
	// constraints.
	private final Set<Class<?>> sheetClasses = new HashSet<Class<?>>();

	@SuppressWarnings("unchecked")
	public WorkbookDescCbe(Class<T> workbookClass, WorkbookCbe workbookCbe) {
		this.workbookClass = workbookClass;
		this.workbookCbe = workbookCbe;

		// All list in this class will be considered a sheet. The fieldName will
		// ne the sheet name
		List<HorizontalRecordSheetDeclaration> workbookSheets = WorkbookProcessor
				.processWorkbook(workbookClass, workbookCbe);
		for (HorizontalRecordSheetDeclaration horizontalRecordSheetDeclaration : workbookSheets) {
			sheetClasses.add(horizontalRecordSheetDeclaration
					.getWorkBookSheet().getSheetKlass());
		}

		int sheetIndex = 0;
		for (HorizontalRecordSheetDeclaration horizontalRecordSheetDeclaration : workbookSheets) {
			WorkBookSheet<T> workBookSheet = horizontalRecordSheetDeclaration
					.getWorkBookSheet();
			addSheet(workBookSheet, workBookSheet.getField().getName(),
					sheetIndex);
			sheetIndex++;
		}
	}

	public boolean isSheet(Class<?> klass) {
		return sheetClasses.contains(klass);
	}

	public WorkbookDescCbe(WorkbookCbe workbookCbe) {
		this.workbookCbe = workbookCbe;
	}

	private <R> void addSheet(WorkBookSheet<R> workBookSheet,
			String beanProperty, int sheetIndex) {
		SheetDescCbe<R, T> sheetDesc = new SheetDescCbe<R, T>(this,
				workBookSheet, beanProperty, sheetIndex);
		label2sheetDesc.put(sheetDesc.getLabel(), sheetDesc);
		orderedSheets.add(sheetDesc);
	}

	public SheetDescCbe<?, T> getSheet(String sheetLabel) {
		return label2sheetDesc.get(sheetLabel);
	}

	public <R> SheetDescCbe<R, T> getSheet(Class<R> rowType) {
		Collection<SheetDescCbe<?, T>> values = label2sheetDesc.values();
		for (SheetDescCbe<?, T> sheetDesc : values) {
			if (sheetDesc.getRecordClass().equals(rowType)) {
				return (SheetDescCbe<R, T>) sheetDesc;
			}
		}
		return null;
	}

	public static boolean isWorkbook(Class<?> workbookClass) {
		return AnnotationUtil.findClassAnnotations(workbookClass, true,
				Workbook.class).size() > 0;
	}

	public List<SheetDescCbe<?, T>> getOrderedSheets() {
		return orderedSheets;
	}

	public T loadWorkbookBean(HSSFWorkbook wb, T workBookInstance,
			ISheetSession<?, ?> session) {
		for (SheetDescCbe<?, T> sheet : orderedSheets) {
			loadSheet(wb, workBookInstance, sheet.getLabel(), session);
		}
		return workBookInstance;
	}

	public List<?> loadSheet(HSSFWorkbook wb, T workBookInstance,
			String sheetName, ISheetSession<?, ?> session) {
		SheetDescCbe<?, T> sheetDesc = label2sheetDesc.get(sheetName);
		HSSFSheet s = wb.getSheet(sheetDesc.getLabel());
		if (s != null) {
			return sheetDesc
					.loadAndSetBeanRecords(s, workBookInstance, session);
		}
		return null;
	}

	public T createWorkbookInstance() {
		try {
			return workbookClass != null ? workbookClass.newInstance() : null;
		} catch (InstantiationException e) {
			throw new SheetSystemException(
					ErrorCodeSheet.INSTANCIATE_WB_BEAN_FAILED, e).addValue(
					"class", workbookClass.getClass().getName());
		} catch (IllegalAccessException e) {
			throw new SheetSystemException(
					ErrorCodeSheet.INSTANCIATE_WB_BEAN_FAILED, e).addValue(
					"class", workbookClass.getClass().getName());
		}
	}

	public <R> void addSheet(Class<R> recordClass, String label,
			String beanProperty, int sheetIndex) {
		throw new UnsupportedOperationException();
	}
}
