package org.adorsys.xlseasy.cbe;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the mapping of an excel file. Each collection field 
 * is an excel-sheet. Non collection fields are ignored.
 * 
 * Getters and setters are required. Sheet can be lists or sets.
 * 
 * Sheet are processed in the order of the fields. If this class extends
 * another one, sheets of this class are process first, then sheet from
 * other classes are processed.
 * 
 * In any case, the Processing Interface allow the speci
 * 
 * @author Sandro Sonntag
 * @author Francis Pouatcha
 */
public class ShopManagement {
	
	/**
	 * Sheet product
	 */
	private List<Product> products = new ArrayList<Product>();
	
	/**
	 * Sheet suppliers
	 */
	private List<Supplier> suppliers = new ArrayList<Supplier>();
	
	public List<Product> getProducts() {
		return products;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
