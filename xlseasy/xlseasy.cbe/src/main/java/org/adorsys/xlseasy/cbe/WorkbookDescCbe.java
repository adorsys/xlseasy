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

/**
 * The Class WorkbookDescCbe.
 *
 * @param <T> the generic type
 */
public class WorkbookDescCbe<T> implements WorkbookDescIF<T> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4879285592072189005L;
	
	/** The label2sheet desc. */
	private final Map<String, SheetDescCbe<?, T>> label2sheetDesc = new HashMap<String, SheetDescCbe<?, T>>();
	
	/** The ordered sheets. */
	private final List<SheetDescCbe<?, T>> orderedSheets = new ArrayList<SheetDescCbe<?, T>>();
	
	/** The workbook class. */
	private Class<T> workbookClass;
	
	/** The workbook cbe. */
	private final WorkbookCbe workbookCbe;
	
	// Maintain a premature list of classes, so we can discover foreign key constraints.
	/** The sheet classes. */
	private final Set<Class<?>> sheetClasses = new HashSet<Class<?>>();

	/**
	 * Instantiates a new workbook desc cbe.
	 *
	 * @param workbookClass the workbook class
	 * @param workbookCbe the workbook cbe
	 */
	@SuppressWarnings("unchecked")
	public WorkbookDescCbe(Class<T> workbookClass, WorkbookCbe workbookCbe) {
		this.workbookClass = workbookClass;
		this.workbookCbe = workbookCbe;
		
		// All list in this class will be considered a sheet. The fieldName  will ne the sheet name
		List<HorizontalRecordSheetDeclaration> workbookSheets = WorkbookProcessor.processWorkbook(
				workbookClass,workbookCbe);
		for (HorizontalRecordSheetDeclaration horizontalRecordSheetDeclaration : workbookSheets) {
			sheetClasses.add(horizontalRecordSheetDeclaration.getWorkBookSheet().getSheetKlass());
		}
		
		int sheetIndex = 0;
		for (HorizontalRecordSheetDeclaration horizontalRecordSheetDeclaration : workbookSheets) {
			WorkBookSheet<T> workBookSheet = horizontalRecordSheetDeclaration.getWorkBookSheet();
			addSheet(workBookSheet, workBookSheet.getField().getName(), sheetIndex);
			sheetIndex++;
		}
	}
	
	/**
	 * Checks if is sheet.
	 *
	 * @param klass the class
	 * @return true, if is sheet
	 */
	public boolean isSheet(Class<?> klass){
		return sheetClasses.contains(klass);
	}
	
	/**
	 * Instantiates a new workbook desc cbe.
	 *
	 * @param workbookCbe the workbook cbe
	 */
	public WorkbookDescCbe(WorkbookCbe workbookCbe) {
		this.workbookCbe = workbookCbe;
	}

	/**
	 * Adds the sheet.
	 *
	 * @param <R> the generic type
	 * @param workBookSheet the work book sheet
	 * @param beanProperty the bean property
	 * @param sheetIndex the sheet index
	 */
	private <R> void addSheet(WorkBookSheet<R> workBookSheet, String beanProperty, int sheetIndex) {
		SheetDescCbe<R, T> sheetDesc = new SheetDescCbe<R, T>(this, workBookSheet, 
				beanProperty, sheetIndex);
		label2sheetDesc.put(sheetDesc.getLabel(), sheetDesc);
		orderedSheets.add(sheetDesc);
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#getSheet(java.lang.String)
	 */
	public SheetDescCbe<?, T> getSheet(String sheetLabel) {
		return label2sheetDesc.get(sheetLabel);
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#getSheet(java.lang.Class)
	 */
	public <R> SheetDescCbe<R, T> getSheet(Class<R> rowType) {
		Collection<SheetDescCbe<?,T>> values = label2sheetDesc.values();
		for (SheetDescCbe<?, T> sheetDesc : values) {
			if (sheetDesc.getRecordClass().equals(rowType)) {
				return (SheetDescCbe<R, T>) sheetDesc;
			}
		}
		return null;
	}
	
	/**
	 * Checks if is workbook.
	 *
	 * @param workbookClass the workbook class
	 * @return true, if is workbook
	 */
	public static boolean isWorkbook(Class<?> workbookClass) {
		return AnnotationUtil.findClassAnnotations(workbookClass, true, Workbook.class).size() > 0;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#getOrderedSheets()
	 */
	public List<SheetDescCbe<?, T>> getOrderedSheets() {
		return orderedSheets;
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#loadWorkbookBean(org.apache.poi.hssf.usermodel.HSSFWorkbook, java.lang.Object, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public T loadWorkbookBean(HSSFWorkbook wb, T workBookInstance, ISheetSession<?, ?> session) {
		for (SheetDescCbe<?, T> sheet : orderedSheets) {
			loadSheet(wb, workBookInstance, sheet.getLabel(), session);
		}
		return workBookInstance;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#loadSheet(org.apache.poi.hssf.usermodel.HSSFWorkbook, java.lang.Object, java.lang.String, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public List<?> loadSheet(HSSFWorkbook wb, T workBookInstance, String
			sheetName,  ISheetSession<?, ?> session) {
		SheetDescCbe<?, T> sheetDesc = label2sheetDesc.get(sheetName);
		HSSFSheet s = wb.getSheet(sheetDesc.getLabel());
		if (s != null) {
			return sheetDesc.loadAndSetBeanRecords(s, workBookInstance, session);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#createWorkbookInstance()
	 */
	public T createWorkbookInstance() {
		try {
			return workbookClass != null ? workbookClass.newInstance() : null;
		} catch (InstantiationException e) {
			throw new SheetSystemException(ErrorCodeSheet.INSTANCIATE_WB_BEAN_FAILED, e).addValue("class",
					workbookClass.getClass().getName());
		} catch (IllegalAccessException e) {
			throw new SheetSystemException(ErrorCodeSheet.INSTANCIATE_WB_BEAN_FAILED, e).addValue("class",
					workbookClass.getClass().getName());
		}
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#addSheet(java.lang.Class, java.lang.String, java.lang.String, int)
	 */
	public <R> void addSheet(Class<R> recordClass, String label,
			String beanProperty, int sheetIndex) {
		throw new UnsupportedOperationException();
	}
}
