package org.adorsys.xlseasy.impl.proc;

import org.adorsys.xlseasy.annotation.Workbook;
import org.adorsys.xlseasy.annotation.filter.AnnotationUtil;

/**
 * Will be used to create workbook descriptors. This will provide the
 * opportunity to inject other way of parsing class. In particular the default
 * interpretation of resources.
 * 
 * @author Francis Pouatcha
 * 
 */
public class WorkbookDescFactory {
	
	/**
	 * Creates a new WorkbookDesc object.
	 *
	 * @param <WT> the generic type
	 * @param clazz the class
	 * @return the workbook desc i f< w t>
	 */
	public <WT> WorkbookDescIF<WT> createWorkbookDesc(Class<WT> clazz) {
		return new WorkbookDesc<WT>(clazz);
	}

	/**
	 * Empty workbook desc.
	 *
	 * @param <WT> the generic type
	 * @param clazz the class
	 * @return the workbook desc if
	 */
	public <WT> WorkbookDescIF<WT> emptyWorkbookDesc(Class<WT> clazz) {
		return new WorkbookDesc<WT>();
	}

	/**
	 * Check if workbook.
	 *
	 * @param workbookClass the workbook class
	 * @return true, if successful
	 */
	public boolean checkIfWorkbook(Class<?> workbookClass) {
		return AnnotationUtil.findClassAnnotations(workbookClass, true,
				Workbook.class).size() > 0;
	}
}
