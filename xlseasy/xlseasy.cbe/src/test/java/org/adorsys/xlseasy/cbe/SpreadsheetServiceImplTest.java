package org.adorsys.xlseasy.cbe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.adorsys.xlseasy.annotation.SpreadsheetService;
import org.adorsys.xlseasy.boot.SpreadSheetServiceBootStrap;
import org.apache.commons.lang.BooleanUtils;
import org.junit.Test;

/**
 * The class SpreadsheetServiceImplTest.
 */
public class SpreadsheetServiceImplTest {

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
		InputStream managementStream = SpreadsheetServiceImplTest.class
				.getResourceAsStream(managementFile);

		// Creates a new ShopManagement and save the loaded spreadsheet into.
		ShopManagement shopManagement = spreadService.loadSpreadsheet(
				managementStream, ShopManagement.class);

		/**
		 * Verifies if all spreadsheets have been read and objects created.
		 * 
		 * This verification is for the case the number of expected objects from
		 * each sheet is known. The program breaks if this number isn't the same
		 * as the number of objects contained in the sheet.
		 * 
		 * In this case, we're expected 3 objects from the sheets clients and
		 * products and 5 objects from users.
		 */
		assertEquals(3, shopManagement.getSuppliers().size());
		assertEquals(3, shopManagement.getProducts().size());

		// Reads through supplier list and verify entries of supplier by
		// iterating through the list and reading the attributes
		List<Supplier> suppliers = shopManagement.getSuppliers();
		assertEquals("Consor", suppliers.get(0).getName());
		assertEquals("flater", suppliers.get(0).getStreet());
		assertEquals("90559", suppliers.get(0).getZipcode());
		assertEquals("Nuremberg", suppliers.get(0).getCity());
		assertTrue(suppliers.get(0).isVip());

		assertEquals("Camsuco", suppliers.get(1).getName());
		assertEquals("Dorotheen", suppliers.get(1).getStreet());
		assertEquals("12345", suppliers.get(1).getZipcode());
		assertEquals("Essen", suppliers.get(1).getCity());
		assertFalse(suppliers.get(1).isVip());

		assertEquals("Hap", suppliers.get(2).getName());
		assertEquals("Am Hartweg", suppliers.get(2).getStreet());
		assertEquals("08146", suppliers.get(2).getZipcode());
		assertEquals("Dortmund", suppliers.get(2).getCity());
		assertTrue(suppliers.get(2).isVip());

		// Reads through product list and verify entries of product by iterating
		// through the list and reading the attributes
		List<Product> products = shopManagement.getProducts();

		assertEquals("Papier", products.get(0).getName());
		assertEquals("DNA4 Blaetter", products.get(0).getDescription());
		assertEquals(23.0, products.get(0).getAmount(), 0);

		assertEquals("Zucker", products.get(1).getName());
		assertEquals("fuer Kaffe", products.get(1).getDescription());
		assertEquals(25.0, products.get(1).getAmount(), 0);

		assertEquals("Saft", products.get(2).getName());
		assertEquals("Durst", products.get(2).getDescription());
		assertEquals(24.0, products.get(2).getAmount(), 0);
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

		// Creates 2 suppliers for the test.
		Supplier supplier1 = new Supplier("Amzon", "Friedriech", "44524",
				"Hagen", true);
		Supplier supplier2 = new Supplier("Bookstore", "Mallinkrot", "44415",
				"Dortmund", true);

		// Creates 3 products for the test.
		Product product1 = new Product("Core jsf", "jsf Book", 32.0, true,
				new Date());
		Product product2 = new Product("Hibernate", "hibernate Book", 25.0,
				true, new Date());
		// empty row
		Product product3 = new Product(null, null, null, null, null);

		// Adds created objects (suppliers and products) to corresponding
		// List<Object>
		shopManagement.getProducts().add(product1);
		shopManagement.getProducts().add(product2);
		shopManagement.getProducts().add(product3);
		shopManagement.getSuppliers().add(supplier1);
		shopManagement.getSuppliers().add(supplier2);

		// The file to create.
		String workbookFile = "target/testmana.xls";

		// Creates a file stream to write to the file.
		OutputStream outputStream = new FileOutputStream(workbookFile);

		// Saves the spreadsheet.
		spreadService.saveSpreadsheet(ShopManagement.class, shopManagement,
				outputStream);

		/**
		 * The spreadsheet has been created and saved into the file testmana.xls
		 * in the directory target. Now we want to check the content of the
		 * created file. It's enough (for us) to check the first object. of each
		 * sheet
		 */

		// Creates a file stream to open the file.
		InputStream managementStream = new FileInputStream(workbookFile);

		// Loads spreadsheet.
		shopManagement = spreadService.loadSpreadsheet(managementStream,
				ShopManagement.class);

		// Verifies if all spreadsheets have been read and checks the number of
		// created objects. In our case, 2 suppliers and 3 products.
		assertTrue(shopManagement.getSuppliers().size() == 2);
		assertTrue(shopManagement.getProducts().size() == 3);

		// Reads through supplier list and verify entries of supplier by
		// iterating through the list and reading the attributes
		List<Supplier> suppliers = shopManagement.getSuppliers();
		checkSupplier(supplier1, suppliers.get(0));
		checkSupplier(supplier2, suppliers.get(1));

		// Reads through product list and verify entries of product by iterating
		// through the list and reading the attributes
		List<Product> products = shopManagement.getProducts();
		checkProduct(product1, products.get(0));
		checkProduct(product2, products.get(1));
		checkProduct(product3, products.get(2));
	}

	/**
	 * Test a supplier.
	 * 
	 * @param expected
	 *            the expected object
	 * @param is
	 *            the real object
	 */
	private void checkSupplier(Supplier expected, Supplier is) {
		assertEquals(expected.getName(), is.getName());
		assertEquals(expected.getStreet(), is.getStreet());
		assertEquals(expected.getZipcode(), is.getZipcode());
		assertEquals(expected.getCity(), is.getCity());
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
		assertEquals(expected.getAmount() != null ? expected.getAmount()
				: Double.valueOf(0.0), is.getAmount());
		assertEquals(BooleanUtils.isTrue(expected.getTopSeller()),
				is.getTopSeller());
		assertEquals(expected.getValidFrom(), is.getValidFrom());
	}
}