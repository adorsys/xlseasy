package org.adorsys.saturn.spreadsheet.test;

import org.adorsys.xlseasy.annotation.FreezePane;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;

// TODO: Auto-generated Javadoc
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
     * @param strasse the strasse
     * @param postleitZahl the postleit zahl
     * @param stadt the stadt
     * @param vip the vip
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

    /** The strasse. */
    @SheetColumn
    private String strasse;


    /** The postleit zahl. */
    @SheetColumn(columnName = "PLZ",
            headerStyle = @SheetCellStyle(fontStyleBold = true)
    )
    private String postleitZahl;

    /** The stadt. */
    @SheetColumn(columnName = "Stadt",
            headerStyle = @SheetCellStyle(fontStyleBold = true)
    )
    private String stadt;

    /** The vip. */
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
     * Gets the strasse.
     *
     * @return the strasse
     */
    public String getStrasse() {
        return strasse;
    }

    /**
     * Sets the strasse.
     *
     * @param strasse the new strasse
     */
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    /**
     * Gets the postleit zahl.
     *
     * @return the postleit zahl
     */
    public String getPostleitZahl() {
        return postleitZahl;
    }

    /**
     * Sets the postleit zahl.
     *
     * @param postleitZahl the new postleit zahl
     */
    public void setPostleitZahl(String postleitZahl) {
        this.postleitZahl = postleitZahl;
    }

    /**
     * Gets the stadt.
     *
     * @return the stadt
     */
    public String getStadt() {
        return stadt;
    }

    /**
     * Sets the stadt.
     *
     * @param stadt the new stadt
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
     * Sets the vip.
     *
     * @param vip the new vip
     */
    public void setVip(boolean vip) {
        this.vip = vip;
    }

}
