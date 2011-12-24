package org.adorsys.xlseasy.cbe;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO set Javadoc for Class
 * @version $Id: $
 * @author sso
 */
public class ShopManagement {
	private List<Product> products = new ArrayList<Product>();
	
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
