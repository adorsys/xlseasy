package org.adorsys.xlseasy.cbe;

import java.util.Date;

/**
 * The Class Product.
 */
public class Product {

	/** Product's name. */
    private String name;

	/** Product's description. */
    private String description;

	/** Product's amount. */
    private Double amount;

	/** Product's topSeller status. */
    private Boolean topSeller;

	/** Product's validation date. */
    private Date validFrom;

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
     * @param amount the amount
     * @param topSeller the top seller
     * @param validFrom the valid from
     */
    public Product(String name, String description, Double amount,
                   Boolean topSeller, Date validFrom) {
        super();
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.topSeller = topSeller;
        this.validFrom = validFrom;
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
     * Gets the product's amount.
     *
     * @return the product's amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Sets the product's amount.
     *
     * @param amount the new product's amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Gets the product's topSeller status.
     *
     * @return the product's topSeller status
     */
    public Boolean getTopSeller() {
        return topSeller;
    }

    /**
     * Sets the product's topSeller status.
     *
     * @param topSeller the new product's topSeller status
     */
    public void setTopSeller(Boolean topSeller) {
        this.topSeller = topSeller;
    }

    /**
     * Gets the product's validation date.
     *
     * @return the product's validation date
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the product's validation date.
     *
     * @param validFrom the new product's validation date
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }
}