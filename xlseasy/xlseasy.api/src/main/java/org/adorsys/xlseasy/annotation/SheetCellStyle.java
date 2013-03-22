package org.adorsys.xlseasy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * The Interface SheetCellStyle.
 *
 * @author Sandro Sonntag <info@adorsys.de>
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SheetCellStyle {
	
	/**
	 * Data format.
	 *
	 * @return the string
	 */
	String dataFormat() default "";
	
	/**
	 * Font name.
	 *
	 * @return the string
	 */
	String fontName() default "";
	
	/**
	 * Font size.
	 *
	 * @return the int
	 */
	int fontSize() default -1;
	
	/**
	 * Font style bold.
	 *
	 * @return true, if successful
	 */
	boolean fontStyleBold() default false;
	
	/**
	 * Font style italic.
	 *
	 * @return true, if successful
	 */
	boolean fontStyleItalic() default false;
	
	/**
	 * Font style strikeout.
	 *
	 * @return true, if successful
	 */
	boolean fontStyleStrikeout() default false;
	
	/**
	 * Font style underline.
	 *
	 * @return true, if successful
	 */
	boolean fontStyleUnderline() default false;
	
	/**
	 * Font color.
	 *
	 * @return the short
	 */
	short fontColor() default -1;
	
	/**
	 * Background color.
	 *
	 * @return the short
	 */
	short backgroundColor() default -1;
	
	/**
	 * Align.
	 *
	 * @return the cell align
	 */
	CellAlign align() default CellAlign.GENERAL;
	
	/**
	 * Wrap text.
	 *
	 * @return true, if successful
	 */
	boolean wrapText() default false;
}
