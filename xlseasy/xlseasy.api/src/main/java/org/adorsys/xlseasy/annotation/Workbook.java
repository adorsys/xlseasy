package org.adorsys.xlseasy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface Workbook.
 *
 * @author Sandro Sonntag
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Workbook {
	
	/**
	 * Sheet order.
	 *
	 * @return the string[]
	 */
	String[] sheetOrder() default {};
}
