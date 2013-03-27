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
 * The Class SpreadsheetServiceImplTest.
 */
public class SpreadsheetServiceImplTest {

	/**
	 * Test load spreadsheet.
	 */
	@Test
	public void testLoadSpreadsheet() {

		SpreadSheetServiceBootStrap bootStrap = new SpreadSheetServiceBootStrap();
		bootStrap.setWorkbookKlass(ShopManagement.class);
		SpreadsheetService spreadService = bootStrap.createSpreadService();

		String managementFile = "management.xls";

		InputStream managementStream = SpreadsheetServiceImplTest.class
				.getResourceAsStream(managementFile);

		ShopManagement shopManagement = spreadService.loadSpreadsheet(
				managementStream, ShopManagement.class);
		// Verify if all spreadsheets have been read and objects created
		assertEquals(3, shopManagement.getProducts().size());

		assertEquals(3, shopManagement.getSuppliers().size());
		// Read through supplier list and verify entries of supplier by
		// iterating
		// through the list and reading the attributes
		List<Supplier> suppliers = shopManagement.getSuppliers();
		assertEquals("Consor", suppliers.get(0).getName());
		assertEquals("flater", suppliers.get(0).getStrasse());
		assertEquals("90559", suppliers.get(0).getPostleitZahl());
		assertEquals("Nuremberg", suppliers.get(0).getStadt());
		assertTrue(suppliers.get(0).isVip());

		assertEquals("Camsuco", suppliers.get(1).getName());
		assertEquals("Dorotheen", suppliers.get(1).getStrasse());
		assertEquals("12345", suppliers.get(1).getPostleitZahl());
		assertEquals("Essen", suppliers.get(1).getStadt());
		assertFalse(suppliers.get(1).isVip());

		assertEquals("Hap", suppliers.get(2).getName());
		assertEquals("Am Hartweg", suppliers.get(2).getStrasse());
		assertEquals("08146", suppliers.get(2).getPostleitZahl());
		assertEquals("Dortmund", suppliers.get(2).getStadt());
		assertTrue(suppliers.get(2).isVip());

		// Read through product list and verify entries of product by iterating
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
	 * Test save spreadsheet.
	 *
	 * @throws FileNotFoundException the file not found exception
	 */
	@Test
	public void testSaveSpreadsheet() throws FileNotFoundException {
		SpreadSheetServiceBootStrap bootStrap = new SpreadSheetServiceBootStrap();
		bootStrap.setWorkbookKlass(ShopManagement.class);
		SpreadsheetService spreadService = bootStrap.createSpreadService();

		ShopManagement shopManagement = new ShopManagement();

		Supplier supplier1 = new Supplier("Amzon", "Friedriech", "44524", "Hagen", true);
		Supplier supplier2 = new Supplier("Bookstore", "Mallinkrot", "44415", "Dortmund", true);

		Product product1 = new Product("Core jsf", "jsf Book", 32.0, true, new Date());
		Product product2 = new Product("Hibernate", "hibernate Book", 25.0, true, new Date());
		//empty row
		Product product3 = new Product(null, null, null, null, null); 

		shopManagement.getProducts().add(product1);
		shopManagement.getProducts().add(product2);
		shopManagement.getProducts().add(product3);
		shopManagement.getSuppliers().add(supplier1);
		shopManagement.getSuppliers().add(supplier2);
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

		assertTrue(shopManagement.getSuppliers().size() == 2);
		// Read through supplier list and verify entries of supplier by
		// iterating
		// through the list and reading the attributes
		List<Supplier> suppliers = shopManagement.getSuppliers();
		supplier1 = suppliers.get(0);
		assertEquals("Amzon", supplier1.getName());
		assertEquals("Friedriech", supplier1.getStrasse());
		assertEquals("44524", supplier1.getPostleitZahl());
		assertEquals("Hagen", supplier1.getStadt());
		
		supplier2 = suppliers.get(1);
		assertEquals("Bookstore", supplier2.getName());
		assertEquals("Mallinkrot", supplier2.getStrasse());
		assertEquals("44415", supplier2.getPostleitZahl());
		assertEquals("Dortmund", supplier2.getStadt());
		
		// Read through product list and verify entries of product by iterating
		// through the list and reading the attributes
		List<Product> products = shopManagement.getProducts();
		checkProduct(product1, products.get(0));
		checkProduct(product2, products.get(1));
		checkProduct(product3, products.get(2));
	}

	/**
	 * Check product.
	 *
	 * @param expected the expected
	 * @param is the is
	 */
	private void checkProduct(Product expected, Product is) {
		assertEquals(expected.getName(), is.getName());
		assertEquals(expected.getDescription(), is.getDescription());
		assertEquals(expected.getAmount() != null ? expected.getAmount() : Double.valueOf(0.0), is.getAmount());
		assertEquals(BooleanUtils.isTrue(expected.getTopSeller()), is.getTopSeller());
		assertEquals(expected.getValidFrom(), is.getValidFrom());
	}

}
