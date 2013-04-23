package org.adorsys.xlseasy.cbe;

import org.adorsys.xlseasy.boot.WorkbookCbe;
import org.adorsys.xlseasy.impl.proc.WorkbookDescFactory;
import org.adorsys.xlseasy.impl.proc.WorkbookDescIF;
import org.adorsys.xlseasy.utils.XlseasyUtils;

public class WorkbookDescFactoryCbe extends WorkbookDescFactory {
	// private final Collection<String> excludedFields;
	// private final Map<Class<?>, String> businessKeyFields;
	// private final Map<Class<?>, Map<String, String>> fieldDateStyles;

	private final WorkbookCbe workbookCbe;

	public WorkbookDescFactoryCbe(WorkbookCbe workbookCbe) {
		super();
		this.workbookCbe = workbookCbe;
		// this.fieldDateStyles = fieldDateStyles;
		// this.businessKeyFields = businessKeyFields;
	}

	@Override
	public <WT> WorkbookDescIF<WT> createWorkbookDesc(Class<WT> clazz) {
		return new WorkbookDescCbe<WT>(clazz, workbookCbe);
	}

	@Override
	public <WT> WorkbookDescIF<WT> emptyWorkbookDesc(Class<WT> clazz) {
		return new WorkbookDescCbe<WT>(workbookCbe);
	}

	@Override
	public boolean checkIfWorkbook(Class<?> workbookClass) {
		return XlseasyUtils.readWorkbookFields(workbookClass).size() > 0;
	}

}
