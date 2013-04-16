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

public class SpreadsheetServiceImplTest {

	@Test
	public void testLoadSpreadsheet() {

		SpreadSheetServiceBootStrap bootStrap = new SpreadSheetServiceBootStrap();
		bootStrap.setWorkbookKlass(ShopManagement.class);
		SpreadsheetService spreadService = bootStrap.createSpreadService();

		// File to load
		String managementFile = "management.xls";

		// Finds the file to load in the resource
		InputStream managementStream = SpreadsheetServiceImplTest.class
				.getResourceAsStream(managementFile);

		// Loads the spreadsheet 
		ShopManagement shopManagement = spreadService.loadSpreadsheet(
				managementStream, ShopManagement.class);
		
		// Verifies if all spreadsheets have been read and objects created
		assertEquals(4, shopManagement.getClients().size());
		assertEquals(3, shopManagement.getProducts().size());		
		
		// Read through supplier list and verify entries of supplier by
		// iterating
		// through the list and reading the attributes
		List<Client> clients = shopManagement.getClients();
		
		assertEquals(1, clients.get(0).getId());
		assertEquals("Mueller", clients.get(0).getName());
		assertEquals("Bahnhofplatz 1", clients.get(0).getStreet());
		assertEquals(90489, clients.get(0).getZipcode());
		assertEquals("Nuremberg", clients.get(0).getCity());
		assertEquals("Germany", clients.get(0).getCountry());
		
		System.out.println("ID \tName \tAddress");
		for (int i = 0; i < shopManagement.getClients().size(); i++) {
			System.out.print(shopManagement.getClients().get(i).getId() + "\t");
			System.out.print(shopManagement.getClients().get(i).getName() + "\t");
			System.out.print(shopManagement.getClients().get(i).getAddress() + "\n");
		}

		// Read through product list and verify entries of product by iterating
		// through the list and reading the attributes
		List<Product> products = shopManagement.getProducts();

		assertEquals("Papier", products.get(0).getName());
		assertEquals("DNA4 Blaetter", products.get(0).getDescription());
		assertEquals(23.0, products.get(0).getPrice(), 0);

		assertEquals("Zucker", products.get(1).getName());
		assertEquals("fuer Kaffe", products.get(1).getDescription());
		assertEquals(25.0, products.get(1).getPrice(), 0);

		assertEquals("Saft", products.get(2).getName());
		assertEquals("Durst", products.get(2).getDescription());
		assertEquals(24.0, products.get(2).getPrice(), 0);
		
		System.out.println("Name \tAmount \tDescription");
		for (int i = 0; i < shopManagement.getProducts().size(); i++) {
			System.out.print(shopManagement.getProducts().get(i).getName() + "\t");
			System.out.print(shopManagement.getProducts().get(i).getPrice() + "\t");
			System.out.print(shopManagement.getProducts().get(i).getDescription() + "\n");
		}
	}

	@Test
	public void testSaveSpreadsheet() throws FileNotFoundException {
		SpreadSheetServiceBootStrap bootStrap = new SpreadSheetServiceBootStrap();
		bootStrap.setWorkbookKlass(ShopManagement.class);
		SpreadsheetService spreadService = bootStrap.createSpreadService();

		ShopManagement shopManagement = new ShopManagement();

		Client supplier1 = new Client("Adorsys", "Hauptstrasse 1", 90489, "Nuremberg", "Germany");
		Client supplier2 = new Client("Urframes", "Goethestrasse", 91056, "Erlangen", "Germany");

		Product product1 = new Product("Find the Word", "Android App", 0.99);
		Product product2 = new Product("Dell XS23", "Monitor 25.6 Inch", 349.0);
		//empty row
		Product product3 = new Product(null, null, null); 

		shopManagement.getProducts().add(product1);
		shopManagement.getProducts().add(product2);
		shopManagement.getProducts().add(product3);
		shopManagement.getClients().add(supplier1);
		shopManagement.getClients().add(supplier2);
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
		// Read through supplier list and verify entries of supplier by
		// iterating
		// through the list and reading the attributes
		List<Client> client = shopManagement.getClients();
		supplier1 = client.get(0);
		assertEquals("Adorsys", supplier1.getName());
		assertEquals("Hauptstrasse 1", supplier1.getStreet());
		assertEquals(90489, supplier1.getZipcode());
		assertEquals("Nuremberg", supplier1.getCity());
		
		supplier2 = client.get(1);
		assertEquals("Urframes", supplier2.getName());
		assertEquals("Goethestrasse", supplier2.getStreet());
		assertEquals(91056, supplier2.getZipcode());
		assertEquals("Erlangen", supplier2.getCity());
		
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