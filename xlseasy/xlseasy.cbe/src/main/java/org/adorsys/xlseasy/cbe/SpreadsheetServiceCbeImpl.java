package org.adorsys.xlseasy.cbe;

import org.adorsys.xlseasy.boot.WorkbookCbe;
import org.adorsys.xlseasy.impl.proc.SpreadsheetServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class SpreadsheetServiceCbeImpl.
 */
public class SpreadsheetServiceCbeImpl extends SpreadsheetServiceImpl {

	/**
	 * Instantiates a new spreadsheet service cbe impl.
	 *
	 * @param workbookCbe the workbook cbe
	 */
	public SpreadsheetServiceCbeImpl(WorkbookCbe workbookCbe){
		super(new WorkbookDescFactoryCbe(workbookCbe));
	}
}
