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

public class ExcelExampleHorizontal extends Name {

	private static final char[] A2Z = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };
	private static final String FILE_NAME = "excelHorizontal.xls";
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
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment((short) 0x2);

		// We establish a new font for this book
		HSSFFont font = book.createFont();
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
			HSSFSheet sheet = book.createSheet("1st Sheet");

			// We create rows and cells and set their values.
			HSSFRow row;
			HSSFCell cell;

			// We create a style to apply on some cell
			HSSFCellStyle style = book.createCellStyle();
			
			// 2nd row
			cellIndex++;
			row = sheet.createRow(1);
			cell = row.createCell(cellIndex);
			for (int i = 0; i < getArrayLength(); i++) {
				cell = row.createCell(cellIndex);
				cell.setCellValue((i + 1) + ".");
				cellIndex++;
			}			
			cellIndex = 1;

			
			// 3rd row
			row = sheet.createRow(2);
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
			row = sheet.createRow(3);
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
			row = sheet.createRow(4);
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
			row = sheet.createRow(5);
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
			sheet.setColumnWidth(1, 4000);
			
			// setup other column's width iteratively
			for (int i = 2; i < getArrayLength() + 2; i++) {
				sheet.setColumnWidth(i, 3000);
			}

			// setup page's margin (top, right, bottom, left)
			sheet.setMargin(Sheet.TopMargin, 0.75);
			sheet.setMargin(Sheet.RightMargin, 0.25);
			sheet.setMargin(Sheet.BottomMargin, 0.75);
			sheet.setMargin(Sheet.LeftMargin, 0.25);

			// setup header and footer margins
			sheet.setMargin(Sheet.HeaderMargin, 0.25);
			sheet.setMargin(Sheet.FooterMargin, 0.25);

			// Now it's time to record the data in a file making sure we close
			// the file
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(FILE_NAME);
				book.write(output);
				output.close();				
				System.out.println("Excel's file written successfully!");
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

	// returns the excel cell number (eg. C11, E4, AD1305 etc.) for this cell.
	public String getCellRefString(int row, int col) {
		StringBuffer retval = new StringBuffer();
		int tempcellnum = col;
		do {
			retval.insert(0, A2Z[tempcellnum % 26]);
			tempcellnum = (tempcellnum / 26) - 1;
		} while (tempcellnum >= 0);
		retval.append(row + 1);

		return retval.toString().toUpperCase();
	}

	// returns the excel cell name (eg. C, E, AB, ABC etc.) for this cell.
	public String getCellByName(int row, int col) {
		String cellName = getCellRefString(row, col);
		String result = new String();
		char tmp;

		for (int i = 0; i < cellName.length(); i++) {
			tmp = cellName.charAt(i);
			if (isInAlphabet(tmp)) {
				result += cellName.charAt(i);
			} else {
				break;
			}
		}
		return result;
	}

	// check if a char is in the alphabet
	public boolean isInAlphabet(char search) {
		for (int i = 0; i < A2Z.length; i++) {
			if (search == A2Z[i])
				return true;
		}
		return false;
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
		ExcelExampleHorizontal excel = new ExcelExampleHorizontal();
		excel.createExcel();
	}
}
