package org.adorsys.xlseasy.testcase;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.adorsys.xlseasy.annotation.SpreadsheetService;
import org.adorsys.xlseasy.boot.SpreadSheetServiceBootStrap;
import org.adorsys.xlseasy.testcase.model.*;
import org.junit.Test;

/**
 * The class LoadSpreadsheetWithErrorTest.
 * 
 * In this case, we test the way <strong>xlseasy</strong> analyze the field from
 * the sheet. Also we create in the 3rd sheet named <em>users</em> 5 fields (ID,
 * Pseudo, Name, Email and Password). It's apparently the same one as in the
 * class Users, but the fields in the file begin with an upper case and those
 * from the class with a lower. That's make all the difference because field's names
 * from file must match with them from targeted class.
 * 
 * TODO: To fix this problem, we've to make the recognition case insensitive.
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
		String managementFile = "loadWithErrorTest.xls";

		// Gets the file to load in the resource.
		InputStream managementStream = LoadSpreadsheetWithErrorTest.class
				.getResourceAsStream(managementFile);

		// Creates a new ShopManagement and save the loaded spreadsheet into.
		ShopManagement shopManagement = spreadService.loadSpreadsheet(
				managementStream, ShopManagement.class);

		/**
		 * Verifies if all spreadsheets have been read and objects created.
		 * 
		 * In this case, we're expected 3 objects from the sheets clients and
		 * products and 5 objects from users.
		 */
		assertEquals(2, shopManagement.getClients().size());
		assertEquals(3, shopManagement.getProducts().size());
		assertEquals(1, shopManagement.getUsers().size());

		// Saves the content of the sheet clients in a List<Client>.
		List<Client> clients = shopManagement.getClients();

		// Saves the content of the sheet clients in a List<Product>.
		List<Product> products = shopManagement.getProducts();

		// Saves the content of the sheet clients in a List<User>.
		List<User> users = shopManagement.getUsers();

		// Gets iterators over the elements in the lists
		Iterator<Client> clientIter = clients.iterator();
		Iterator<Product> productIter = products.iterator();
		Iterator<User> userIter = users.iterator();

		// prints clients
		while (clientIter.hasNext()) {
			Client current = clientIter.next();
			System.out
					.println(current.getName() + ":  " + current.getAddress());
			if (!clientIter.hasNext())
				System.out.println();
		}

		// prints products
		while (productIter.hasNext()) {
			Product current = productIter.next();
			System.out.println(current.getName() + ": "
					+ current.getDescription() + " : " + current.getPrice());
			if (!productIter.hasNext())
				System.out.println();
		}

		// prints users
		while (userIter.hasNext()) {
			User current = userIter.next();
			System.out.println(current.getId() + ": " + current.getName() + "<"
					+ current.getEmail() + ">, " + current.getPseudo() + ", "
					+ current.encodedPassword());
			if (!userIter.hasNext())
				System.out.println();
		}

		// These lines will be printed if all tests returned true
		System.out.println("testLoadSpreadsheet: OK");
		System.out.println("The file has been successful loaded.\n");
	}
}