package org.adorsys.xlseasy.cbe;

/**
 * The Class Supplier.
 */
public class Supplier {

	/** Supplier name. */
	private String name;
	
	/** Supplier street. */
	private String strasse;
	
	/** Supplier zipcode. */
	private String postleitZahl;
	
	/** Supplier city. */
	private String stadt;
	
	/** Supplier VIP status. */
	private boolean vip;

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
	 * @param strasse the street
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
	 * Sets the supplier VIP status.
	 *
	 * @param vip the new VIP status
	 */
	public void setVip(boolean vip) {
		this.vip = vip;
	}
}
