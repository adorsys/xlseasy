package org.adorsys.xlseasy.impl.proc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.ISheetSession;
import org.adorsys.xlseasy.annotation.SheetCellStyleObject;
import org.adorsys.xlseasy.annotation.SheetColumnObject;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

// TODO: Auto-generated Javadoc
/**
 * TODO set Javadoc for Class.
 *
 * @param <WT> the generic type
 * @param <RT> the generic type
 * @version $Id: $
 * @author sso
 */
public class SheetSession<WT, RT> implements ISheetSession<WT, RT> {

	/** The workbook. */
	private HSSFWorkbook workbook;

	/** The cell style cache. */
	private final Map<SheetCellStyleObject, WorkbookStyle> cellStyleCache = new HashMap<SheetCellStyleObject, WorkbookStyle>();

	/** The Constant WORKBOOKDESC_CACHE. */
	private static final Map<Class<?>, WorkbookDescIF<?>> WORKBOOKDESC_CACHE = new HashMap<Class<?>, WorkbookDescIF<?>>();

	/** The bean type. */
	private final Class<?> beanType;
	
	/** The workbook type. */
	private final boolean workbookType;
	
	/** The workbook desc. */
	private final WorkbookDescIF<WT> workbookDesc;
	
	/** The workbook bean. */
	private final WT workbookBean;
	
	/** The key2object cache. */
	private final HashMap<List<?>, Object> key2objectCache = new HashMap<List<?>, Object>();

	/** The workbook desc factory. */
	private WorkbookDescFactory workbookDescFactory;
	
	/**
	 * Instantiates a new sheet session.
	 *
	 * @param beanType the bean type
	 */
	public SheetSession(Class<?> beanType) {
		this(beanType, new WorkbookDescFactory());
	}
	
	/**
	 * Instantiates a new sheet session.
	 *
	 * @param beanType the bean type
	 * @param workbookDescFactory the workbook desc factory
	 */
	public SheetSession(Class<?> beanType, WorkbookDescFactory workbookDescFactory) {
		this.workbookDescFactory = workbookDescFactory;
		this.beanType = beanType;
		this.workbookType = workbookDescFactory.checkIfWorkbook(beanType);
		workbookDesc = getOrCreateWorkbookDesc(beanType);
		workbookBean = workbookDesc.createWorkbookInstance();
		reset();
	}

	
	/**
	 * Reset.
	 */
	public void reset() {
		this.workbook = new HSSFWorkbook();
		cellStyleCache.clear();
	}

	/**
	 * Load.
	 *
	 * @param is the is
	 */
	public void load(InputStream is) {
		try {
			this.workbook = new HSSFWorkbook(is);
		} catch (IOException e) {
			throw new SheetSystemException(ErrorCodeSheet.LOAD_XLS_FAILED, e);
		}
	}

	/**
	 * Gets the workbook style.
	 *
	 * @param column the column
	 * @param style the style
	 * @return the workbook style
	 */
	public WorkbookStyle getWorkbookStyle(SheetColumnObject column,
			SheetCellStyleObject style) {
		WorkbookStyle cachedStyle = cellStyleCache.get(style);
		if (cachedStyle == null) {
			cachedStyle = new WorkbookStyle(workbook, column, style);
			cellStyleCache.put(style, cachedStyle);
		}
		return cachedStyle;
	}

	/**
	 * Load.
	 *
	 * @param data the data
	 */
	public void load(WT data) {
		List<? extends SheetDescIF<?,WT>> orderedSheets = workbookDesc.getOrderedSheets();
		for (SheetDescIF<?,WT> sheetDesc : orderedSheets) {
			Collection<?> sheetData = null;
			if (data != null) {
				sheetData = sheetDesc.getSheetData(data);
			}
			loadSheetData(sheetDesc, sheetData);
		}
	}

	/**
	 * Load.
	 *
	 * @param data the data
	 */
	public void load(Collection<?> data) {
		List<? extends SheetDescIF<?, WT>> orderedSheets = workbookDesc.getOrderedSheets();
		for (SheetDescIF<?, WT> sheetDesc : orderedSheets) {
			loadSheetData(sheetDesc, data);
		}
	}

	/**
	 * Load sheet data.
	 *
	 * @param sheetDesc the sheet desc
	 * @param sheetData the sheet data
	 */
	private void loadSheetData(SheetDescIF<?, WT> sheetDesc, Collection<?> sheetData) {
		sheetDesc.createSheet(sheetData, this);
	}

	/**
	 * Gets the or create workbook desc.
	 *
	 * @param clazz the clazz
	 * @return the or create workbook desc
	 */
	@SuppressWarnings("unchecked")
	private WorkbookDescIF<WT> getOrCreateWorkbookDesc(Class<?> clazz) {
		WorkbookDescIF<WT> workbookDesc = (WorkbookDescIF<WT>) WORKBOOKDESC_CACHE.get(clazz);
		if (workbookDesc == null) {
			synchronized (WORKBOOKDESC_CACHE) {
				workbookDesc = (WorkbookDescIF<WT>) WORKBOOKDESC_CACHE.get(clazz);
				if (workbookDesc == null) {
					if (workbookDescFactory.checkIfWorkbook(clazz)) {
						// is workbook
//						workbookDesc = new WorkbookDesc<WT>((Class<WT>) clazz);
						workbookDesc = workbookDescFactory.createWorkbookDesc((Class<WT>)clazz);
					} else {
						// is sheet
//						workbookDesc = new WorkbookDesc<WT>();
						workbookDesc = workbookDescFactory.emptyWorkbookDesc((Class<WT>) clazz);
						workbookDesc.addSheet(clazz, null, null, 0);
					}
					WORKBOOKDESC_CACHE.put(clazz, workbookDesc);
				}
			}
		}
		return workbookDesc;
	}

	/**
	 * Save.
	 *
	 * @param os the os
	 */
	public void save(OutputStream os) {
		try {
			workbook.write(os);
		} catch (IOException e) {
			throw new SheetSystemException(ErrorCodeSheet.SAVE_XLS_FAILED, e);
		}
	}
	
	/**
	 * To record list.
	 *
	 * @return the list
	 */
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

	/**
	 * To workbook bean.
	 *
	 * @return the wt
	 */
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

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.annotation.ISheetSession#getObjectByKey(java.lang.Class, java.lang.Object)
	 */
	public <T> T getObjectByKey(Class<T> recordClass, Object key) {
		return (T) key2objectCache.get(Arrays.asList(recordClass, key));
	}

	/* (non-Javadoc)
	 * @see org.adorsys.xlseasy.annotation.ISheetSession#setObjectByKey(java.lang.Class, java.lang.Object, java.lang.Object)
	 */
	public <T> void setObjectByKey(Class<T> recordClass, Object key, T object) {
		if (key != null) {
			key2objectCache.put(Arrays.asList(recordClass, key), object);
		}
	}
	
}
