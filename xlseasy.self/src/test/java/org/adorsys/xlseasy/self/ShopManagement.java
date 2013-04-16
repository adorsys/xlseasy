package org.adorsys.xlseasy.self;

import java.util.ArrayList;
import java.util.List;

/**
 * The class ShopManagement
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
