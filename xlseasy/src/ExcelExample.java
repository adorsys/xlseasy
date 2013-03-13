import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelExample extends MyExcelFunction {

	private static final String FILE_NAME = "excelExample.xls";
	private int rowIndex = 0;
	private int cellIndex = 1;

	/**
	 * 
	 * This function creates a new style for a book
	 * 
	 * @param book
	 *            This is the book where we are going to create the style
	 * @return We return the style created for the book
	 * @throws Exception
	 * 
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook book) throws Exception {
		// First of all we have to create the style for this book
		HSSFCellStyle style = book.createCellStyle();

		// We establish the background color
		style.setFillForegroundColor(HSSFColor.LIME.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment((short) 0x2);

		// We establish a new font for this book
		HSSFFont font = book.createFont();
		font.setColor(HSSFColor.GREEN.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);

		return style;
	}

	/**
	 * 
	 * This procedure create a book, sheet, row and cell in this order. Also it
	 * records the data into the FILE_NAME.
	 * 
	 */
	public void createExcel() {

		try {
			// We create the book. If we'd like to load a book from a file we
			// should write something like this: new HSSFWorkbook(new
			// FileInputStream(file))
			HSSFWorkbook book = new HSSFWorkbook();

			// We create the sheet for this book
			HSSFSheet sheet1 = book.createSheet("1st Sheet");
			HSSFSheet sheet2 = book.createSheet("2nd Sheet");

			// We create rows and cells and set their values.
			HSSFRow row;
			HSSFCell cell;

			// 1st row
			rowIndex++;
			row = sheet1.createRow(rowIndex);
			cell = row.createCell(1);
			cell.setCellValue("Client's name");
			cell.setCellStyle(getStyle(book));

			cell = row.createCell(2);
			cell.setCellValue(new HSSFRichTextString("Price / article"));
			cell.setCellStyle(getStyle(book));

			cell = row.createCell(3);
			cell.setCellValue("Qty");
			cell.setCellStyle(getStyle(book));

			cell = row.createCell(5);
			cell.setCellValue("Total");
			cell.setCellStyle(getStyle(book));

			// other rows
			for (int i = 0; i < getArrayLength(); i++) {
				HSSFCellStyle style = book.createCellStyle();

				rowIndex++;
				row = sheet1.createRow(rowIndex);
				cell = row.createCell(0);
				cell.setCellValue((i + 1) + ".");
				style.setAlignment((short) 0x2);
				cell.setCellStyle(style);

				cell = row.createCell(1);
				cell.setCellValue(name[i]);
				style.setAlignment((short) 0x1);
				cell.setCellStyle(style);

				cell = row.createCell(2);
				cell.setCellValue("2.5 EUR");
				style.setAlignment((short) 0x3);
				cell.setCellStyle(style);

				// get random index to compute total price
				int rdm = getRandomIndex();

				cell = row.createCell(3);
				cell.setCellValue(rdm);
				style.setAlignment((short) 0x2);
				cell.setCellStyle(style);

				cell = row.createCell(5);
				cell.setCellValue(rdm * 2.5);
				style.setAlignment((short) 0x2);
				cell.setCellStyle(style);
			}
			rowIndex++;
			row = sheet1.createRow(rowIndex);
			cell = row.createCell(5);

			// get column reference
			String ref = getCellByName(cell.getRowIndex(),
					cell.getColumnIndex());

			cell.setCellFormula("SUM(" + ref + "3:" + ref + "37)");
			cell.setCellStyle(getStyle(book));

			// setup column's width
			sheet1.setColumnWidth(0, 1500);
			sheet1.setColumnWidth(1, 4000);
			sheet1.setColumnWidth(2, 4000);
			sheet1.setColumnWidth(3, 1500);
			sheet1.setColumnWidth(4, 700);
			sheet1.setColumnWidth(5, 2000);

			// setup page's margin (top, right, bottom, left)
			sheet1.setMargin(Sheet.TopMargin, 0.75);
			sheet1.setMargin(Sheet.RightMargin, 0.25);
			sheet1.setMargin(Sheet.BottomMargin, 0.75);
			sheet1.setMargin(Sheet.LeftMargin, 0.25);

			// setup header and footer margins
			sheet1.setMargin(Sheet.HeaderMargin, 0.25);
			sheet1.setMargin(Sheet.FooterMargin, 0.25);
			
			
			
			/**
			 * Write now on Sheet 2
			 * */
			
			// We create a style to apply on some cell
			HSSFCellStyle style = book.createCellStyle();
			
			// 2nd row
			cellIndex++;
			row = sheet2.createRow(1);
			cell = row.createCell(cellIndex);
			for (int i = 0; i < getArrayLength(); i++) {
				cell = row.createCell(cellIndex);
				cell.setCellValue((i + 1) + ".");
				cellIndex++;
			}			
			cellIndex = 1;

			
			// 3rd row
			row = sheet2.createRow(2);
			cell = row.createCell(cellIndex);
			cell.setCellValue("Client's name");
			cell.setCellStyle(getStyle(book));
			cellIndex++;
			
			for (int i = 0; i < getArrayLength(); i++) {
				cell = row.createCell(cellIndex);
				cell.setCellValue(name[i]);
				cellIndex++;
			}			
			cellIndex = 1;

			
			// 4th row
			row = sheet2.createRow(3);
			cell = row.createCell(cellIndex);
			cell.setCellValue(new HSSFRichTextString("Price / article"));
			cell.setCellStyle(getStyle(book));
			cellIndex++;
			
			for (int i = 0; i < getArrayLength(); i++) {
				cell = row.createCell(cellIndex);
				cell.setCellValue("2.5 EUR");
				cellIndex++;
			}			
			cellIndex = 1;
			
			
			// 5th row
			row = sheet2.createRow(4);
			cell = row.createCell(cellIndex);
			cell.setCellValue("Qty");
			cell.setCellStyle(getStyle(book));
			cellIndex++;

			// creates an array to save generated random numbers
			int[] myArray = new int[getArrayLength()];
			
			// get random index to compute total price
			int rdm;		
			for (int i = 0; i < getArrayLength(); i++) {
				rdm = getRandomIndex();
				
				// save the random number into our new array
				myArray[i] = rdm;
				
				cell = row.createCell(cellIndex);
				cell.setCellValue(rdm);
				style.setAlignment((short) 0x2);
				cell.setCellStyle(style);
				cellIndex++;
			}			
			cellIndex = 1;

			
			// 6th row
			row = sheet2.createRow(5);
			cell = row.createCell(cellIndex);
			cell.setCellValue("Total");
			cell.setCellStyle(getStyle(book));
			cellIndex++;
			
			for (int i = 0; i < getArrayLength(); i++) {
				
				cell = row.createCell(cellIndex);
				cell.setCellFormula("" + myArray[i] + "*2.5");
				style.setAlignment((short) 0x2);
				cell.setCellStyle(style);
				cellIndex++;
			}			
			cellIndex = 2;

			// setup B-column's width
			sheet2.setColumnWidth(1, 4000);
			
			// setup other column's width iteratively
			for (int i = 2; i < getArrayLength() + 2; i++) {
				sheet2.setColumnWidth(i, 3000);
			}

			// setup page's margin (top, right, bottom, left)
			sheet2.setMargin(Sheet.TopMargin, 0.75);
			sheet2.setMargin(Sheet.RightMargin, 0.25);
			sheet2.setMargin(Sheet.BottomMargin, 0.75);
			sheet2.setMargin(Sheet.LeftMargin, 0.25);

			// setup header and footer margins
			sheet2.setMargin(Sheet.HeaderMargin, 0.25);
			sheet2.setMargin(Sheet.FooterMargin, 0.25);
			

			// Now it's time to record the data in a file making sure we close
			// the file
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(FILE_NAME);
				book.write(output);
				output.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (output != null)
					output.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Create an instance of ExcelExample object and call the createExcel
	 * procedure
	 * 
	 * @param args
	 *            Command line parameters. Not required in this example
	 * 
	 */
	public static void main(String args[]) {
		ExcelExample excel = new ExcelExample();
		excel.createExcel();
	}
}
