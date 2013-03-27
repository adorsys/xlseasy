import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Generates a multi-line report for any client and saves it into an Excel (.xls) file.
 * 
 */
public class ExcelGenerateReport {

	private HSSFCellStyle cs, csBold, csTop, csRight, csBottom, csLeft, csTopLeft, csTopRight, csBottomLeft, csBottomRight;

	public void createExcel() {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Excel Report");

			// setup some style that we need for the cells
			setCellStyles(wb);

			// get current date and time
			Date date = new Date(System.currentTimeMillis());
			DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

			// set column widths
			sheet.setColumnWidth(0, 2500);
			sheet.setColumnWidth(1, 2500);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 10000);
			sheet.setColumnWidth(4, 3000);

			// setup the page margins (top, right, bottom, left)
			sheet.setMargin(Sheet.TopMargin, 0.75);
			sheet.setMargin(Sheet.RightMargin, 0.25);
			sheet.setMargin(Sheet.BottomMargin, 0.75);
			sheet.setMargin(Sheet.LeftMargin, 0.25);

			// setup header and footer margins
			sheet.setMargin(Sheet.HeaderMargin, 0.25);
			sheet.setMargin(Sheet.FooterMargin, 0.25);

			// If you are using HSSFWorkbook() instead of XSSFWorkbook()
			// HSSFPrintSetup ps = (HSSFPrintSetup) sheet.getPrintSetup();
			// ps.setHeaderMargin((double) .25);
			// ps.setFooterMargin((double) .25);

			// Set Header Information
			HSSFHeader header = sheet.getHeader();
			header.setLeft("*** ORIGINAL COPY ***");
			header.setCenter(HSSFHeader.font("Arial", "Bold")
					+ HSSFHeader.fontSize((short) 14) + "SAMPLE ORDER");
			header.setRight(df.format(date));

			// Set Footer Information with Page Numbers
			HSSFFooter footer = sheet.getFooter();
			footer.setRight("Page " + HeaderFooter.page() + " of "
					+ HeaderFooter.numPages());

			int rowIndex = 0;
			rowIndex = insertHeaderInfo(sheet, rowIndex);
			rowIndex = insertDetailInfo(sheet, rowIndex);

			// Write the Excel file
			FileOutputStream fileOut = new FileOutputStream("myExcelReport.xls");
			wb.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void setCellStyles(HSSFWorkbook wb) {
		// font size 12
		HSSFFont f = wb.createFont();
		f.setFontHeightInPoints((short) 12);

		// Simple style
		cs = wb.createCellStyle();
		cs.setFont(f);

		// Bold Fond
		HSSFFont bold = wb.createFont();
		bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		bold.setFontHeightInPoints((short) 12);

		// Bold style
		csBold = wb.createCellStyle();
		csBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBold.setFont(bold);

		// Setup style for Top Border Line
		csTop = wb.createCellStyle();
		csTop.setBorderTop(HSSFCellStyle.BORDER_THIN);
		csTop.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csTop.setFont(f);

		// Setup style for Right Border Line
		csRight = wb.createCellStyle();
		csRight.setBorderRight(HSSFCellStyle.BORDER_THIN);
		csRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csRight.setFont(f);

		// Setup style for Bottom Border Line
		csBottom = wb.createCellStyle();
		csBottom.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		csBottom.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBottom.setFont(f);

		// Setup style for Left Border Line
		csLeft = wb.createCellStyle();
		csLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		csLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csLeft.setFont(f);

		// Setup style for Top/Left corner cell Border Lines
		csTopLeft = wb.createCellStyle();
		csTopLeft.setBorderTop(HSSFCellStyle.BORDER_THIN);
		csTopLeft.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csTopLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		csTopLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csTopLeft.setFont(f);

		// Setup style for Top/Right corner cell Border Lines
		csTopRight = wb.createCellStyle();
		csTopRight.setBorderTop(HSSFCellStyle.BORDER_THIN);
		csTopRight.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csTopRight.setBorderRight(HSSFCellStyle.BORDER_THIN);
		csTopRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csTopRight.setFont(f);

		// Setup style for Bottom/Left corner cell Border Lines
		csBottomLeft = wb.createCellStyle();
		csBottomLeft.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		csBottomLeft.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBottomLeft.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		csBottomLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csBottomLeft.setFont(f);

		// Setup style for Bottom/Right corner cell Border Lines
		csBottomRight = wb.createCellStyle();
		csBottomRight.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		csBottomRight.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBottomRight.setBorderRight(HSSFCellStyle.BORDER_THIN);
		csBottomRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csBottomRight.setFont(f);
	}
	
	public int insertHeaderInfo(HSSFSheet sheet, int index) {
		int rowIndex = index;
		HSSFRow row = null;
		HSSFCell c = null;

		rowIndex++;
		row = sheet.createRow(rowIndex);
		c = row.createCell(0);
		c.setCellValue("Customer No:");
		c.setCellStyle(csTopLeft);
		c = row.createCell(1);
		c.setCellStyle(csTop);
		c = row.createCell(2);
		c.setCellValue("ABC");
		c.setCellStyle(csTopRight);

		rowIndex++;
		row = sheet.createRow(rowIndex);
		c = row.createCell(0);
		c.setCellValue("Order No:");
		c.setCellStyle(csLeft);
		c = row.createCell(2);
		c.setCellValue("123456");
		c.setCellStyle(csRight);

		rowIndex++;
		row = sheet.createRow(rowIndex);
		c = row.createCell(0);
		c.setCellStyle(csLeft);
		c = row.createCell(2);
		c.setCellStyle(csRight);

		rowIndex++;
		row = sheet.createRow(rowIndex);
		c = row.createCell(0);
		c.setCellValue("Name:");
		c.setCellStyle(csLeft);
		c = row.createCell(2);
		c.setCellValue("ABC Customer");
		c.setCellStyle(csRight);

		rowIndex++;
		row = sheet.createRow(rowIndex);
		c = row.createCell(0);
		c.setCellValue("Address:");
		c.setCellStyle(csLeft);
		c = row.createCell(2);
		c.setCellValue("123 Street No.");
		c.setCellStyle(csRight);

		rowIndex++;
		row = sheet.createRow(rowIndex);
		c = row.createCell(0);
		c.setCellStyle(csLeft);
		c = row.createCell(2);
		c.setCellValue("City, State ZIPCODE");
		c.setCellStyle(csRight);

		rowIndex++;
		row = sheet.createRow(rowIndex);
		c = row.createCell(0);
		c.setCellStyle(csBottomLeft);
		c = row.createCell(1);
		c.setCellStyle(csBottom);
		c = row.createCell(2);
		c.setCellValue("U.S.A.");
		c.setCellStyle(csBottomRight);
		
		rowIndex = rowIndex + 3;
		row = sheet.createRow(rowIndex);
		c = row.createCell(0);
		c.setCellValue("Line No");
		c.setCellStyle(csBold);
		c = row.createCell(1);
		c.setCellValue("Quantity");
		c.setCellStyle(csBold);
		c = row.createCell(2);
		c.setCellValue("Item No");
		c.setCellStyle(csBold);
		c = row.createCell(3);
		c.setCellValue("Description");
		c.setCellStyle(csBold);
		c = row.createCell(4);
		c.setCellValue("Price");
		c.setCellStyle(csBold);

		return rowIndex;
	}
	
	// returns current time
	public String getDate() {
		// get current time
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		return df.format(date);
	}

	public int insertDetailInfo(HSSFSheet sheet, int index) {
		int rowIndex = 0;
		HSSFRow row = null;
		HSSFCell c = null;

		for (int i = 1; i < 35; i++) {
			rowIndex = index + i;
			row = sheet.createRow(rowIndex);
			c = row.createCell(0);
			c.setCellValue(i);
			c.setCellStyle(cs);
			c = row.createCell(1);
			c.setCellValue(10 + i);
			c.setCellStyle(cs);
			c = row.createCell(2);
			c.setCellValue("ITEM" + i);
			c.setCellStyle(cs);
			c = row.createCell(3);
			c.setCellValue("My ITEM" + i + " Description");
			c.setCellStyle(cs);
			c = row.createCell(4);
			c.setCellValue(1.11 * i);
			c.setCellStyle(cs);
		}
		return rowIndex;
	}

	public static void main(String[] args) {
		ExcelGenerateReport report = new ExcelGenerateReport();
		report.createExcel();
	}
}