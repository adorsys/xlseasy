/**
 * The class Product.
 *
 * @author Marius Guede <mariusguede@urframes.net>
 */
public class Product {
	
	/** Product's name. */
	private String name;
	
	/** Product's description. */
	private String desc;

	/** Product's price. */
	private Double price;

	/**
	 * Instantiates a new product.
	 */
	public Product() {
		super();
	}
	
	/**
	 * Instantiates a new product.
	 *
	 * @param name the name
	 * @param desc the description
	 * @param price the price
	 */
	public Product(String name, String desc, Double price) {
		super();
		this.name = name;
		this.desc = desc;
		this.price = price;		
	}
	
	/**
	 * Gets the product's name.
	 *
	 * @return the product's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the product's name.
	 *
	 * @param name the new product's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the product's description.
	 *
	 * @return the product's description
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Sets the product's description.
	 *
	 * @param desc the new product's description
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * Gets the product's price.
	 *
	 * @return the product's price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the product's price.
	 *
	 * @param price the new product's price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
}