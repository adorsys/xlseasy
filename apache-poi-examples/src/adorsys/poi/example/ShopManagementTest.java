package adorsys.poi.example;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import adorsys.poi.example.utils.*;

/**
 * Simulates the management of a shop using our implemented classes (Client,
 * Product, ShopManagement, MyExcelFunction).
 * 
 * @author Marius Guede
 */
public class ShopManagementTest {

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
			// creates the workbook
			HSSFWorkbook book = new HSSFWorkbook();

			// creates the sheet for this book
			HSSFSheet sheet = book.createSheet("Shop Manager");
			
			// We create a new Function Object.
			Function function = new Function();

			// the following line allow to create a sheet and add sheet's name
			// with special characters such as [], ? # + %$�"
			// book.createSheet(WorkbookUtil.createSafeSheetName("?789.[]uyfa^!$#*%"));

			// creates row and cell
			HSSFRow row;
			HSSFCell cell;

			// creates 3 clients
			Client client1 = new Client("Siegfried Mueller", "Bahnhofplatz 1",
					90489, "Nuremberg", "Germany");
			Client client2 = new Client("Antoine David", "Rue des Joffres 34",
					20489, "Marseille", "France");
			Client client3 = new Client("James Houston", "New-Town Way 10",
					10489, "Los Angeles", "USA");

			// creates 3 products
			Product product1 = new Product("ASUS 8.0", "Notebook", 1200.0);
			Product product2 = new Product("Samsung Galaxy Tab 2 10.1",
					"Tablet", 340.0);
			Product product3 = new Product("Seagate USB", "USB Stick - 8GB",
					6.0);

			// creates the 3 shop managers for 3 purchases
			ShopManagement manager1 = new ShopManagement(client1, product2, 2);
			ShopManagement manager2 = new ShopManagement(client2, product1, 7);
			ShopManagement manager3 = new ShopManagement(client3, product3, 1);

			// creates an ArrayList of ShopManagement and saves our manager into
			// it
			List<ShopManagement> managerList = new ArrayList<ShopManagement>();
			managerList.add(manager1);
			managerList.add(manager2);
			managerList.add(manager3);

			// creates an array to save the arrayList
			ShopManagement[] managerListArray = ShopManagement
					.ArrayList(managerList);

			// creates 2 new sheets for clients and products
			HSSFSheet sheetClients = book.createSheet("Clients");
			HSSFSheet sheetProducts = book.createSheet("Products");

			// creates rows and cells for our new sheets
			HSSFRow rowClient, rowProduct;
			HSSFCell cellClient, cellProduct;

			// writes in clients sheet
			rowClient = sheetClients.createRow(1);
			
			
			cellClient = rowClient.createCell(1);
			cellClient.setCellValue("ID");
			cellClient.setCellStyle(function.getMyDefaultStyle(book));

			cellClient = rowClient.createCell(2);
			cellClient.setCellValue("Name");
			cellClient.setCellStyle(function.getMyDefaultStyle(book));

			cellClient = rowClient.createCell(3);
			cellClient.setCellValue("Street");
			cellClient.setCellStyle(function.getMyDefaultStyle(book));

			cellClient = rowClient.createCell(4);
			cellClient.setCellValue("Zipcode");
			cellClient.setCellStyle(function.getMyDefaultStyle(book));

			cellClient = rowClient.createCell(5);
			cellClient.setCellValue("City");
			cellClient.setCellStyle(function.getMyDefaultStyle(book));

			cellClient = rowClient.createCell(6);
			cellClient.setCellValue("Country");
			cellClient.setCellStyle(function.getMyDefaultStyle(book));

			for (int i = 2; i < managerListArray.length + 2; i++) {
				rowClient = sheetClients.createRow(i);
				
				cellClient = rowClient.createCell(1);
				function.setMyCellAlignment(book, cellClient, defaultAlignment);
				cellClient.setCellValue(managerListArray[i - 2].getClient()
						.getId());

				cellClient = rowClient.createCell(2);
				function.setMyCellAlignment(book, cellClient, defaultAlignment);
				cellClient.setCellValue(managerListArray[i - 2].getClient()
						.getName());

				cellClient = rowClient.createCell(3);
				function.setMyCellAlignment(book, cellClient, defaultAlignment);
				cellClient.setCellValue(managerListArray[i - 2].getClient()
						.getStreet());

				cellClient = rowClient.createCell(4);
				function.setMyCellAlignment(book, cellClient, defaultAlignment);
				cellClient.setCellValue(managerListArray[i - 2].getClient()
						.getZipcode());

				cellClient = rowClient.createCell(5);
				function.setMyCellAlignment(book, cellClient, defaultAlignment);
				cellClient.setCellValue(managerListArray[i - 2].getClient()
						.getCity());

				cellClient = rowClient.createCell(6);
				function.setMyCellAlignment(book, cellClient, defaultAlignment);
				cellClient.setCellValue(managerListArray[i - 2].getClient()
						.getCountry());
			}

			// writes in products sheet
			rowProduct = sheetProducts.createRow(1);
			cellProduct = rowProduct.createCell(1);
			cellProduct.setCellValue("Name");
			cellProduct.setCellStyle(function.getMyDefaultStyle(book));

			cellProduct = rowProduct.createCell(2);
			cellProduct.setCellValue("Description");
			cellProduct.setCellStyle(function.getMyDefaultStyle(book));

			cellProduct = rowProduct.createCell(3);
			cellProduct.setCellValue("Price");
			cellProduct.setCellStyle(function.getMyDefaultStyle(book));

			for (int i = 2; i < managerListArray.length + 2; i++) {
				rowProduct = sheetProducts.createRow(i);
				cellProduct = rowProduct.createCell(1);
				function.setMyCellAlignment(book, cellProduct, defaultAlignment);
				cellProduct.setCellValue(managerListArray[i - 2].getProduct()
						.getName());

				cellProduct = rowProduct.createCell(2);
				function.setMyCellAlignment(book, cellProduct, defaultAlignment);
				cellProduct.setCellValue(managerListArray[i - 2].getProduct()
						.getDesc());

				cellProduct = rowProduct.createCell(3);
				function.setMyCellAlignment(book, cellProduct, defaultAlignment);
				cellProduct.setCellValue(managerListArray[i - 2].getProduct()
						.getPrice());
			}

			// 1st row
			rowIndex++;
			row = sheet.createRow(rowIndex);

			// saves the header's title
			String[] headerTitle = { "Client's ID", "Client",
					"Delivery address", "Article", "Price / article",
					"Description", "Qty", "Total" };

			// writes header's title into the row
			for (int i = 0; i < headerTitle.length; i++) {
				cell = row.createCell(i + 1);
				cell.setCellValue(headerTitle[i]);
				cell.setCellStyle(function.getMyDefaultStyle(book));
			}

			// used to save the row of first, last product and the column reference with prices and compute the total price for all articles
			String totalPriceFormular = new String();
			
			// other rows
			for (int i = 0; i < managerList.size(); i++) {
				rowIndex++;
				row = sheet.createRow(rowIndex);
				cell = row.createCell(0);
				cell.setCellValue((i + 1) + ".");
				function.setMyCellAlignment(book, cell, defaultAlignment);
				function.setMyCellFont(book, cell, HSSFFont.BOLDWEIGHT_BOLD,
						defaultFontHeightInPoints);

				cell = row.createCell(1);
				cell.setCellValue(managerListArray[i].getClient().getId());
				function.setMyCellAlignment(book, cell, defaultAlignment);

				cell = row.createCell(2);
				cell.setCellValue(managerListArray[i].getClient().getName());
				function.setMyCellAlignment(book, cell, defaultAlignment);

				cell = row.createCell(3);
				cell.setCellValue(managerListArray[i].getClient().getAddress());
				function.setMyCellAlignment(book, cell, defaultAlignment);

				cell = row.createCell(4);
				cell.setCellValue(managerListArray[i].getProduct().getName());
				function.setMyCellAlignment(book, cell, defaultAlignment);

				cell = row.createCell(5);
				cell.setCellValue(managerListArray[i].getProduct().getPrice());
				function.setMyCellAlignment(book, cell, defaultAlignment);

				cell = row.createCell(6);
				cell.setCellValue(managerListArray[i].getProduct().getDesc());
				function.setMyCellAlignment(book, cell, defaultAlignment);

				cell = row.createCell(7);
				cell.setCellValue(function.getRandomIndex());
				function.setMyCellAlignment(book, cell, defaultAlignment);
				
				cell = row.createCell(8);			
				// computes the total price using the article's price and quantity
				cell.setCellFormula(function.getCellByReference(cell.getRowIndex(), 5) + "*" + function.getCellByReference(cell.getRowIndex(), 7));
				function.setMyCellAlignment(book, cell, defaultAlignment);
				
				
				// gets the cell's reference of the first product
				if (i == 0) totalPriceFormular = "SUM(" + function.getCellByReference(cell.getRowIndex(), cell.getColumnIndex());
				
				// gets the cell's reference of the last product
				if (i == managerList.size() - 1) totalPriceFormular += ":" + function.getCellByReference(cell.getRowIndex(), cell.getColumnIndex()) + ")";
			}
			rowIndex++;
			
			// sets formula
			row = sheet.createRow(rowIndex);
			cell = row.createCell(8);
			cell.setCellFormula(totalPriceFormular);
			function.setMyCellAlignment(book, cell, defaultAlignment);
			function.setMyCellFont(book, cell, HSSFFont.BOLDWEIGHT_BOLD, defaultFontHeightInPoints);

			// setups column's width
			for (int i = 0; i < headerTitle.length; i++) {
				sheet.autoSizeColumn(i);
				sheetClients.autoSizeColumn(i);
				sheetProducts.autoSizeColumn(i);
			}

			// setups page's margin (top, right, bottom, left)
			sheet.setMargin(Sheet.TopMargin, 0.75);
			sheet.setMargin(Sheet.RightMargin, 0.25);
			sheet.setMargin(Sheet.BottomMargin, 0.75);
			sheet.setMargin(Sheet.LeftMargin, 0.25);

			// setups header and footer margins
			sheet.setMargin(Sheet.HeaderMargin, 0.25);
			sheet.setMargin(Sheet.FooterMargin, 0.25);

			// records the data in a file, then making sure to close the file
			FileOutputStream output = null;
			try {
				output = new FileOutputStream(FILE_NAME);

				// writes the workbook into the FileOutputStream
				book.write(output);

				// closes the FileOutputStream to avoid any other modifications
				output.close();

				// confirms the creation of the sheet through the console
				System.out.println("The spreadsheet <" + FILE_NAME
						+ "> has been successfull created.");

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
	 * @param args
	 *            Command line parameters. Not required in this example
	 */
	public static void main(String args[]) {
		ShopManagementTest excel = new ShopManagementTest();
		excel.createExcel();
	}
}
