package org.adorsys.xlseasy.cbe;

import java.util.Collection;
import java.util.Map;

import org.adorsys.xlseasy.impl.proc.WorkbookDescFactory;
import org.adorsys.xlseasy.impl.proc.WorkbookDescIF;
import org.adorsys.xlseasy.utils.XlseasyUtils;

public class WorkbookDescFactoryCbe extends WorkbookDescFactory {
	private final Collection<String> excludedFields;
	private final Map<Class<?>, String> businessKeyFields;
	private final Map<Class<?>, Map<String, String>> fieldDateStyles;
	
	public WorkbookDescFactoryCbe(Collection<String> excludedFields,
			Map<Class<?>, Map<String, String>> fieldDateStyles,
			Map<Class<?>, String> businessKeyFields) {
		super();
		this.excludedFields = excludedFields;
		this.fieldDateStyles = fieldDateStyles;
		this.businessKeyFields = businessKeyFields;
	}

	@Override
	public <WT> WorkbookDescIF<WT> createWorkbookDesc(Class<WT> clazz) {
		return new WorkbookDescCbe<WT>(clazz, excludedFields,fieldDateStyles,businessKeyFields);
	}

	@Override
	public <WT> WorkbookDescIF<WT> emptyWorkbookDesc(Class<WT> clazz) {
		return new WorkbookDescCbe<WT>(excludedFields,fieldDateStyles,businessKeyFields);
	}

	@Override
	public boolean checkIfWorkbook(Class<?> workbookClass) {
		return XlseasyUtils.readWorkbookFields(workbookClass).size() > 0;
	}
	
	
}
