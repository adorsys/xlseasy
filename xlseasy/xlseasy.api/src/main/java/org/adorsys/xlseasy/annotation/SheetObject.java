package org.adorsys.xlseasy.annotation;

import java.io.Serializable;

import org.adorsys.xlseasy.annotation.SheetFormatter.NoneSheetFormatter;

public class SheetObject implements Serializable {

	private static final long serialVersionUID = -3638106240583873540L;

	public SheetObject() {
	}

	public SheetObject(Sheet sheet) {
		name = sheet.name();
		columnOrder = sheet.columnOrder();
		freezePane = new FreezePaneObject(sheet.freezePane());
		autoSizeColumns = sheet.autoSizeColumns();
		marged = sheet.marged();
		formatter = sheet.formatter();
	}

	public SheetObject(String label) {
		this.name = label;
	}

	String name = "";
	public String name() {
		return name;
	}

	String[] columnOrder = {};
	public String[] columnOrder() {
		return columnOrder;
	}

	FreezePaneObject freezePane = new FreezePaneObject();
	public FreezePaneObject freezePane() {
		return freezePane;
	}

	boolean autoSizeColumns = false;
	public boolean autoSizeColumns() {
		return autoSizeColumns;
	}
	
    /**
     * @author mariusguede
     * sheet's margin
     * */
	boolean marged = false;
	public boolean marged() {
		return marged;
	}
	
	double margin = 0.25;
	public double margin() {
		return margin;
	}

	Class<? extends SheetFormatter> formatter = NoneSheetFormatter.class;
	public Class<? extends SheetFormatter> formatter() {
		return formatter;
	}
}