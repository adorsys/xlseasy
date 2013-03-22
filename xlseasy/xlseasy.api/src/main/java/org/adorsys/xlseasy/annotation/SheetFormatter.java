package org.adorsys.xlseasy.annotation;

// TODO: Auto-generated Javadoc
/**
 * HSSFSheet replaced with Object. Implementation will cast to HSSFSheet.
 * 
 * @author Sandro Sonntag <info@adorsys.de>
 * @author Francis Pouatcha <info@adorsys.de>
 * 
 */
public interface SheetFormatter {

	/**
	 * The Class NoneSheetFormatter.
	 */
	public class NoneSheetFormatter implements SheetFormatter {
		
		/* (non-Javadoc)
		 * @see org.adorsys.xlseasy.annotation.SheetFormatter#format(java.lang.Object)
		 */
		public void format(Object sheet) {
		}
	}

	/**
	 * Format.
	 *
	 * @param sheet the sheet
	 */
	void format(Object sheet);
}
