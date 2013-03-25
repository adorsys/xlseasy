package org.adorsys.xlseasy.annotation;

import java.io.Serializable;

/**
 * The Class FreezePaneObject.
 */
public class FreezePaneObject implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3375292360135601153L;

	
    /**
     * Instantiates a new freeze pane object.
     */
    public FreezePaneObject() {
	}

    /**
     * Instantiates a new freeze pane object.
     *
     * @param freezePane the freeze pane
     */
    public FreezePaneObject(FreezePane freezePane) {
    	colSplit = freezePane.colSplit();
    	rowSplit = freezePane.rowSplit();
    	leftmostColumn = freezePane.leftmostColumn();
    	topRow = freezePane.topRow();
	}

	/**
     * Horizonatal position of split.
     */
    int colSplit = 0;
    
    /**
     * Col split.
     *
     * @return the int
     */
    public int colSplit() { return colSplit; }

    /**
     * Vertical position of split.
     */
    int rowSplit = 1;
    
    /**
     * Row split.
     *
     * @return the int
     */
    public int rowSplit() { return rowSplit; }

    /** Top row visible in bottom pane. */
    int leftmostColumn = 0;
    
    /**
     * Leftmost column.
     *
     * @return the int
     */
    public int leftmostColumn() { return leftmostColumn; }

    /**
     * Left column visible in right pane.
     */
    int topRow = 1;
    
    /**
     * Top row.
     *
     * @return the int
     */
    public int topRow() { return topRow;}
}
