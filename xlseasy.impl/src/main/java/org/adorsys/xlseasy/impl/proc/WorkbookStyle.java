package org.adorsys.xlseasy.impl.proc;

import org.adorsys.xlseasy.annotation.CellAlign;
import org.adorsys.xlseasy.annotation.ErrorCodeSheet;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;
import org.adorsys.xlseasy.annotation.SheetSystemException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

class WorkbookStyle {
	private final HSSFCellStyle style;

	/**
	 * @param style
	 * @param format
	 * @param font
	 */
	public WorkbookStyle(HSSFWorkbook workbook, SheetColumn column, SheetCellStyle style) {
		super();
		HSSFCellStyle cellStyle = workbook.createCellStyle();

		String dataFormat = style.dataFormat();
		if (StringUtils.isNotEmpty(dataFormat)) {
			short format = workbook.createDataFormat()
					.getFormat(dataFormat);
			if (format == -1) {
				throw new SheetSystemException(
						ErrorCodeSheet.UNKNOWN_CELL_FORMAT).addValue(
						"dataFormat", dataFormat);
			}
			cellStyle.setDataFormat(format);
		}

		String fontName = style.fontName();
		int fontSize = style.fontSize();
		boolean fontStyleBold = style.fontStyleBold();
		boolean fontStyleItalic = style.fontStyleItalic();
		boolean fontStyleStrikeout = style.fontStyleStrikeout();
		boolean fontStyleUnderline = style.fontStyleUnderline();
		short fontColor = style.fontColor();
		if (StringUtils.isNotEmpty(fontName) || fontStyleBold
				|| fontStyleItalic || fontStyleStrikeout
				|| fontStyleUnderline || fontSize != -1 || fontColor != -1) {
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

		this.style = cellStyle;
	}

	public void applyFormat(HSSFCell cell) {
		cell.setCellStyle(style);
	}
	
	

}