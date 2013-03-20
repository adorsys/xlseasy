package org.adorsys.saturn.spreadsheet.test;

import org.adorsys.xlseasy.annotation.FreezePane;
import org.adorsys.xlseasy.annotation.Sheet;
import org.adorsys.xlseasy.annotation.SheetCellStyle;
import org.adorsys.xlseasy.annotation.SheetColumn;

@Sheet(columnOrder = {"name", "strasse", "postleitZahl", "stadt", "vip"},
        freezePane = @FreezePane(colSplit = 5, rowSplit = 1, leftmostColumn = 5, topRow = 1)
)
public class Supplier {

    public Supplier() {
        super();
    }

    /**
     * @param name
     * @param strasse
     * @param postleitZahl
     * @param stadt
     * @param vip
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

    @SheetColumn(columnName = "Name",
            headerStyle = @SheetCellStyle(fontStyleBold = true)
    )
    private String name;

    @SheetColumn
    private String strasse;


    @SheetColumn(columnName = "PLZ",
            headerStyle = @SheetCellStyle(fontStyleBold = true)
    )
    private String postleitZahl;

    @SheetColumn(columnName = "Stadt",
            headerStyle = @SheetCellStyle(fontStyleBold = true)
    )
    private String stadt;

    @SheetColumn(columnName = "VIP")
    private boolean vip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPostleitZahl() {
        return postleitZahl;
    }

    public void setPostleitZahl(String postleitZahl) {
        this.postleitZahl = postleitZahl;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

}
