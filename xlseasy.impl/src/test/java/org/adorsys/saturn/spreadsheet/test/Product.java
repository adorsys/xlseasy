package org.adorsys.saturn.spreadsheet.test;

import java.util.Date;

import org.adorsys.xlseasy.annotation.CellAlign;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;

@Sheet(autoSizeColumns = true,
        columnOrder = {"name", "amount", "description", "topSeller", "validFrom"})
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

    @SheetColumn(columnName = "Name", headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true))
    private String name;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true))
    private String description;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true))
    private Double amount;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true))
    private Boolean topSeller;

    @SheetColumn(headerStyle = @SheetCellStyle(align = CellAlign.CENTER, fontStyleBold = true))
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
