package org.adorsys.xlseasy.cbe;

import java.util.Date;

/**
 * The Class Product.
 */
public class Product {

    /** The name. */
    private String name;
    
    /** The description. */
    private String description;
    
    /** The amount. */
    private Double amount;
    
    /** The top seller. */
    private Boolean topSeller;
    
    /** The valid from. */
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
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     *
     * @param amount the new amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Checks if product is a topseller.
     *
     * @return the top seller
     */
    public Boolean getTopSeller() {
        return topSeller;
    }

    /**
     * Sets the top seller.
     *
     * @param topSeller the new top seller
     */
    public void setTopSeller(Boolean topSeller) {
        this.topSeller = topSeller;
    }

    /**
     * Gets the valid from.
     *
     * @return the valid from
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * Sets the valid from.
     *
     * @param validFrom the new valid from
     */
    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

}
