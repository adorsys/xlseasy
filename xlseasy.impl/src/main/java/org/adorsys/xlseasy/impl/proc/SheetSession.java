package org.adorsys.xlseasy.impl.proc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * TODO set Javadoc for Class
 * 
 * @version $Id: $
 * @author sso
 */
public class SheetSession<WT, RT> implements ISheetSession<WT, RT> {

	private HSSFWorkbook workbook;

	private final Map<SheetCellStyle, WorkbookStyle> cellStyleCache = new HashMap<SheetCellStyle, WorkbookStyle>();

	private static final Map<Class<?>, WorkbookDesc<?>> WORKBOOKDESC_CACHE = new HashMap<Class<?>, WorkbookDesc<?>>();

	private final Class<?> beanType;
	private final boolean workbookType;
	private final WorkbookDesc<WT> workbookDesc;
	private final WT workbookBean;
	
	private final HashMap<List<?>, Object> key2objectCache = new HashMap<List<?>, Object>();

	/**
	 * @param workbook
	 */
	public SheetSession(Class<?> beanType) {
		super();
		this.beanType = beanType;
		this.workbookType = WorkbookDesc.isWorkbook(beanType);
		workbookDesc = getOrCreateWorkbookDesc(beanType);
		workbookBean = workbookDesc.createWorkbookInstance();
		reset();
	}

	public void reset() {
		this.workbook = new HSSFWorkbook();
		cellStyleCache.clear();
	}

	public void load(InputStream is) {
		try {
			this.workbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			throw new SheetSystemException(ErrorCodeSheet.LOAD_XLS_FAILED, e);
		}
	}

	WorkbookStyle getWorkbookStyle(SheetColumn column,
			SheetCellStyle style) {
		WorkbookStyle cachedStyle = cellStyleCache.get(style);
		if (cachedStyle == null) {
			cachedStyle = new WorkbookStyle(workbook, column, style);
			cellStyleCache.put(style, cachedStyle);
		}
		return cachedStyle;
	}

	public void load(WT data) {
		List<SheetDesc<?,WT>> orderedSheets = workbookDesc.getOrderedSheets();
		for (SheetDesc<?,WT> sheetDesc : orderedSheets) {
			Collection<?> sheetData = null;
			if (data != null) {
				sheetData = sheetDesc.getSheetData(data);
			}
			loadSheetData(sheetDesc, sheetData);
		}
	}

	public void load(Collection<?> data) {
		List<SheetDesc<?, WT>> orderedSheets = workbookDesc.getOrderedSheets();
		for (SheetDesc<?, WT> sheetDesc : orderedSheets) {
			loadSheetData(sheetDesc, data);
		}
	}

	private void loadSheetData(SheetDesc<?, WT> sheetDesc, Collection<?> sheetData) {
		sheetDesc.createSheet(sheetData, this);
	}

	@SuppressWarnings("unchecked")
	private WorkbookDesc<WT> getOrCreateWorkbookDesc(Class<?> clazz) {
		WorkbookDesc<WT> workbookDesc = (WorkbookDesc<WT>) WORKBOOKDESC_CACHE.get(clazz);
		if (workbookDesc == null) {
			synchronized (WORKBOOKDESC_CACHE) {
				workbookDesc = (WorkbookDesc<WT>) WORKBOOKDESC_CACHE.get(clazz);
				if (workbookDesc == null) {
					if (WorkbookDesc.isWorkbook(clazz)) {
						// is workbook
						workbookDesc = new WorkbookDesc<WT>((Class<WT>) clazz);
					} else {
						// is sheet
						workbookDesc = new WorkbookDesc<WT>();
						workbookDesc.addSheet(clazz, null, null, 0);
					}
					WORKBOOKDESC_CACHE.put(clazz, workbookDesc);
				}
			}
		}
		return workbookDesc;
	}

	public void save(OutputStream os) {
		try {
			workbook.write(os);
		} catch (IOException e) {
			throw new SheetSystemException(ErrorCodeSheet.SAVE_XLS_FAILED, e);
		}
	}
	
	public List<RT> toRecordList() {
		if (!workbookType) {
			@SuppressWarnings("unchecked")
			SheetDesc<RT, WT> sheetDesc = (SheetDesc<RT, WT>) workbookDesc.getOrderedSheets().get(0);
			
			HSSFSheet sheet = workbook.getSheet(sheetDesc.getLabel());
			if (sheet != null) {
				return sheetDesc.loadBeanRecords(sheet, this);
			} else {
				return null;
			}
		}
		throw new SheetSystemException(ErrorCodeSheet.NOT_A_SHEETTYPE_SESSION);
	}

	public WT toWorkbookBean() {
		if (workbookType) {
			return workbookDesc.loadWorkbookBean(workbook, workbookBean, this);
		}
		throw new SheetSystemException(ErrorCodeSheet.NOT_A_WORKBOOK_SESSION);
	}
	
	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ISheetSession#getSheetRecords(java.lang.String)
	 */
	public List<?> getSheetRecords(String name) {
		if (workbookType) {
			List<?> loadSheet = workbookDesc.loadSheet(workbook, workbookBean, name, this);
			return loadSheet;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.impl.proc.ISheetSession#getWorkbook()
	 */
	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public <T> T getObjectByKey(Class<T> recordClass, Object key) {
		return (T) key2objectCache.get(Arrays.asList(recordClass, key));
	}

	public <T> void setObjectByKey(Class<T> recordClass, Object key, T object) {
		if (key != null) {
			key2objectCache.put(Arrays.asList(recordClass, key), object);
		}
	}
	
}
