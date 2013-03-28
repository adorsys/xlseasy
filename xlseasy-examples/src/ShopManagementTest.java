import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Simulates the management of a shop using our implemented classes (Client, Product, ShopManagement, MyExcelFunction).
 * 
 *  @author Marius Guede <mariusguede@urframes.net>
 */
public class ShopManagementTest extends MyExcelFunction {

	/** The current row index. */
	private int rowIndex = 0;
	
	/** The default alignment. */
	private short defaultAlignment = 0x2;
	
	/** The default font height in points. */
	private short defaultFontHeightInPoints = 12;
	
	/** The standard name of the file to generate. */
	private static final String FILE_NAME = "shopManager.xls";

	/**
	 * creates a spreadsheet and records data into it.
	 */
	public void createExcel() {

		try {
			// We create the book. If we'd like to load a book from a file we
			// should write something like this: new HSSFWorkbook(new
			// FileInputStream(file))
			HSSFWorkbook book = new HSSFWorkbook();			

			// We create the sheet for this book
			HSSFSheet sheet1 = book.createSheet("1st Sheet");

			// We create rows and cells and set their values.
			HSSFRow row;
			HSSFCell cell;
			
			/** creates 3 clients */
			Client client1 = new Client("Siegfried", "Bahnhofplatz 1", 90489, "Nuremberg", "Germany");
			Client client2 = new Client("Antoine", "Rue des Joffres 34", 20489, "Marseille", "France");
			Client client3 = new Client("James", "New-Town Way 10", 10489, "Los Angeles", "USA");

			/** creates 3 products */
			Product product1 = new Product("ASUS 8.0", "computer", 1200.0);
			Product product2 = new Product("Samsung 10.0", "tablet", 340.0);
			Product product3 = new Product("USB Stick 8GB", "USB Stick", 6.0);

			/** creates the 3 shop managers for 3 purchases */
			ShopManagement manager1 = new ShopManagement(client1, product1, 2);
			ShopManagement manager2 = new ShopManagement(client2, product2, 7);
			ShopManagement manager3 = new ShopManagement(client3, product3, 1);

			/** creates an ArrayList of ShopManagement and saves our manager into it */
			List<ShopManagement> managerList = new ArrayList<ShopManagement>();
			managerList.add(manager1);
			managerList.add(manager2);
			managerList.add(manager3);
			
			/** creates an array to save the arrayList*/
			ShopManagement[] managerListArray = ShopManagement.ArrayList(managerList);
			
			// 1st row
			rowIndex++;
			row = sheet1.createRow(rowIndex);
			cell = row.createCell(1);
			cell.setCellValue("Client");
			cell.setCellStyle(getMyDefaultStyle(book));

			cell = row.createCell(2);
			cell.setCellValue(new HSSFRichTextString("Delivery address"));
			cell.setCellStyle(getMyDefaultStyle(book));

			cell = row.createCell(3);
			cell.setCellValue(new HSSFRichTextString("Article"));
			cell.setCellStyle(getMyDefaultStyle(book));

			cell = row.createCell(4);
			cell.setCellValue(new HSSFRichTextString("Price / article"));
			cell.setCellStyle(getMyDefaultStyle(book));

			cell = row.createCell(5);
			cell.setCellValue(new HSSFRichTextString("Description"));
			cell.setCellStyle(getMyDefaultStyle(book));

			cell = row.createCell(6);
			cell.setCellValue("Qty");
			cell.setCellStyle(getMyDefaultStyle(book));

			cell = row.createCell(7);
			cell.setCellValue("Total");
			cell.setCellStyle(getMyDefaultStyle(book));

			// other rows
			for (int i = 0; i < managerList.size(); i++) {
				rowIndex++;
				row = sheet1.createRow(rowIndex);
				cell = row.createCell(0);
				cell.setCellValue((i + 1) + ".");
				setMyCellAlignment(book, cell, defaultAlignment);
				setMyCellFont(book, cell, HSSFFont.BOLDWEIGHT_BOLD,
						defaultFontHeightInPoints);

				cell = row.createCell(1);
				cell.setCellValue(managerListArray[i].getClient().getName());
				setMyCellAlignment(book, cell, defaultAlignment);

				cell = row.createCell(2);
				cell.setCellValue(managerListArray[i].getClient().getAddress());
				setMyCellAlignment(book, cell, defaultAlignment);

				cell = row.createCell(3);
				cell.setCellValue(managerListArray[i].getProduct().getName());
				setMyCellAlignment(book, cell, defaultAlignment);

				cell = row.createCell(4);
				cell.setCellValue(managerListArray[i].getProduct().getPrice());
				setMyCellAlignment(book, cell, defaultAlignment);

				cell = row.createCell(5);
				cell.setCellValue(managerListArray[i].getProduct().getDesc());
				setMyCellAlignment(book, cell, defaultAlignment);

				// get random index to compute total price
				int rdm = getRandomIndex();

				cell = row.createCell(6);
				cell.setCellValue(rdm);
				setMyCellAlignment(book, cell, defaultAlignment);

				cell = row.createCell(7);
				cell.setCellValue(rdm * managerListArray[i].getProduct().getPrice());
				setMyCellAlignment(book, cell, defaultAlignment);
			}
			rowIndex++;

			// setup column's width
			sheet1.setColumnWidth(0, 1500);
			sheet1.setColumnWidth(1, 4000);
			sheet1.setColumnWidth(2, 10000);
			sheet1.setColumnWidth(3, 5000);
			sheet1.setColumnWidth(4, 4000);
			sheet1.setColumnWidth(5, 5000);			
			sheet1.setColumnWidth(6, 1200);
			sheet1.setColumnWidth(7, 2000);

			// setup page's margin (top, right, bottom, left)
			sheet1.setMargin(Sheet.TopMargin, 0.75);
			sheet1.setMargin(Sheet.RightMargin, 0.25);
			sheet1.setMargin(Sheet.BottomMargin, 0.75);
			sheet1.setMargin(Sheet.LeftMargin, 0.25);

			// setup header and footer margins
			sheet1.setMargin(Sheet.HeaderMargin, 0.25);
			sheet1.setMargin(Sheet.FooterMargin, 0.25);

			
			// Now it's time to record the data in a file making sure we close
			// the file
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(FILE_NAME);
				book.write(output);
				output.close();

				System.out
						.println("The excel's file has been successfull created.");
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
	 * Create an instance of ExcelExample object and call the createExcel
	 * procedure.
	 *
	 * @param args Command line parameters. Not required in this example
	 */
	public static void main(String args[]) {
		ShopManagementTest excel = new ShopManagementTest();
		excel.createExcel();
	}
}
