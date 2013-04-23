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

package org.adorsys.xlseasy.annotation.filter;

import java.lang.annotation.Annotation;

/**
 * Filters noting and return true.
 * 
 * @author Sandro Sonntag
 */
public class NonAnnotationFilter implements AnnotationFilter {
	/**
	 * Use this singelton.
	 */
	public static final AnnotationFilter INSTANCE = new NonAnnotationFilter();

	private NonAnnotationFilter() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.adorsys.xlseasy.annotation.filter.lang.annotation.AnnotationFilter#accept(java.lang.annotation.Annotation)
	 */
	public boolean accept(Annotation annotation) {
		return true;
	}
}