package org.adorsys.saturn.spreadsheet.test;

import org.adorsys.xlseasy.annotation.FreezePane;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;

/**
 * The Class Supplier.
 */
@Sheet(columnOrder = {"name", "strasse", "postleitZahl", "stadt", "vip"},
        freezePane = @FreezePane(colSplit = 5, rowSplit = 1, leftmostColumn = 5, topRow = 1)
)
public class Supplier {

    /**
     * Instantiates a new supplier.
     */
    public Supplier() {
        super();
    }

    /**
     * Instantiates a new supplier.
     *
     * @param name the name
     * @param strasse the city
     * @param postleitZahl the zipcode
     * @param stadt the city
     * @param vip the VIP status
     */
    public Supplier(String name, String strasse, String postleitZahl,
                    String stadt, boolean vip) {
        super();
        this.name = name;
        this.strasse = strasse;
        this.postleitZahl = postleitZahl;
        this.stadt = stadt;
        this.vip = vip;
    }

    /** The name. */
    @SheetColumn(columnName = "Name",
            headerStyle = @SheetCellStyle(fontStyleBold = true)
    )
    private String name;

    /** The street. */
    @SheetColumn
    private String strasse;


    /** The zipcode. */
    @SheetColumn(columnName = "PLZ",
            headerStyle = @SheetCellStyle(fontStyleBold = true)
    )
    private String postleitZahl;

    /** The city. */
    @SheetColumn(columnName = "Stadt",
            headerStyle = @SheetCellStyle(fontStyleBold = true)
    )
    private String stadt;

    /** The VIP Status. */
    @SheetColumn(columnName = "VIP")
    private boolean vip;

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
     * Gets the street.
     *
     * @return the street
     */
    public String getStrasse() {
        return strasse;
    }

    /**
     * Sets the street.
     *
     * @param strasse the new street
     */
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    /**
     * Gets the zipcode.
     *
     * @return the zipcode
     */
    public String getPostleitZahl() {
        return postleitZahl;
    }

    /**
     * Sets the zipcode.
     *
     * @param postleitZahl the new zipcode
     */
    public void setPostleitZahl(String postleitZahl) {
        this.postleitZahl = postleitZahl;
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getStadt() {
        return stadt;
    }

    /**
     * Sets the city.
     *
     * @param stadt the new city
     */
    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    /**
     * Checks if is vip.
     *
     * @return true, if is vip
     */
    public boolean isVip() {
        return vip;
    }

    /**
     * Sets the VIP Status.
     *
     * @param vip the new VIP Status
     */
    public void setVip(boolean vip) {
        this.vip = vip;
    }

}
