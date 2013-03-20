package org.adorsys.xlseasy.cbe;

import java.util.Date;

public class Product {

    public Product() {
        super();
    }

    /**
     * @param name
     * @param description
     * @param amount
     * @param topSeller
     * @param validFrom
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

    private String name;

    private String description;

    private Double amount;

    private Boolean topSeller;

    private Date validFrom;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getTopSeller() {
        return topSeller;
    }

    public void setTopSeller(Boolean topSeller) {
        this.topSeller = topSeller;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

}
