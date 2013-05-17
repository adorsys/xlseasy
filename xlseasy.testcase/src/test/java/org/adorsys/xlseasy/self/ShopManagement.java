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
 * In any case, the Processing Interface allow the specifications
 * 
 * @author Sandro Sonntag
 * @author Francis Pouatcha
 * @author Marius Guede
 */
public class ShopManagement {
	
	/** Sheet product. */
	private List<Product> products = new ArrayList<Product>();
	
	/** Sheet suppliers. */
	private List<Client> clients = new ArrayList<Client>();
	
	/** Sheet users. */
	private List<User> users = new ArrayList<User>();
	
	/**
	 * Gets the sheet product.
	 *
	 * @return the sheet product
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * Sets the sheet suppliers.
	 *
	 * @param clients the new sheet suppliers
	 */
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	/**
	 * Gets the sheet suppliers.
	 *
	 * @return the sheet suppliers
	 */
	public List<Client> getClients() {
		return clients;
	}

	/**
	 * Sets the sheet product.
	 *
	 * @param products the new sheet product
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	/**
	 * Gets the sheet users.
	 *
	 * @return the sheet users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Sets the sheet users.
	 *
	 * @param users the new sheet users
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
}