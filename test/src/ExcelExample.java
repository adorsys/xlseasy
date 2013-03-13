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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 
 * This class shows how to create a basic file excel using the POI API
 * 
 * @author <a
 *         href="http://www.ProgrammingWorkshop.net">www.ProgrammingWorkshop.net
 *         </a>
 * @version 1.0
 * 
 */
public class ExcelExample extends Name {

	private static final char[] A2Z = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z'};
	private static final String FILE_NAME = "helloworld.xls";
	private int rowIndex = 0;

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

			// 1st row
			rowIndex++;
			row = sheet.createRow(rowIndex);
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
				row = sheet.createRow(rowIndex);
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
			row = sheet.createRow(rowIndex);
			cell = row.createCell(5);

			// get column reference
			String ref = getCellRefString(cell.getRowIndex(),
					cell.getColumnIndex());
			ref = String.valueOf(ref.charAt(0));

			cell.setCellFormula("SUM(" + ref + "3:" + ref + "37)");
			cell.setCellStyle(getStyle(book));

			// setup column's width
			sheet.setColumnWidth(0, 100);
			sheet.setColumnWidth(1, 4000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 1500);
			sheet.setColumnWidth(4, 700);
			sheet.setColumnWidth(5, 2000);

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
	 * returns the excel cell number (eg. C11, E4, AD1305 etc.) for this cell.
	 */
	public String getCellRefString(int row, int col) {
		StringBuffer retval = new StringBuffer();
		int tempcellnum = col;
		do {
			retval.insert(0, A2Z[tempcellnum % 26]);
			tempcellnum = (tempcellnum / 26) - 1;
		} while (tempcellnum >= 0);
		retval.append(row + 1);

		return retval.toString();
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
