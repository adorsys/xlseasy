package adorsys.poi.example;

import java.util.List;
import java.util.Iterator;

/** The Class ShopManagement. */
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
	 */
	public ShopManagement(Client client, Product product, Integer numberOfProducts) {
		super();
		this.client = client;
		this.product = product;
		this.numberOfProducts = numberOfProducts;
	}
	
	/**
	 * Gets the price for this number of products.
	 */
	public Double getPriceForNumberOfProducts() {
		return product.getPrice() * numberOfProducts;
	}

	/**
	 * Gets the product.
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Sets the product.
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Gets the client.
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Sets the client.
	 */
	public void setClient(Client client) {
		this.client = client;
	}
	
	/**
	 * Returns an array of type ShopManagement from an ArrayList List<ShopManagement>.
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