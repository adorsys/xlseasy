package org.adorsys.saturn.spreadsheet.test;

import java.util.Date;

import org.adorsys.xlseasy.annotation.CellAlign;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;

/**
 * The Class Product.
 */
@Sheet(autoSizeColumns = true,
        columnOrder = {"name", "amount", "description", "topSeller", "validFrom"})
public class Product {

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

    /** The name. */
    @SheetColumn(columnName = "Name", headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true))
    private String name;

    /** The description. */
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true))
    private String description;

    /** The amount. */
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true))
    private Double amount;

    /** The top seller. */
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true))
    private Boolean topSeller;

    /** The valid from. */
    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true))
    private Date validFrom;

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
     * Gets the top seller.
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
