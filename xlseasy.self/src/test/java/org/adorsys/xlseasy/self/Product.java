package org.adorsys.xlseasy.self;

/**
 * The class Product.
 *
 * @author Marius Guede
 */
public class Product {

	/** The product's name. */
    private String name;

	/** The product's description. */
    private String description;

	/** The product's price. */
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
     * @param description the description
     * @param price the price
     */
    public Product(String name, String description, Double price) {
        super();
        this.name = name;
        this.description = description;
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
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product's description.
     *
     * @param description the new product's description
     */
    public void setDescription(String description) {
        this.description = description;
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
