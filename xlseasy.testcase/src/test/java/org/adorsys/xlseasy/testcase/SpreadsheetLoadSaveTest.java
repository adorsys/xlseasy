package org.adorsys.xlseasy.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.adorsys.xlseasy.annotation.SpreadsheetService;
import org.adorsys.xlseasy.boot.SpreadSheetServiceBootStrap;
import org.adorsys.xlseasy.testcase.model.Client;
import org.adorsys.xlseasy.testcase.model.Product;
import org.adorsys.xlseasy.testcase.model.User;
import org.junit.Test;

import org.adorsys.xlseasy.testcase.model.*;

/**
 * The class ImpTest.
 * 
 * @author Marius Guede
 */
public class SpreadsheetLoadSaveTest {

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
		String managementFile = "management.xls";

		// Finds the file to load in the resource.
		InputStream managementStream = SpreadsheetLoadSaveTest.class
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
		assertEquals(3, shopManagement.getProducts().size());
		assertEquals(3, shopManagement.getClients().size());
		assertEquals(5, shopManagement.getUsers().size());

		// Saves the content of the sheet clients in a List<Client>.
		List<Client> clients = shopManagement.getClients();

		/**
		 * Sheet: clients Verifies the data of the first object (line)
		 * 
		 * A line represents an object in this case. Each entry should exist as
		 * a client's attribute (look file Client.java).
		 * */
		Client clientObject = new Client("Mueller", "Goethestr.", "90559",
				"Nuremberg", "Germany");
		checkClient(clientObject, clients.get(0));

		// Saves the content of the sheet clients in a List<Product>.
		List<Product> products = shopManagement.getProducts();

		/**
		 * Sheet: products Verifies the data of the first object (line)
		 * 
		 * A line represents an object. Each entry should exist as a product's
		 * attribute (look file Product.java).
		 * */
		Product productObject = new Product("Samsung Galaxy", "Tablet 10.1",
				349.95);
		checkProduct(productObject, products.get(0));

		// Saves the content of the sheet clients in a List<User>.
		List<User> users = shopManagement.getUsers();

		/**
		 * Sheet: users Verifies the data of the first object (line)
		 * 
		 * A line represents an object. Each entry should exist as attribute in
		 * the targeted class (look file User.java). For example
		 * */
		User userObject = new User();
		userObject.setPseudo("flor90");
		userObject.setName("Flore");
		userObject.setEmail("flore@email.com");
		userObject.setPassword("passenger");
		checkUser(userObject, users.get(0));

		// It's easier to create an user like using the second constructor:
		// user = User("flor90", "Flore", "flore@email.com", "passenger");
		// but this constructor would increase the number of created users
		// to 1. This would be wrong because this object is just a test
		// and we won't count it with the real users

		/**
		 * Now the spreadsheet has been read sheet by sheet. You can print the
		 * content of each sheet in the console to make sure it matches with the
		 * content of your excel file.
		 * 
		 * For this, just delete the lines 123 and 148.
		 * */

		// These lines will be printed if all tests returned true
		System.out.println("testLoadSpreadsheet: OK");
		System.out.println("The file has been successful loaded.\n");
	}

	/**
	 * Saves a spreadsheet and then loads it.
	 * 
	 * @throws FileNotFoundException
	 *             the exception
	 */
	@Test
	public void testSaveSpreadsheet() throws FileNotFoundException {

		// Creates a new SpreadSheetServiceBootStrap.
		SpreadSheetServiceBootStrap bootStrap = new SpreadSheetServiceBootStrap();

		// Sets the workbook's class.
		bootStrap.setWorkbookKlass(ShopManagement.class);

		// Creates a new SpreadsheetService.
		SpreadsheetService spreadService = bootStrap.createSpreadService();

		// Creates a new ShopManagement to load the spreadsheet.
		ShopManagement shopManagement = new ShopManagement();

		// Creates 2 clients for the test.
		Client client1 = new Client("Adorsys", "Bartolomausstrasse", "90489",
				"Nuremberg", "Germany");
		Client client2 = new Client("Urframes", "Hauptstrasse", "91056",
				"Erlangen", "Germany");

		// Creates 3 products for the test.
		Product product1 = new Product("Processor", "Core Processor", 305.0);
		Product product2 = new Product("CMS Manager", "Templates Licence", 7.5);
		/** now we create a product without description and price */
		Product product3 = new Product("Web Space", null, null);

		// Adds clients and products into targeted Lists.
		shopManagement.getClients().add(client1);
		shopManagement.getClients().add(client2);
		shopManagement.getProducts().add(product1);
		shopManagement.getProducts().add(product2);
		shopManagement.getProducts().add(product3);

		// The file to create.
		String workbookFile = "target/testmana.xls";

		// Creates a file stream to write to our file.
		OutputStream outputStream = new FileOutputStream(workbookFile);

		// Saves the spreadsheet.
		spreadService.saveSpreadsheet(ShopManagement.class, shopManagement,
				outputStream);

		/**
		 * The spreadsheet has been created and save into the file testmana.xls
		 * in the directory target. Now we want to check the content of the
		 * created file. It's enough (for us) to check the first object. of each
		 * sheet
		 */

		// Creates a file stream to open our file.
		InputStream managementStream = new FileInputStream(workbookFile);

		// Loads spreadsheet.
		shopManagement = spreadService.loadSpreadsheet(managementStream,
				ShopManagement.class);

		// Verifies if all spreadsheets have been read and checks the number of
		// created objects. In our case, 2 clients and 3 products.
		assertTrue(shopManagement.getClients().size() == 2);
		assertTrue(shopManagement.getProducts().size() == 3);

		// Saves the content of the sheet clients in a List<Client> and tests
		// the content of the first object.
		List<Client> clients = shopManagement.getClients();
		checkClient(client1, clients.get(0));

		// Saves the content of the sheet products in a List<Product> and tests
		// the content of the first object.
		List<Product> products = shopManagement.getProducts();
		checkProduct(product1, products.get(0));

		// These lines will be printed if all tests returned true
		System.out.println("testSaveSpreadsheet: OK");
		System.out
				.println("The file has been successful created and the content loaded (and checked).");
	}

	/**
	 * Test a client.
	 * 
	 * @param expected
	 *            the expected object
	 * @param is
	 *            the real object
	 */
	private void checkClient(Client expected, Client is) {
		assertEquals(expected.getName(), is.getName());
		assertEquals(expected.getStreet(), is.getStreet());
		assertEquals(expected.getZipcode(), is.getZipcode());
		assertEquals(expected.getCity(), is.getCity());
		assertEquals(expected.getCountry(), is.getCountry());
	}

	/**
	 * Test a product.
	 * 
	 * @param expected
	 *            the expected object
	 * @param is
	 *            the real object
	 */
	private void checkProduct(Product expected, Product is) {
		assertEquals(expected.getName(), is.getName());
		assertEquals(expected.getDescription(), is.getDescription());
		assertEquals(
				expected.getPrice() != null ? expected.getPrice()
						: Double.valueOf(0.0), is.getPrice());
	}

	/**
	 * Test an user.
	 * 
	 * @param expected
	 *            the expected object
	 * @param is
	 *            the real object
	 */
	private void checkUser(User expected, User is) {
		assertEquals(expected.getPseudo(), is.getPseudo());
		assertEquals(expected.getName(), is.getName());
		assertEquals(expected.getEmail(), is.getEmail());
		assertEquals(expected.getPassword(), is.getPassword());
	}
}