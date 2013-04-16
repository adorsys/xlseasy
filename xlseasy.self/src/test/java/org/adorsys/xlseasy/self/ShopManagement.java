package org.adorsys.xlseasy.self;

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
 * @author mariusguede
 */
public class ShopManagement {
	
	/**
	 * The products
	 */
	private List<Product> products = new ArrayList<Product>();
	
	/**
	 * The clients
	 */
	private List<Client> clients = new ArrayList<Client>();
	
	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * Sets the clients.
	 *
	 * @param client the new clients
	 */
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	/**
	 * Gets the clients.
	 *
	 * @return the clients
	 */
	public List<Client> getClients() {
		return clients;
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
