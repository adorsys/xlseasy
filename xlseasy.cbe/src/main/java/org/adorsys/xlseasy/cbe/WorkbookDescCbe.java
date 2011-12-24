package org.adorsys.xlseasy.cbe;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.HorizontalRecordSheetObject;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.annotation.Workbook;
import org.adorsys.xlseasy.annotation.filter.AnnotationUtil;
import org.adorsys.xlseasy.impl.proc.WorkbookDescIF;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class WorkbookDescCbe<T> implements WorkbookDescIF<T>{

	private static final long serialVersionUID = 4879285592072189005L;
	
	private final Map<String, SheetDescCbe<?, T>> label2sheetDesc = new HashMap<String, SheetDescCbe<?, T>>();
	private final List<SheetDescCbe<?, T>> orderedSheets = new ArrayList<SheetDescCbe<?, T>>();
	private Class<T> workbookClass;

	private final Collection<String> excludedFields;
	private final Map<Class<?>, String> businessKeyFields;
	private final Map<Class<?>, Map<String, String>> fieldDateStyles;
	
	// Maintain a premature list of classes, so we can discover foreign key constraints.
	private final Set<Class<?>> sheetClasses = new HashSet<Class<?>>();

	public WorkbookDescCbe(Class<T> workbookClass, Collection<String> excludedFields, 
			Map<Class<?>, Map<String, String>> fieldDateStyles, Map<Class<?>, String> businessKeyFields) {
		this.workbookClass = workbookClass;
		this.excludedFields = excludedFields;
		this.fieldDateStyles = fieldDateStyles;
		this.businessKeyFields = businessKeyFields;
		
		// All list in this class will be considered a sheet. The fieldName  will ne the sheet name
		List<HorizontalRecordSheetDeclaration> workbookSheets = WorkbookProcessor.processWorkbook(workbookClass);
		for (HorizontalRecordSheetDeclaration horizontalRecordSheetDeclaration : workbookSheets) {
			sheetClasses.add(horizontalRecordSheetDeclaration.getRecordKlass());
		}
		
		int sheetIndex = 0;
		for (HorizontalRecordSheetDeclaration horizontalRecordSheetDeclaration : workbookSheets) {
			addSheet(horizontalRecordSheetDeclaration, sheetIndex);
			sheetIndex++;
		}
	}
	
	public boolean isSheet(Class<?> klass){
		return sheetClasses.contains(klass);
	}
	
	public WorkbookDescCbe(Collection<String> excludedFields, 
			Map<Class<?>, Map<String, String>> fieldDateStyles, Map<Class<?>, String> businessKeyFields) {
		this.excludedFields = excludedFields;
		this.fieldDateStyles = fieldDateStyles;
		this.businessKeyFields = businessKeyFields;
	}

	private void addSheet(
			HorizontalRecordSheetDeclaration horizontalRecordSheetDeclaration,
			int sheetIndex) {
		PropertyDescriptor propertyDescriptor = horizontalRecordSheetDeclaration.getPropertyDescriptor();
		HorizontalRecordSheetObject hrs = horizontalRecordSheetDeclaration.getHorizontalRecordSheet();
		Class<?> recordClass = horizontalRecordSheetDeclaration.getRecordKlass();
		if (Object.class == recordClass) {
			recordClass = propertyDescriptor.getPropertyType();
		}
		addSheet(recordClass, hrs.label(), propertyDescriptor.getName(), sheetIndex);
	}

	public <R> void addSheet(Class<R> recordClass, String label, String beanProperty, int sheetIndex) {
		Map<String, String> dateFormaters = fieldDateStyles.get(recordClass);
		SheetDescCbe<R, T> sheetDesc = new SheetDescCbe<R, T>(this, recordClass, label, 
				beanProperty, sheetIndex,excludedFields,dateFormaters,businessKeyFields);
		label2sheetDesc.put(sheetDesc.getLabel(), sheetDesc);
		orderedSheets.add(sheetDesc);
	}
	
	public SheetDescCbe<?, T> getSheet(String sheetLabel) {
		return label2sheetDesc.get(sheetLabel);
	}
	
	public <R> SheetDescCbe<R, T> getSheet(Class<R> rowType) {
		Collection<SheetDescCbe<?,T>> values = label2sheetDesc.values();
		for (SheetDescCbe<?, T> sheetDesc : values) {
			if (sheetDesc.getRecordClass().equals(rowType)) {
				return (SheetDescCbe<R, T>) sheetDesc;
			}
		}
		return null;
	}
	
	public static boolean isWorkbook(Class<?> workbookClass) {
		return AnnotationUtil.findClassAnnotations(workbookClass, true, Workbook.class).size() > 0;
	}

	public List<SheetDescCbe<?, T>> getOrderedSheets() {
		return orderedSheets;
	}
	
	public T loadWorkbookBean(HSSFWorkbook wb, T workBookInstance, ISheetSession<?, ?> session) {
		for (SheetDescCbe<?, T> sheet : orderedSheets) {
			loadSheet(wb, workBookInstance, sheet.getLabel(), session);
		}
		return workBookInstance;
	}

	public List<?> loadSheet(HSSFWorkbook wb, T workBookInstance, String
			sheetName,  ISheetSession<?, ?> session) {
		SheetDescCbe<?, T> sheetDesc = label2sheetDesc.get(sheetName);
		HSSFSheet s = wb.getSheet(sheetDesc.getLabel());
		if (s != null) {
			return sheetDesc.loadAndSetBeanRecords(s, workBookInstance, session);
		}
		return null;
	}
	
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
}
