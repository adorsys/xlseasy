package main.java.org.adorsys.xlseasy.cbe;

import org.adorsys.xlseasy.boot.WorkbookCbe;
import org.adorsys.xlseasy.impl.proc.SpreadsheetServiceImpl;

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
