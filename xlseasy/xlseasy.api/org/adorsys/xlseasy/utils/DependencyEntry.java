package org.adorsys.xlseasy.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adorsys.xlseasy.boot.WorkBookSheet;

/**
 * The Class DependencyEntry.
 */
public 	class DependencyEntry implements Comparable<DependencyEntry> {
	
	/** The class. */
	private final Class<?> klass;
	
	/** The key field. */
	private final Field keyField;
	
	/** The dependencies. */
	private final Set<DependencyEntry> dependents = new HashSet<DependencyEntry>();
	
	/** The extent. */
	private final Map<Class<?>, DependencyEntry> extent;
	
	/** The fields. */
	private final List<Field> fields = new ArrayList<Field>();
	
	/** The excluded fields. */
	private final Collection<String> excludedFields = new ArrayList<String>();
	
	/** The level. */
	private int level;
	
	/** The field date styles. */
	private final Map<String, String> fieldDateStyles;
	
	/** The field order. */
	List<Field> fieldOrder;
	
	
	/**
	 * Instantiates a new dependency entry.
	 *
	 * @param field the field
	 * @param klass the class
	 * @param extent the extent
	 * @param excludedFields the excluded fields
	 * @param keyField the key field
	 * @param fieldDateStyles the field date styles
	 */
	public DependencyEntry(final Field field,
			final Class<?> klass, 
			final Map<Class<?>, DependencyEntry> extent,
			Collection<String> excludedFields, Field keyField, Map<String, String> fieldDateStyles) {
		this.klass = klass;
		this.extent = extent;
		this.keyField = keyField;
		this.extent.put(klass, this);
		this.excludedFields.addAll(excludedFields);
		this.fields.add(field);
		this.fieldDateStyles = fieldDateStyles;
		fieldOrder = XlseasyUtils.readSheetFields(klass, excludedFields);
	}
	
	/**
	 * Adds the field.
	 *
	 * @param field the field
	 */
	public void addField(Field field){
		fields.add(field);
	}
	
	/**
	 * Gets the fields.
	 *
	 * @return the fields
	 */
	public List<Field> getFields(){
		return Collections.unmodifiableList(fields);
	}

	/**
	 * Checks if is dependent.
	 *
	 * @param e the e
	 * @return true, if is dependent
	 */
	public boolean isDependent(DependencyEntry e){
		if (dependents.contains(e)) return true;
		for (DependencyEntry dependencyEntry : dependents) {
			if(dependencyEntry.isDependent(e)) return true;
		}
		return false;
	}
	
	/**
	 * Adds the dependent.
	 *
	 * @param d the d
	 * @param fieldName the field name
	 */
	private void addDependent(DependencyEntry d, String fieldName) {
		if(d.isDependent(this))// exclude circular relationship
			excludedFields.add(fieldName);
			fieldOrder.remove(fieldName);
		d.increaseLevel();
		dependents.add(d);
	}
	
	/**
	 * Increase level.
	 */
	public void increaseLevel(){
		level+=1;
		for (DependencyEntry dependencyEntry : dependents) {
			dependencyEntry.increaseLevel();
		}
	}
	
	/**
	 * Process dependencies.
	 */
	public void processDependencies(){
		for (Field field : fieldOrder) {
			DependencyEntry dependencyEntry = extent.get(field.getType());
			if(dependencyEntry!=null){
				addDependent(dependencyEntry, field.getName());
			} else {
				if (XlseasyUtils.isCollectionType(field)){
					Class<?> elementType = XlseasyUtils.extractElementType(field);
					DependencyEntry dependencyEntry2 = extent.get(elementType);
					if(dependencyEntry2!=null){
						addDependent(dependencyEntry2, field.getName());
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(DependencyEntry other) {
		return new Integer(level).compareTo(new Integer(other.level));
	}
	
	/**
	 * Gets the work book sheets.
	 *
	 * @return the work book sheets
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WorkBookSheet> getWorkBookSheets(){
		List<WorkBookSheet> result = new ArrayList<WorkBookSheet>(fields.size());
		List<Field> fieldsToRemove = new ArrayList<Field>();
		for (Field field : fieldOrder) {
			if(excludedFields.contains(field.getName())) fieldsToRemove.add(field);
		}
		fieldOrder.removeAll(fieldsToRemove);
		for (Field field : fields) {
			result.add(new WorkBookSheet(field, fieldOrder, excludedFields, klass, keyField, fieldDateStyles));
		}
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DependencyEntry [klass=" + klass + ", level=" + level + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((klass == null) ? 0 : klass.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DependencyEntry other = (DependencyEntry) obj;
		if (klass == null) {
			if (other.klass != null)
				return false;
		} else if (!klass.equals(other.klass))
			return false;
		return true;
	}
	
	
}
