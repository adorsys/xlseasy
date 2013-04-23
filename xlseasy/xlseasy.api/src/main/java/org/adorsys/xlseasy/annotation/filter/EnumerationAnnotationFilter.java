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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Filter checkes for the given annotations.
 * 
 * @author Sandro Sonntag
 */
public class EnumerationAnnotationFilter implements AnnotationFilter {
	
	private final Set<Class<?>> annotationTypes;
	
	/**
	 * @param annotationTypes
	 */
	public EnumerationAnnotationFilter(Class<?>... annotationTypes) {
		super();
		this.annotationTypes = new HashSet<Class<?>>(Arrays.asList(annotationTypes));
	}

	/**
	 * {@inheritDoc}
	 * @see org.adorsys.xlseasy.annotation.filter.lang.annotation.AnnotationFilter#accept(java.lang.annotation.Annotation)
	 */
	public boolean accept(Annotation annotation) {
		return annotationTypes.contains(annotation.annotationType());
	}
}