import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelMultipleSheets extends MyExcelFunction {
	
	private static final String FILENAME = "multipleSheets.xls";
	
	public HSSFCellStyle getStyle(HSSFWorkbook book) throws Exception {
		// First of all we have to create the style for this book
		HSSFCellStyle style = book.createCellStyle();

		// We establish the background color
		style.setFillForegroundColor(HSSFColor.GOLD.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// We establish a new font for this book
		HSSFFont font = book.createFont();
		font.setColor(HSSFColor.GREEN.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);

		return style;
	}
	
	public void createExcel() {
		try {
			// creates an workbook
			HSSFWorkbook wb = new HSSFWorkbook();
						
			// creates 2 new sheets
			HSSFSheet sheet1 = wb.createSheet("1st sheet");
			HSSFSheet sheet2 = wb.createSheet("2nd sheet");

			// sheet1: creates 5 rows
			HSSFRow[] row = new HSSFRow[5]; 
			for (int i = 0; i < 5; i++) {
				row[i] = sheet1.createRow(i);				
			}
			
			// sheet1: creates 5 cells pro row
			HSSFCell[] cell = new HSSFCell[5]; 
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					cell[j] = row[i].createCell(j);
					cell[j].setCellValue(getRandomName());
				}
			}
			
			// sheet2: creates 5 rows
			HSSFRow[] row2 = new HSSFRow[5]; 
			for (int i = 0; i < 5; i++) {
				row2[i] = sheet2.createRow(i);				
			}
			
			// sheet2: creates 5 cells pro row
			HSSFCell[] cell2 = new HSSFCell[5]; 
			for (int i = 1; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					cell2[j] = row2[i].createCell(j);
					cell2[j].setCellValue(getRandomIndex());
				}
			}

			// sheet2: write into the first row
			cell2[0] = row2[0].createCell(0);
			cell2[0].setCellValue("Col1");
			cell2[1] = row2[0].createCell(1);
			cell2[1].setCellValue("Col2");
			cell2[2] = row2[0].createCell(2);
			cell2[2].setCellValue("Col3");
			cell2[3] = row2[0].createCell(3);
			cell2[3].setCellValue("Col4");
			cell2[4] = row2[0].createCell(4);
			cell2[4].setCellValue("Col5");
			
			
			/**
			 * sheet2: write into the last row
			 * each cell is the sum of the values of the cell same column over it. */
			
			String cellRef;
			for (int i = 0; i < 5; i++) {
				cell2[i] = row2[4].createCell(i);
				
				// get cell's reference
				cellRef = getCellByName(cell2[i].getRowIndex(), cell2[i].getColumnIndex());

				// save formula into cell
				cell2[i].setCellFormula("SUM(" + cellRef + "2:" + cellRef + "4)");
			}
						
			// creates the excel's file
			FileOutputStream fileOut = new FileOutputStream(FILENAME);
			
			// save our file into the workbook
			wb.write(fileOut);
			
			// close the created file
			fileOut.close();			
			
		} catch (Exception e) {
			System.err.println("Catched error:\n" + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		ExcelMultipleSheets self = new ExcelMultipleSheets();
		self.createExcel();
	}

}
