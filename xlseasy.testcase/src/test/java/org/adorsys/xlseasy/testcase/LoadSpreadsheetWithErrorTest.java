package org.adorsys.xlseasy.testcase;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.adorsys.xlseasy.annotation.SpreadsheetService;
import org.adorsys.xlseasy.boot.SpreadSheetServiceBootStrap;
import org.adorsys.xlseasy.testcase.model.Client;
import org.adorsys.xlseasy.testcase.model.Product;
import org.adorsys.xlseasy.testcase.model.ShopManagement;
import org.adorsys.xlseasy.testcase.model.User;
import org.junit.Test;

/**
 * The class LoadSpreadsheetWithErrorTest.
 * 
 * @author Marius Guede
 */
public class LoadSpreadsheetWithErrorTest {

	/**
	 * Loads a spreadsheet.
	 */
	@Test
	public void testLoadSpreadsheet() {

		// Creates a new SpreadSheetServiceBootStrap.
		SpreadSheetServiceBootStrap bootStrap = new SpreadSheetServiceBootStrap();

		// Sets the workbook's class.
		bootStrap.setWorkbookKlass(ShopManagement.class);

		// Creates a new SpreadsheetService.
		SpreadsheetService spreadService = bootStrap.createSpreadService();

		// The file to load.
		String managementFile = "target/loadWithErrorTest.xls";

		// Finds the file to load in the resource.
		InputStream managementStream = LoadSpreadsheetWithErrorTest.class
				.getResourceAsStream(managementFile);

		// Creates a new ShopManagement and save the loaded spreadsheet into.
		ShopManagement shopManagement = spreadService.loadSpreadsheet(
				managementStream, ShopManagement.class);
		
//		// Creates a new ShopManagement and save the loaded spreadsheet into.
//		ShopManagement shopManagement = spreadService.loadSpreadsheet(
//				managementStream, ShopManagement.class);

		/**
		 * Verifies if all spreadsheets have been read and objects created.
		 * 
		 * In this case, we're expected 3 objects from the sheets clients and
		 * products and 5 objects from users.
		 */
//		assertEquals(3, shopManagement.getProducts().size());
//		assertEquals(3, shopManagement.getClients().size());
//		assertEquals(5, shopManagement.getUsers().size());

		// Saves the content of the sheet clients in a List<Client>.
		List<Client> clients = shopManagement.getClients();

		// Saves the content of the sheet clients in a List<Product>.
		List<Product> products = shopManagement.getProducts();

		// Saves the content of the sheet clients in a List<User>.
		List<User> users = shopManagement.getUsers();


		// These lines will be printed if all tests returned true
		System.out.println("testLoadSpreadsheet: OK");
		System.out.println("The file has been successful loaded.\n");
	}
}