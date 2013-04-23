package org.adorsys.xlseasy.cbe;

import org.adorsys.xlseasy.boot.WorkbookCbe;
import org.adorsys.xlseasy.impl.proc.SpreadsheetServiceImpl;

public class SpreadsheetServiceCbeImpl extends SpreadsheetServiceImpl {

	public SpreadsheetServiceCbeImpl(WorkbookCbe workbookCbe) {
		super(new WorkbookDescFactoryCbe(workbookCbe));
	}
}
