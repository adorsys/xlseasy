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

public class DependencyEntry implements Comparable<DependencyEntry> {
	private final Class<?> klass;
	private final Field keyField;
	private final Set<DependencyEntry> dependents = new HashSet<DependencyEntry>();
	private final Map<Class<?>, DependencyEntry> extent;
	private final List<Field> fields = new ArrayList<Field>();
	private final Collection<String> excludedFields = new ArrayList<String>();
	private int level;
	private final Map<String, String> fieldDateStyles;

	List<Field> fieldOrder;

	public DependencyEntry(final Field field, final Class<?> klass,
			final Map<Class<?>, DependencyEntry> extent,
			Collection<String> excludedFields, Field keyField,
			Map<String, String> fieldDateStyles) {
		this.klass = klass;
		this.extent = extent;
		this.keyField = keyField;
		this.extent.put(klass, this);
		this.excludedFields.addAll(excludedFields);
		this.fields.add(field);
		this.fieldDateStyles = fieldDateStyles;
		fieldOrder = XlseasyUtils.readSheetFields(klass, excludedFields);
	}

	public void addField(Field field) {
		fields.add(field);
	}

	public List<Field> getFields() {
		return Collections.unmodifiableList(fields);
	}

	public boolean isDependent(DependencyEntry e) {
		if (dependents.contains(e))
			return true;
		for (DependencyEntry dependencyEntry : dependents) {
			if (dependencyEntry.isDependent(e))
				return true;
		}
		return false;
	}

	private void addDependent(DependencyEntry d, String fieldName) {
		// exclude circular relationship
		if (d.isDependent(this))
			excludedFields.add(fieldName);
		
		fieldOrder.remove(fieldName);
		d.increaseLevel();
		dependents.add(d);
	}

	public void increaseLevel() {
		level += 1;
		for (DependencyEntry dependencyEntry : dependents) {
			dependencyEntry.increaseLevel();
		}
	}

	public void processDependencies() {
		for (Field field : fieldOrder) {
			DependencyEntry dependencyEntry = extent.get(field.getType());
			if (dependencyEntry != null) {
				addDependent(dependencyEntry, field.getName());
			} else {
				if (XlseasyUtils.isCollectionType(field)) {
					Class<?> elementType = XlseasyUtils
							.extractElementType(field);
					DependencyEntry dependencyEntry2 = extent.get(elementType);
					if (dependencyEntry2 != null) {
						addDependent(dependencyEntry2, field.getName());
					}
				}
			}
		}
	}

	public int compareTo(DependencyEntry other) {
		return new Integer(level).compareTo(new Integer(other.level));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WorkBookSheet> getWorkBookSheets() {
		List<WorkBookSheet> result = new ArrayList<WorkBookSheet>(fields.size());
		List<Field> fieldsToRemove = new ArrayList<Field>();
		for (Field field : fieldOrder) {
			if (excludedFields.contains(field.getName()))
				fieldsToRemove.add(field);
		}
		fieldOrder.removeAll(fieldsToRemove);
		for (Field field : fields) {
			result.add(new WorkBookSheet(field, fieldOrder, excludedFields,
					klass, keyField, fieldDateStyles));
		}
		return result;
	}

	@Override
	public String toString() {
		return "DependencyEntry [klass=" + klass + ", level=" + level + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((klass == null) ? 0 : klass.hashCode());
		return result;
	}

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