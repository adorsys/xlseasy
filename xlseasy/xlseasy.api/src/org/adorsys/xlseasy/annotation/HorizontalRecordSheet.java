package org.adorsys.xlseasy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for the property which is mapped to the horizontal table records.
 * 
 * @author Sandro Sonntag <info@adorsys.de>
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HorizontalRecordSheet {
	
	/**
	 * Record class.
	 *
	 * @return the class
	 */
	Class<?> recordClass();
	
	/**
	 * Returns the mapped sheet name.
	 * 
	 * @return the sheet name
	 */
	String label() default "";
}
