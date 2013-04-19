package org.adorsys.xlseasy.self;

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
import org.junit.Test;

/**
 * The class ImpTest.
 *
 * @author Francis Pouatcha
 * @author Sandro Sonntag
 * @author Marius Guede
 */
public class ImplTest {

	/**
	 * Test load spreadsheet.
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

		// Find the file to load in the resource.
		InputStream managementStream = ImplTest.class
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
		 * Sheet: clients
		 * Verifies the data of the first object (line)
		 * 
		 * A line represents an object in this case. Each entries should exist
		 * as a client's attribut (look file Client.java).
		 * */
		Client clientObject = new Client("Mueller", "Goethestr.", "90559", "Nuremberg", "Germany");
		checkClient(clientObject, clients.get(0));		

		// Saves the content of the sheet clients in a List<Product>.
		List<Product> products = shopManagement.getProducts();

		/**
		 * Sheet: products
		 * Verifies the data of the first object (line)
		 * 
		 * A line represents an object in this case. Each entries should exist
		 * as a product's attribut (look file Product.java).
		 * */		
		Product productObject = new Product("Samsung Galaxy", "Tablet 10.1", 349.95);
		checkProduct(productObject, products.get(0));

		// Saves the content of the sheet clients in a List<User>.
		List<User> users = shopManagement.getUsers();

		/**
		 * Sheet: users
		 * Verifies the data of the first object (line)
		 * 
		 * A line represents an object in this case. Each entries should exist
		 * as a user's attribut (look file User.java).
		 * */
		User userObject = new User();
		userObject.setPseudo("flor90");
		userObject.setName("Flore");
		userObject.setEmail("flore@email.com");
		userObject.setPassword("passenger");
		checkUser(userObject, users.get(0));
		
		// It would have been easier to create an user like this:
		// user = User("flor90", "Flore", "flore@email.com", "passenger");
		// but this constructor would increase the number of created users 
		// to 1 and it would be wrong because this object is just a test 
		// and we don't want to count it with the real users
		
		/**
		 * Now the spreadsheet has been sheet by sheet read.
		 * You can prints the content of each sheet in the console 
		 * to make sure it matches with the content of your excel file.
		 * */

		// Prints all objects from the client's list.
		System.out.println("### Clients ###");
		for (int i = 0; i < shopManagement.getClients().size(); i++) {
			System.out.print(clients.get(i).getName() + "\t");
			System.out.println(clients.get(i).getAddress());
		}

		// Prints all objects from the product's list.
		System.out.println("\n### Products ###");
		for (int i = 0; i < shopManagement.getProducts().size(); i++) {
			System.out.print(products.get(i).getName() + "\t");
			System.out.print(products.get(i).getDescription() + "\t");
			System.out.println(products.get(i).getPrice());
		}

		// Prints all objects from the user's list.
		System.out.println("\n### Users ###");
		for (int i = 0; i < shopManagement.getUsers().size(); i++) {
			System.out.print(users.get(i).getId() + "\t");
			System.out.print(users.get(i).getPseudo() + "\t");
			System.out.print(users.get(i).getName() + "\t");
			System.out.print(users.get(i).getEmail() + "\t");
			System.out.println(users.get(i).getEncodedPassword());
		}
	}

	/**
	 * Test save spreadsheet.
	 *
	 * @throws FileNotFoundException the file not found exception
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

		// Creates 2 clients for our test.
		Client client1 = new Client("Adorsys", "Bartolomausstrasse", "90489",
				"Nuremberg", "Germany");
		Client client2 = new Client("Urframes", "Hauptstrasse", "91056",
				"Erlangen", "Germany");

		// Creates 3 products for our test.
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

		// Creates a file stream to open to our file.
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
		System.out.println("The file has been created and the content checked.");
	}
	
	/**
	 * Test a client.
	 * 
	 * @param expected the expected object
	 * @param is the real object
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
	 * @param expected the expected object
	 * @param is the real object
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
	 * @param expected the expected object
	 * @param is the real object
	 */
	private void checkUser(User expected, User is) {
		assertEquals(expected.getPseudo(), is.getPseudo());
		assertEquals(expected.getName(), is.getName());
		assertEquals(expected.getEmail(), is.getEmail());
		assertEquals(expected.getPassword(), is.getPassword());
	}	
}