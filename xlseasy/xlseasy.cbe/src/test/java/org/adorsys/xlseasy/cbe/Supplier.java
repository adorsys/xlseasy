package org.adorsys.xlseasy.cbe;

// TODO: Auto-generated Javadoc
/**
 * The Class Supplier.
 */
public class Supplier {

	/** The name. */
	private String name;
	
	/** The strasse. */
	private String strasse;
	
	/** The postleit zahl. */
	private String postleitZahl;
	
	/** The stadt. */
	private String stadt;
	
	/** The vip. */
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
