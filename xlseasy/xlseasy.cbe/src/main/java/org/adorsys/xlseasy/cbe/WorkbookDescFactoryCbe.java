package main.java.org.adorsys.xlseasy.cbe;

import org.adorsys.xlseasy.boot.WorkbookCbe;
import org.adorsys.xlseasy.impl.proc.WorkbookDescFactory;
import org.adorsys.xlseasy.impl.proc.WorkbookDescIF;
import org.adorsys.xlseasy.utils.XlseasyUtils;

/**
 * The Class WorkbookDescFactoryCbe.
 */
public class WorkbookDescFactoryCbe extends WorkbookDescFactory {
	
	/** The workbook cbe. */
	private final WorkbookCbe workbookCbe;
	
	/**
	 * Instantiates a new workbook desc factory cbe.
	 *
	 * @param workbookCbe the workbook cbe
	 */
	public WorkbookDescFactoryCbe(WorkbookCbe workbookCbe) {
		super();
		this.workbookCbe = workbookCbe;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescFactory#createWorkbookDesc(java.lang.Class)
	 */
	@Override
	public <WT> WorkbookDescIF<WT> createWorkbookDesc(Class<WT> clazz) {
		return new WorkbookDescCbe<WT>(clazz, workbookCbe);
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescFactory#emptyWorkbookDesc(java.lang.Class)
	 */
	@Override
	public <WT> WorkbookDescIF<WT> emptyWorkbookDesc(Class<WT> clazz) {
		return new WorkbookDescCbe<WT>(workbookCbe);
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.WorkbookDescFactory#checkIfWorkbook(java.lang.Class)
	 */
	@Override
	public boolean checkIfWorkbook(Class<?> workbookClass) {
		return XlseasyUtils.readWorkbookFields(workbookClass).size() > 0;
	}
	
	
}
