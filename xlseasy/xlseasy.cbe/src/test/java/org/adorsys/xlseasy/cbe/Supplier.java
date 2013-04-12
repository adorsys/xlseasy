package org.adorsys.xlseasy.cbe;


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

    private String name;

    private String strasse;

    private String postleitZahl;

    private String stadt;

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
