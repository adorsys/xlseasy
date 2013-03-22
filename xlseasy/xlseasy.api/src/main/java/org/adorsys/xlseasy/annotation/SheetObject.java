package org.adorsys.xlseasy.annotation;

import java.io.Serializable;

import org.adorsys.xlseasy.annotation.SheetFormatter.NoneSheetFormatter;

// TODO: Auto-generated Javadoc
/**
 * The Class SheetObject.
 */
public class SheetObject implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3638106240583873540L;
	
    /**
     * Instantiates a new sheet object.
     */
    public SheetObject() {
	}
    
    /**
     * Instantiates a new sheet object.
     *
     * @param sheet the sheet
     */
    public SheetObject(Sheet sheet) {
    	name = sheet.name();
    	columnOrder = sheet.columnOrder();
    	freezePane = new FreezePaneObject(sheet.freezePane());
    	autoSizeColumns = sheet.autoSizeColumns();
    	formatter = sheet.formatter();
	}
    
    /**
     * Instantiates a new sheet object.
     *
     * @param label the label
     */
    public SheetObject(String label){
    	this.name = label;
    }

	/** The name. */
	String name = "";
    
    /**
     * Name.
     *
     * @return the string
     */
    public String name() { return name;}

    /** The column order. */
    String[] columnOrder = {};
    
    /**
     * Column order.
     *
     * @return the string[]
     */
    public String[] columnOrder (){ return columnOrder; }

    /** The freeze pane. */
    FreezePaneObject freezePane = new FreezePaneObject();
    
    /**
     * Freeze pane.
     *
     * @return the freeze pane object
     */
    public FreezePaneObject freezePane()  { return freezePane; }

    /** The auto size columns. */
    boolean autoSizeColumns = false;
    
    /**
     * Auto size columns.
     *
     * @return true, if successful
     */
    public boolean autoSizeColumns()  { return autoSizeColumns; }

    /** The formatter. */
    Class<? extends SheetFormatter> formatter = NoneSheetFormatter.class;
    
    /**
     * Formatter.
     *
     * @return the class<? extends sheet formatter>
     */
    public Class<? extends SheetFormatter> formatter()  { return formatter; }
}
