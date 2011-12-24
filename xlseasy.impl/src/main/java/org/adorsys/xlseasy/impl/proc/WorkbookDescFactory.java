package org.adorsys.xlseasy.impl.proc;

import org.adorsys.xlseasy.annotation.Workbook;
import org.adorsys.xlseasy.annotation.filter.AnnotationUtil;



/**
 * Will be used to create workbook descriptors. This will provide the 
 * opportunity to inject other way of parsing class. In particular
 * the default interpretation of resources.
 * 
 * @author francis
 *
 */
public class WorkbookDescFactory {
	
	public <WT> WorkbookDescIF<WT> createWorkbookDesc(Class<WT> clazz){
		return new WorkbookDesc<WT>(clazz);
	}

	public <WT> WorkbookDescIF<WT> emptyWorkbookDesc(Class<WT> clazz){
		return new WorkbookDesc<WT>();
	}

	public boolean checkIfWorkbook(Class<?> workbookClass) {
		return AnnotationUtil.findClassAnnotations(workbookClass, true, Workbook.class).size() > 0;
	}
}
