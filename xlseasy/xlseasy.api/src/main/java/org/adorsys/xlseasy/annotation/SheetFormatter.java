package org.adorsys.xlseasy.annotation;

/**
 * HSSFSheet replaced with Object. Implementation will cast to HSSFSheet.
 * 
 * @author Sandro Sonntag
 * @author Francis Poutcha
 *
 */
public interface SheetFormatter {
	
	public class NoneSheetFormatter implements SheetFormatter {

		public void format(Object sheet) {
			// TODO: what should the method do?
		}
	}
	
	void format(Object sheet);
}