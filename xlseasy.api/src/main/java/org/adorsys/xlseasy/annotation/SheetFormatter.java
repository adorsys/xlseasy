package org.adorsys.xlseasy.annotation;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface SheetFormatter {
	
	public class NoneSheetFormatter implements SheetFormatter {

		public void format(HSSFSheet sheet) {
		}
	}
	
	void format(HSSFSheet sheet);

}
