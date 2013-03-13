import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * This class shows how to create a basic file excel using the POI API
 */
public class ExcelFromName extends Name {

	private static final String FILENAME = "listOfNames.xls";
	private int NUM_OF_ROWS = 4;
	private int NUM_OF_COLS = getArrayLength() / NUM_OF_ROWS;

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
//		style.setFillForegroundColor(HSSFColor.GOLD.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// We establish a new font for this book
		HSSFFont font = book.createFont();
		font.setColor(HSSFColor.GREEN.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
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
			HSSFSheet sheet = book.createSheet("Name");

			// We create rows. It starts in 0.
			HSSFRow[] row = new HSSFRow[NUM_OF_ROWS];
			
			for (int i = 0; i < row.length; i++) {
				row[i] = sheet.createRow(i);
			}

			// We create cells for each created row. It starts in 0.
			HSSFCell[] cell = new HSSFCell[NUM_OF_COLS];
			
			// Used to count the number of added elements
			int counter = 0;
			
			for (int i = 0; i < row.length; i++) {
				for (int j = 0; j < cell.length; j++) {
					cell[j] = row[i].createCell(j);
					
					// We establish the value for that cell
					if (counter < getArrayLength()) {
						cell[j].setCellValue(name[counter]);
					}
					
					// Optional. We establish the cell style
					cell[j].setCellStyle(getStyle(book));
					
					// Increments the counter
					counter++;
				}
			}

			// Optional. We establish the column wide. When I open an excel I
			// don't like to click over the column to see all the information
			// inside the cell :)
			for (int i = 0; i < cell.length; i++) {			
				sheet.setColumnWidth(i, 15 * 256);				
			}

			// Now it's time to record the data in a file making sure we close
			// the file
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(FILENAME);
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
	 */
	public static void main(String args[]) {
		ExcelFromName excel = new ExcelFromName();
		excel.createExcel();
	}
}
