package org.adorsys.saturn.spreadsheet.test;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.xlseasy.annotation.HorizontalRecordSheet;
import org.adorsys.xlseasy.annotation.Workbook;

/**
 * @author Sandro Sonntag <info@adorsys.de>
 */
@Workbook(sheetOrder={"products", "suppliers"})
public class ShopManagement {
	
	/** The products. */
	@HorizontalRecordSheet(recordClass=Product.class)
	private List<Product> products = new ArrayList<Product>();
	
	/** The suppliers. */
	@HorizontalRecordSheet(recordClass=Supplier.class)
	private List<Supplier> suppliers = new ArrayList<Supplier>();
	
	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * Sets the suppliers.
	 *
	 * @param suppliers the new suppliers
	 */
	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	/**
	 * Gets the suppliers.
	 *
	 * @return the suppliers
	 */
	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	/**
	 * Sets the products.
	 *
	 * @param products the new products
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
}
