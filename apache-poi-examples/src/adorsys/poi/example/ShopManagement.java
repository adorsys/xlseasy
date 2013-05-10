package adorsys.poi.example;

import java.util.List;
import java.util.Iterator;

/**
 * The Class ShopManagement.
 *
 * @author Marius Guede
 */
public class ShopManagement {
	
	/** The product. */
	private Product product;
	
	/** The client. */
	private Client client;
	
	/** The number of products. */
	private Integer numberOfProducts;
	
	/**
	 * Instantiates a new shop management.
	 */
	public ShopManagement() {
		super();
	}
	
	/**
	 * Instantiates a new ShopManagement object.
	 *
	 * @param client the client
	 * @param product the product
	 * @param numberOfProducts the number of products
	 */
	public ShopManagement(Client client, Product product, Integer numberOfProducts) {
		super();
		this.client = client;
		this.product = product;
		this.numberOfProducts = numberOfProducts;
	}
	
	/**
	 * Gets the price for this number of products.
	 *
	 * @return the price for number of products
	 */
	public Double getPriceForNumberOfProducts() {
		return product.getPrice() * numberOfProducts;
	}

	/**
	 * Gets the product.
	 *
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Sets the product.
	 *
	 * @param product the new product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Sets the client.
	 *
	 * @param client the new client
	 */
	public void setClient(Client client) {
		this.client = client;
	}
	
	/**
	 * Returns an array ShopManagement[] from an ArrayList List<ShopManagement>.
	 *
	 * @param manager the manager ArrayList
	 * @return the array list
	 */
	public static ShopManagement[] ArrayList(List<ShopManagement> managerList) {
		ShopManagement[] arrayFromList = new ShopManagement[managerList.size()];
		Iterator<ShopManagement> managerListIterator = managerList.iterator();
		int index = 0;
		while (managerListIterator.hasNext()) {
			arrayFromList[index] = (ShopManagement) managerListIterator.next();
			index++;
		}
		return arrayFromList;
	}	
}
