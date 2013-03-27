package org.adorsys.xlseasy.impl.proc;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.HorizontalRecordSheet;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.adorsys.xlseasy.annotation.Workbook;
import org.adorsys.xlseasy.annotation.filter.AnnotationUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @param <T> the generic type
 * @author Sandro Sonntag
 */
public class WorkbookDesc<T> implements WorkbookDescIF<T> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The label2sheet desc. */
	private final Map<String, SheetDesc<?, T>> label2sheetDesc = new HashMap<String, SheetDesc<?, T>>();
	
	/** The ordered sheets. */
	private final List<SheetDesc<?, T>> orderedSheets = new ArrayList<SheetDesc<?, T>>();
	
	/** The workbook class. */
	private Class<T> workbookClass;

	/**
	 * Instantiates a new workbook desc.
	 *
	 * @param workbookClass the workbook class
	 */
	public WorkbookDesc(Class<T> workbookClass) {
		this.workbookClass = workbookClass;
		Map<PropertyDescriptor, Map<Class<?>, Annotation>> findBeanPropertyDescriptorAnnotations = AnnotationUtil
				.findBeanPropertyDescriptorAnnotations(workbookClass, true,
						HorizontalRecordSheet.class);
		Map<String, PropertyDescriptor> propertyKey2PropertyDescriptor = AnnotationUtil.extractPropertyKey2PropertyDescriptor(findBeanPropertyDescriptorAnnotations.keySet());
		Collection<Annotation> findClassAnnotations = AnnotationUtil.findClassAnnotations(workbookClass, true, Workbook.class);
		Workbook so;
		if (findClassAnnotations.size() > 0) {
			so = (Workbook) findClassAnnotations.iterator().next();
		} else {
			throw new SheetSystemException(ErrorCodeSheet.CLASS_HAS_NO_WORKBOOK_ANNOTATION).addValue("class", workbookClass);
		}
		
		Collection<String> orderedProperties;		
		if (so.sheetOrder().length > 0) {
			//sort order
			orderedProperties = Arrays.asList(so.sheetOrder());
		} else {
			//apha label order
			orderedProperties = new TreeSet<String>(propertyKey2PropertyDescriptor.keySet());
		}
		
		int sheetIndex = 0;
		for (String propertyKey : orderedProperties) {
			addSheet(findBeanPropertyDescriptorAnnotations,
					propertyKey2PropertyDescriptor,
					propertyKey, sheetIndex);
			sheetIndex++;
		}
	}
	
	/**
	 * Instantiates a new workbook desc.
	 */
	public WorkbookDesc() {
	}

	/**
	 * Adds the sheet.
	 *
	 * @param findBeanPropertyDescriptorAnnotations the find bean property descriptor annotations
	 * @param propertyKey2PropertyDescriptor the property key2 property descriptor
	 * @param propertyKey the property key
	 * @param sheetIndex the sheet index
	 */
	private void addSheet(
			Map<PropertyDescriptor, Map<Class<?>, Annotation>> findBeanPropertyDescriptorAnnotations,
			Map<String, PropertyDescriptor> propertyKey2PropertyDescriptor,
			String propertyKey, int sheetIndex) {
		PropertyDescriptor propertyDescriptor = propertyKey2PropertyDescriptor.get(propertyKey);
		if (propertyDescriptor != null) {
			Map<Class<?>, Annotation> map = findBeanPropertyDescriptorAnnotations.get(propertyDescriptor);
			HorizontalRecordSheet hrs = (HorizontalRecordSheet) map.get(HorizontalRecordSheet.class);
			
			Class<?> recordClass = hrs.recordClass();
			if (Object.class == recordClass) {
				recordClass = propertyDescriptor.getPropertyType();
			}
			String label = StringUtils.trimToNull(hrs.label());
			addSheet(recordClass, label, propertyDescriptor.getName(), sheetIndex);
		}
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#addSheet(java.lang.Class, java.lang.String, java.lang.String, int)
	 */
	public <R> void addSheet(Class<R> recordClass, String label, String beanProperty, int sheetIndex) {
		SheetDesc<R, T> sheetDesc = new SheetDesc<R, T>(this, recordClass, label, beanProperty, sheetIndex);
		label2sheetDesc.put(sheetDesc.getLabel(), sheetDesc);
		orderedSheets.add(sheetDesc);
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#getSheet(java.lang.String)
	 */
	public SheetDesc<?, T> getSheet(String sheetLabel){
		return label2sheetDesc.get(sheetLabel);
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#getSheet(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <R> SheetDescIF<R, T> getSheet(Class<R> rowType){
		Collection<SheetDesc<?,T>> values = label2sheetDesc.values();
		for (SheetDesc<?, T> sheetDesc : values) {
			if (sheetDesc.getRecordClass().equals(rowType)) {
				return (SheetDescIF<R, T>) sheetDesc;
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
	public List<? extends SheetDescIF<?, T>> getOrderedSheets(){
		return orderedSheets;
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#loadWorkbookBean(org.apache.poi.hssf.usermodel.HSSFWorkbook, java.lang.Object, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public T loadWorkbookBean(HSSFWorkbook wb, T workBookInstance, ISheetSession<?, ?> session) {
		for (SheetDesc<?, T> sheet : orderedSheets) {
			loadSheet(wb, workBookInstance, sheet.getLabel(), session);
		}
		return workBookInstance;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescIF#loadSheet(org.apache.poi.hssf.usermodel.HSSFWorkbook, java.lang.Object, java.lang.String, org.adorsys.xlseasy.annotation.ISheetSession)
	 */
	public List<?> loadSheet(HSSFWorkbook wb, T workBookInstance, String
			sheetName,  ISheetSession<?, ?> session) {
		SheetDesc<?, T> sheetDesc = label2sheetDesc.get(sheetName);
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
}