package org.adorsys.xlseasy.annotation;

import java.io.Serializable;

public class FreezePaneObject implements Serializable {

	private static final long serialVersionUID = 3375292360135601153L;

	
    public FreezePaneObject() {
	}

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
    public int colSplit() { return colSplit; }

    /**
     * Vertical position of split.
     */
    int rowSplit = 1;
    public int rowSplit() { return rowSplit; }

    /**
     * Top row visible in bottom pane
     */
    int leftmostColumn = 0;
    public int leftmostColumn() { return leftmostColumn; }

    /**
     * Left column visible in right pane.
     */
    int topRow = 1;
    public int topRow() { return topRow;}
}
