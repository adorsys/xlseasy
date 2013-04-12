package org.adorsys.xlseasy.boot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains the list of sheet decriptors.
 * 
 * @author Francis Pouatcha
 *
 */
public class WorkbookCbe {

	private final List<WorkBookSheet> workBookSheets = new ArrayList<WorkBookSheet>();
	private final Map<Class<?>, WorkBookSheet> sheetMap = new HashMap<Class<?>, WorkBookSheet>();
	
	public WorkbookCbe(List<WorkBookSheet> workBookSheets) {
		this.workBookSheets.addAll(workBookSheets);
		for (WorkBookSheet workBookSheet : workBookSheets) {
			sheetMap.put(workBookSheet.getSheetKlass(), workBookSheet);
			workBookSheet.setWorkbookCbe(this);
		}
	}

	public List<WorkBookSheet> getWorkBookSheets() {
		return workBookSheets;
	}
	
	public WorkBookSheet getWorkBookSheet(Class<?> sheetKlass){
		return sheetMap.get(sheetKlass);
	}
}
