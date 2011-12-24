package org.adorsys.xlseasy.cbe;

import java.util.Collection;
import java.util.Map;

import org.adorsys.xlseasy.impl.proc.SpreadsheetServiceImpl;

public class SpreadsheetServiceCbeImpl extends SpreadsheetServiceImpl {

	public SpreadsheetServiceCbeImpl(Map<Class<?>, Map<String, String>> fieldDateStyles,
			Collection<String> excludedFields,
			Map<Class<?>, String> businessKeyFields){
		super(new WorkbookDescFactoryCbe(excludedFields, fieldDateStyles, businessKeyFields));
	}
}
