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

package org.adorsys.xlseasy.impl.proc;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;

/**
 * Common Annotation Util.
 * @version $Id: $
 * @author sso
 */
public class AnnotationUtil {
	
	private AnnotationUtil(){
	}
	
	private interface ClassVisitor {
		public void visit(Class<?> clazz);
	}
	
	/**
	 * Finds class annotations  with a custom filter.
	 * @param clazz
	 * @param inherited
	 * @param filter
	 * @return
	 */
	public static Collection<Annotation> findClassAnnotations(Class<?> clazz, boolean inherited, final AnnotationFilter filter) {
		final ArrayList<Annotation> annotations = new ArrayList<Annotation>();
		ClassVisitor classVisitor = new ClassVisitor() {			
			public void visit(Class<?> c) {
				annotations.addAll(filterAnnotations(c.getAnnotations(), filter));
			}
		};
		visitClass(clazz, classVisitor, inherited);
		return annotations;
	}
	
	/**
	 * Find class anotations by list of annotation types.
	 * @param clazz
	 * @param inherited
	 * @param annotationsToFind
	 * @return
	 */
	public static Collection<Annotation> findClassAnnotations(Class<?> clazz, boolean inherited, Class<?>... annotationsToFind) {
		return findClassAnnotations(clazz, inherited, new EnumerationAnnotationFilter(annotationsToFind));
	}
	
	/**
	 * Find fields by a custom annotation filter.
	 * @param clazz
	 * @param inherited
	 * @param annotationsToFind
	 * @return
	 */
	public static Collection<Field> findFieldsByAnnotation(Class<?> clazz, boolean inherited, Class<?>... annotationsToFind) {
		return findFieldsByAnnotation(clazz, inherited, new EnumerationAnnotationFilter(annotationsToFind));
	}
	
	/**
	 * Finds java bean property releated annotations. It considers the getters, setters and field annotations. 
	 * @param clazz
	 * @param inherited
	 * @param annotationsToFind
	 * @return
	 */
	public static Map<PropertyDescriptor, Map<Class<?>, Annotation>> findBeanPropertyDescriptorAnnotations(Class<?> clazz, boolean inherited, Class<?>... annotationsToFind) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			throw new AnnotationSystemException(ErrorCodeSheet.BEAN_INTROSPECTION_EXCEPTION, e).addValue("class", clazz.getName());
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		HashMap<String, PropertyDescriptor> properties2Descriptor = new HashMap<String, PropertyDescriptor>(propertyDescriptors.length);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			properties2Descriptor.put(propertyDescriptors[i].getName(), propertyDescriptors[i]);
		}
		
		Map<PropertyDescriptor, Map<Class<?>, Annotation>> result = new HashMap<PropertyDescriptor, Map<Class<?>, Annotation>>();
		
		// find getter / setter annotations
		Collection<Method> methods = findMethodsByAnnotation(clazz, inherited, annotationsToFind);
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("get") || methodName.startsWith("set")) {
				String capitalizedName = methodName.substring(3);
				String propertyName = Introspector.decapitalize(capitalizedName);
				PropertyDescriptor pd = properties2Descriptor.get(propertyName);
				if (pd != null) {
					//yes, its a real property
					HashMap<Class<?>, Annotation> clazz2Annotation = new HashMap<Class<?>, Annotation>();
					
					if (pd.getReadMethod() != null)
						toAnnotationMap(clazz2Annotation, pd.getReadMethod().getAnnotations());
					if (pd.getWriteMethod() != null)
						toAnnotationMap(clazz2Annotation, pd.getWriteMethod().getAnnotations());					
					result.put(pd, clazz2Annotation);
				}
			}
		}
		
		//find field annotations
		Collection<Field> fields = findFieldsByAnnotation(clazz, inherited, annotationsToFind);
		for (Field field : fields) {
			String propertyName = field.getName();
			if (properties2Descriptor.containsKey(propertyName)) {
				Map<Class<?>, Annotation> map = result.get(properties2Descriptor.get(propertyName));
				if (map == null) {
					map = new HashMap<Class<?>, Annotation>();
					result.put(properties2Descriptor.get(propertyName), map);
				}
				
				toAnnotationMap(map, field.getAnnotations());
			}
		}
		return result;
	}

	private static void toAnnotationMap(Map<Class<?>, Annotation> map,
			Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			map.put(annotation.annotationType(), annotation);
		}
	}
	
	/**
	 * Find fields by a list of annotation types.
	 * @param clazz
	 * @param inherited
	 * @param filter
	 * @return
	 */
	public static Collection<Field> findFieldsByAnnotation(Class<?> clazz, boolean inherited, final AnnotationFilter filter) {
		final ArrayList<Field> fields = new ArrayList<Field>();
		ClassVisitor classVisitor = new ClassVisitor() {			
			public void visit(Class<?> c) {
				Field[] clazzFields = c.getDeclaredFields();
				for (Field field : clazzFields) {
					Annotation[] annotations = field.getAnnotations();
					for (Annotation a : annotations) {
						if (filter.accept(a)) {
							fields.add(field);
							break;
						}
					}
				}
			}
		};
		visitClass(clazz, classVisitor, inherited);
		return fields;
	}
	
	/**
	 * Find methodes by a list of annotation types.
	 * @param clazz
	 * @param inherited
	 * @param annotationsToFind
	 * @return
	 */
	public static Collection<Method> findMethodsByAnnotation(Class<?> clazz, boolean inherited, Class<?>... annotationsToFind) {
		return findMethodsByAnnotation(clazz, inherited, new EnumerationAnnotationFilter(annotationsToFind));
	}
	
	/**
	 * Find methodes by a custom annotation filter.
	 * @param clazz
	 * @param inherited
	 * @param annotationsToFind
	 * @return
	 */
	public static Collection<Method> findMethodsByAnnotation(Class<?> clazz, boolean inherited, final AnnotationFilter filter) {
		final ArrayList<Method> methods = new ArrayList<Method>();
		ClassVisitor classVisitor = new ClassVisitor() {			
			public void visit(Class<?> c) {
				Method[] clazzMethods = c.getDeclaredMethods();
				for (Method method : clazzMethods) {
					Annotation[] annotations = method.getAnnotations();
					for (Annotation a : annotations) {
						if (filter.accept(a)) {
							methods.add(method);
							break;
						}
					}
				}
			}
		};
		visitClass(clazz, classVisitor, inherited);
		return methods;
	}
	
	private static final Collection<Annotation> filterAnnotations(Annotation[] annotations, AnnotationFilter filter) {
		ArrayList<Annotation> filteredAnno = new ArrayList<Annotation>(annotations.length);
		for (Annotation annotation : annotations) {
			if (filter.accept(annotation)) {
				filteredAnno.add(annotation); 
			}
		}
		return filteredAnno;
	}
	
	private static void visitClass(Class<?> clazz, ClassVisitor visitor, boolean inherited) {
		if (inherited) {
			visitClassTree(clazz, visitor, true);
		} else {
			visitor.visit(clazz);
		}
	}
	
	private static void visitClassTree(Class<?> clazz, ClassVisitor visitor, boolean includeInterfaces) {
		if (clazz != null && clazz != Object.class) {
			visitor.visit(clazz);
		
			if (includeInterfaces) {
				Class<?>[] interfaces = clazz.getInterfaces();
				for (Class<?> i : interfaces) {
					visitor.visit(i);
					//add the interfaces of the interfaces
					visitClassTree(i, visitor, includeInterfaces);
				}
			}
			visitClassTree(clazz.getSuperclass(), visitor, includeInterfaces);
		}
	}
	
	/**
	 * Extracts all annotation types from a collection of annotations.
	 * @param annotation
	 * @return extracted types.
	 */
	public static Set<Class<? extends Annotation>> extractAnnotationTypes(Collection<Annotation> annotation) {
		HashSet<Class<? extends Annotation>> types = new HashSet<Class<? extends Annotation>>();
		for (Annotation a : annotation) {
			types.add(a.annotationType());
		}
		return types;
	}
	
	/**
	 * extracts the propertyDrescriptor names for a easy access to findBeanPropertyDescriptorAnnotations;
	 * @param pd
	 * @return
	 */
	public static Map<String, PropertyDescriptor> extractPropertyKey2PropertyDescriptor(Collection<PropertyDescriptor> pd) {
		Map<String, PropertyDescriptor> propertyKey2PropertyDescriptor = new HashMap<String, PropertyDescriptor>(pd.size());
		for (PropertyDescriptor propertyDescriptor : pd) {
			propertyKey2PropertyDescriptor.put(propertyDescriptor.getName(), propertyDescriptor);
		}
		return propertyKey2PropertyDescriptor;
	}
	
	/**
	 * Checks a class for the spezified annotation type.
	 * @param classToExplore
	 * @param a
	 * @param inherit
	 * @return
	 */
	public static boolean containsClassAnnotationType(Class<?> classToExplore, Class<? extends Annotation> a, boolean inherit){
		Collection<Annotation> findClassAnnotations = findClassAnnotations(classToExplore, inherit, a);
		return findClassAnnotations.size() > 0;
	}

}
