package org.adorsys.saturn.spreadsheet.test;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.xlseasy.annotation.HorizontalRecordSheet;
import org.adorsys.xlseasy.annotation.Workbook;

/**
 * TODO set Javadoc for Class
 * @version $Id: $
 * @author sso
 */
@Workbook(sheetOrder={"products", "suppliers"})
public class ShopManagement {
	@HorizontalRecordSheet(recordClass=Product.class)
	private List<Product> products = new ArrayList<Product>();
	
	@HorizontalRecordSheet(recordClass=Supplier.class)
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
