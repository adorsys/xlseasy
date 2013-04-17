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
 * The class ImpTest
 * 
 * @author Marius Guede
 * */
public class ImplTest {

	@Test
	public void testLoadSpreadsheet() {

		/** Creates a new SpreadSheetServiceBootStrap. */
		SpreadSheetServiceBootStrap bootStrap = new SpreadSheetServiceBootStrap();
		
		/** Sets the workbook's class. */
		bootStrap.setWorkbookKlass(ShopManagement.class);
		
		/** Creates a new SpreadsheetService. */
		SpreadsheetService spreadService = bootStrap.createSpreadService();

		/** The file to load. */
		String managementFile = "management.xls";

		/** Find the file to load in the resource. */
		InputStream managementStream = ImplTest.class
				.getResourceAsStream(managementFile);

		/** Creates a new ShopManagement and save the loaded spreadsheet into. */
		ShopManagement shopManagement = spreadService.loadSpreadsheet(
				managementStream, ShopManagement.class);
		
		/**
		 * Verifies if all spreadsheets have been read and objects created.
		 * 
		 * In this case, we're expected 3 object from the sheets clients 
		 * and products. */
		assertEquals(3, shopManagement.getProducts().size());		
		assertEquals(3, shopManagement.getClients().size());
		
		
		/** Saves the content of the sheet clients in a List<Client>. */
		List<Client> clients = shopManagement.getClients();
		
		/**
		 * Sheet: clients
		 * Verifies the data of the first object (line)
		 * 
		 * A line represents an object in this case. Each entries should exist
		 * as a client's attribut (look file Client.java).
		 * */
		assertEquals("Mueller", clients.get(0).getName());
		assertEquals("Goethestr.", clients.get(0).getStreet());
		assertEquals("90559", clients.get(0).getZipcode());
		assertEquals("Nuremberg", clients.get(0).getCity());
		assertEquals("Germany", clients.get(0).getCountry());

		/** Prints all objects from the client's list. */
		System.out.println("### Clients ###");
		for (int i = 0; i < shopManagement.getClients().size(); i++) {
			System.out.print(clients.get(i).getName() + "\t");
			System.out.println(clients.get(i).getAddress());
		}
		
		
		/** Saves the content of the sheet clients in a List<Product>. */
		List<Product> products = shopManagement.getProducts();

		/**
		 * Sheet: products
		 * Verifies the data of the first object (line)
		 * 
		 * A line represents an object in this case. Each entries should exist
		 * as a client's attribut (look file Product.java).
		 * */
		assertEquals("Samsung Galaxy", products.get(0).getName());
		assertEquals("Tablet 10.1", products.get(0).getDescription());
		assertEquals(349.95, products.get(0).getPrice(), 0);

		/** Prints all objects from the product's list. */
		System.out.println("\n### Products ###");
		for (int i = 0; i < shopManagement.getProducts().size(); i++) {
			System.out.print(products.get(i).getName() + "\t");
			System.out.print(products.get(i).getDescription() + "\t");
			System.out.println(products.get(i).getPrice());
		}
	}

	@Test
	public void testSaveSpreadsheet() throws FileNotFoundException {
		SpreadSheetServiceBootStrap bootStrap = new SpreadSheetServiceBootStrap();
		bootStrap.setWorkbookKlass(ShopManagement.class);
		SpreadsheetService spreadService = bootStrap.createSpreadService();

		ShopManagement shopManagement = new ShopManagement();

		Client client1 = new Client("Amzon", "Friedriech", "44524", "Hagen", "England");
		Client client2 = new Client("Bookstore", "Mallinkrot", "44415", "Dortmund", "Germany");

		Product product1 = new Product("Core jsf", "jsf Book", 32.0);
		Product product2 = new Product("Hibernate", "hibernate Book", 25.0);
		//empty row
		Product product3 = new Product(null, null, null); 

		shopManagement.getProducts().add(product1);
		shopManagement.getProducts().add(product2);
		shopManagement.getProducts().add(product3);
		shopManagement.getClients().add(client1);
		shopManagement.getClients().add(client2);
		OutputStream outputStream = null;
		String workbookFile = "target/testmana.xls";

		outputStream = new FileOutputStream(workbookFile);

		spreadService.saveSpreadsheet(ShopManagement.class, shopManagement,
				outputStream);

		InputStream managementStream = new FileInputStream(workbookFile);

		shopManagement = spreadService.loadSpreadsheet(
				managementStream, ShopManagement.class);
		// Verify if all spreadsheets have been read and objects created
		assertTrue(shopManagement.getProducts().size() == 3);

		assertTrue(shopManagement.getClients().size() == 2);
		// Read through Client list and verify entries of Client by
		// iterating
		// through the list and reading the attributes
		List<Client> Clients = shopManagement.getClients();
		client1 = Clients.get(0);
		assertEquals("Amzon", client1.getName());
		assertEquals("Friedriech", client1.getStreet());
		assertEquals("44524", client1.getZipcode());
		assertEquals("Hagen", client1.getCity());
		
		client2 = Clients.get(1);
		assertEquals("Bookstore", client2.getName());
		assertEquals("Mallinkrot", client2.getStreet());
		assertEquals("44415", client2.getZipcode());
		assertEquals("Dortmund", client2.getCity());
		
		// Read through product list and verify entries of product by iterating
		// through the list and reading the attributes
		List<Product> products = shopManagement.getProducts();
		checkProduct(product1, products.get(0));
		checkProduct(product2, products.get(1));
		checkProduct(product3, products.get(2));
	}

	private void checkProduct(Product expected, Product is) {
		assertEquals(expected.getName(), is.getName());
		assertEquals(expected.getDescription(), is.getDescription());
		assertEquals(expected.getPrice() != null ? expected.getPrice() : Double.valueOf(0.0), is.getPrice());
	}

}
