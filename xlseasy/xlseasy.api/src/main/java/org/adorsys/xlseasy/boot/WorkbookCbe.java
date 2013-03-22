package org.adorsys.xlseasy.boot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains the list of sheet decriptors.
 * 
 * @author Francis Pouatcha <info@adorsys.de>
 */
public class WorkbookCbe {

	/** The work book sheets. */
	private final List<WorkBookSheet> workBookSheets = new ArrayList<WorkBookSheet>();
	
	/** The sheet map. */
	private final Map<Class<?>, WorkBookSheet> sheetMap = new HashMap<Class<?>, WorkBookSheet>();
	
	/**
	 * Instantiates a new workbook cbe.
	 *
	 * @param workBookSheets the work book sheets
	 */
	public WorkbookCbe(List<WorkBookSheet> workBookSheets) {
		this.workBookSheets.addAll(workBookSheets);
		for (WorkBookSheet workBookSheet : workBookSheets) {
			sheetMap.put(workBookSheet.getSheetKlass(), workBookSheet);
			workBookSheet.setWorkbookCbe(this);
		}
	}

	/**
	 * Gets the work book sheets.
	 *
	 * @return the work book sheets
	 */
	public List<WorkBookSheet> getWorkBookSheets() {
		return workBookSheets;
	}
	
	/**
	 * Gets the work book sheet.
	 *
	 * @param sheetKlass the sheet klass
	 * @return the work book sheet
	 */
	public WorkBookSheet getWorkBookSheet(Class<?> sheetKlass){
		return sheetMap.get(sheetKlass);
	}
}
