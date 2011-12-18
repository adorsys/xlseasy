package org.adorsys.xlseasy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO set Javadoc for Class
 * @version $Id: $
 * @author sso
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SheetCellStyle {
	String dataFormat() default "";
	String fontName() default "";
	int fontSize() default -1;
	boolean fontStyleBold() default false;
	boolean fontStyleItalic() default false;
	boolean fontStyleStrikeout() default false;
	boolean fontStyleUnderline() default false;
	short fontColor() default -1;
	short backgroundColor() default -1;
	CellAlign align() default CellAlign.GENERAL;
	boolean wrapText() default false;	
}
