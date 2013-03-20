package org.adorsys.xlseasy.annotation;

/**
 * HSSFSheet replaced with Object. Implementation will cast to HSSFSheet.
 * 
 * @author sandro
 * @author francis
 *
 */
public interface SheetFormatter {
	
	public class NoneSheetFormatter implements SheetFormatter {

		public void format(Object sheet) {
		}
	}
	
	void format(Object sheet);

}
