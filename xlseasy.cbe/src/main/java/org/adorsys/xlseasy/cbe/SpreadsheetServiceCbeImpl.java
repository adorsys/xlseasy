package org.adorsys.xlseasy.cbe;

import java.util.Collection;
import java.util.Map;

import org.adorsys.xlseasy.boot.WorkbookCbe;
import org.adorsys.xlseasy.impl.proc.SpreadsheetServiceImpl;

public class SpreadsheetServiceCbeImpl extends SpreadsheetServiceImpl {

	public SpreadsheetServiceCbeImpl(WorkbookCbe workbookCbe){
		super(new WorkbookDescFactoryCbe(workbookCbe));
	}
}
