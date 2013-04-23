package org.adorsys.xlseasy.impl.proc;

import org.adorsys.xlseasy.annotation.CellAlign;
import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.SheetCellStyleObject;
import org.adorsys.xlseasy.annotation.SheetColumnObject;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class WorkbookStyle {

	/** The workbook style. */
	private final HSSFCellStyle style;

	/**
	 * Instantiates a new workbook style.
	 * 
	 * @param workbook
	 *            the workbook
	 * @param column
	 *            the column
	 * @param style
	 *            the style
	 */
	public WorkbookStyle(HSSFWorkbook workbook, SheetColumnObject column,
			SheetCellStyleObject style) {
		super();

		// Creates a cell's style for the workbook.
		HSSFCellStyle cellStyle = workbook.createCellStyle();

		// Gets the data format as String.
		String dataFormat = style.dataFormat();

		// If the data format wasn't setted, sets it.
		if (StringUtils.isNotEmpty(dataFormat)) {
			short format = workbook.createDataFormat().getFormat(dataFormat);
			if (format == -1) {
				throw new SheetSystemException(
						ErrorCodeSheet.UNKNOWN_CELL_FORMAT).addValue(
						"dataFormat", dataFormat);
			}
			cellStyle.setDataFormat(format);
		}

		// Gets the font's name as String
		String fontName = style.fontName();

		// Gets font's properties as corresponding data type
		int fontSize = style.fontSize();
		boolean fontStyleBold = style.fontStyleBold();
		boolean fontStyleItalic = style.fontStyleItalic();
		boolean fontStyleStrikeout = style.fontStyleStrikeout();
		boolean fontStyleUnderline = style.fontStyleUnderline();
		short fontColor = style.fontColor();

		// If the cell's font (or one of the called properties) wasn't setted, sets it
		// (them).
		if (StringUtils.isNotEmpty(fontName) || fontStyleBold
				|| fontStyleItalic || fontStyleStrikeout || fontStyleUnderline
				|| fontSize != -1 || fontColor != -1) {
			HSSFFont font = workbook.createFont();
			if (fontStyleBold)
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			if (fontSize > 0)
				font.setFontHeightInPoints((short) fontSize);
			if (fontStyleItalic)
				font.setItalic(true);
			if (fontStyleStrikeout)
				font.setStrikeout(true);
			if (fontStyleUnderline)
				font.setUnderline(HSSFFont.U_SINGLE);
			if (fontColor != -1) {
				font.setColor(fontColor);
			}
			cellStyle.setFont(font);
		}

		if (style.backgroundColor() != -1) {
			cellStyle.setFillBackgroundColor(style.backgroundColor());
		}

		// Sets the cell's alignment
		if (CellAlign.LEFT.equals(style.align()))
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		else if (CellAlign.RIGHT.equals(style.align()))
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		else if (CellAlign.CENTER.equals(style.align()))
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		else if (CellAlign.JUSTIFY.equals(style.align()))
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);

		if (style.wrapText())
			cellStyle.setWrapText(true);

		if (column.hidden())
			cellStyle.setHidden(true);

		// Saves the setted properties to cell's style
		this.style = cellStyle;
	}

	/** Applies the format to the Workbook. */
	public void applyFormat(HSSFCell cell) {
		cell.setCellStyle(style);
	}
}