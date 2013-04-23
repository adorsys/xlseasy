package org.adorsys.xlseasy.annotation;

import java.io.Serializable;

import org.adorsys.xlseasy.annotation.SheetFormatter.NoneSheetFormatter;

public class SheetObject implements Serializable {

	String name = "";

	String[] columnOrder = {};

	FreezePaneObject freezePane = new FreezePaneObject();

	boolean autoSizeColumns = false;

	Class<? extends SheetFormatter> formatter = NoneSheetFormatter.class;

	private static final long serialVersionUID = -3638106240583873540L;

	public SheetObject() {
	}

	public SheetObject(Sheet sheet) {
		name = sheet.name();
		columnOrder = sheet.columnOrder();
		freezePane = new FreezePaneObject(sheet.freezePane());
		autoSizeColumns = sheet.autoSizeColumns();
		formatter = sheet.formatter();
	}

	public SheetObject(String label) {
		this.name = label;
	}

	public String name() {
		return name;
	}

	public String[] columnOrder() {
		return columnOrder;
	}

	public FreezePaneObject freezePane() {
		return freezePane;
	}

	public boolean autoSizeColumns() {
		return autoSizeColumns;
	}

	public Class<? extends SheetFormatter> formatter() {
		return formatter;
	}
}