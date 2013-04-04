/**
 * Copyright 2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.adorsys.xlseasy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface SheetColumn.
 *
 * @author Sandro Sonntag <info@adorsys.de>
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SheetColumn {
	
	/**
	 * Column name.
	 *
	 * @return the string
	 */
	String columnName() default "";
	
	/**
	 * Merged.
	 *
	 * @return true, if successful
	 */
	boolean merged() default false;
	
	/**
	 * Header merged.
	 *
	 * @return the int
	 */
	int headerMerged() default 0;
	
	/**
	 * Optional.
	 *
	 * @return true, if successful
	 */
	boolean optional() default false;
	
	/**
	 * Converter.
	 *
	 * @return the class
	 */
	Class<?> converter() default Object.class;
	
	/**
	 * Hidden.
	 *
	 * @return true, if successful
	 */
	boolean hidden() default false;
	
	/**
	 * Header comment.
	 *
	 * @return the string
	 */
	String headerComment() default "";
	
	/**
	 * Header style.
	 *
	 * @return the sheet cell style
	 */
	SheetCellStyle headerStyle() default @SheetCellStyle;
	
	/**
	 * Column style.
	 *
	 * @return the sheet cell style
	 */
	SheetCellStyle columnStyle() default @SheetCellStyle;
	
}
