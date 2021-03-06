package org.adorsys.xlseasy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sandro Sonntag
 */
@Target({ ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SheetCellStyle {
	
	String dataFormat() default "";

	String fontName() default "";

	int fontSize() default -1;

	short fontHeightInPoints() default (short) 12;
	
	boolean fontStyleBold() default false;

	boolean fontStyleItalic() default false;

	boolean fontStyleStrikeout() default false;

	boolean fontStyleUnderline() default false;

	short fontColor() default -1;

	short backgroundColor() default -1;

	CellAlign align() default CellAlign.GENERAL;

	boolean wrapText() default false;
	
	/**
	 * @author Marius Guede
	 * border annotation
	 */
	short fontStyleBorder() default 1;
	short fontStyleBorderTop() default 1;
	short fontStyleBorderBottom() default 1;
	short fontStyleBorderLeft() default 1;
	short fontStyleBorderRight() default 1;
}