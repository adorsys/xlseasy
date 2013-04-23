package org.adorsys.xlseasy.cbe;

/**
 * The Class Supplier.
 */
public class Supplier {

	/** Supplier's name. */
	private String name;

	/** Supplier's street. */
	private String street;

	/** Supplier's zipcode. */
	private String zipcode;

	/** Supplier's city. */
	private String city;

	/** Supplier's VIP status. */
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
	 * @param name
	 *            the name
	 * @param street
	 *            the street
	 * @param zipcode
	 *            the zipcode
	 * @param city
	 *            the city
	 * @param vip
	 *            the vip
	 */
	public Supplier(String name, String street, String zipcode, String city,
			boolean vip) {
		super();
		this.name = name;
		this.street = street;
		this.zipcode = zipcode;
		this.city = city;
		this.vip = vip;
	}

	/**
	 * Gets the supplier's name.
	 * 
	 * @return the supplier's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the supplier's name.
	 * 
	 * @param name
	 *            the new supplier's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the supplier's street.
	 * 
	 * @return the supplier's street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the supplier's street.
	 * 
	 * @param street
	 *            the new supplier's street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the supplier's zipcode.
	 * 
	 * @return the supplier's zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * Sets the supplier's zipcode.
	 * 
	 * @param zipcode
	 *            the new supplier's zipcode
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * Gets the supplier's city.
	 * 
	 * @return the supplier's city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the supplier's city.
	 * 
	 * @param city
	 *            the new supplier's city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Checks if is supplier's VIP status.
	 * 
	 * @return the supplier's VIP status
	 */
	public boolean isVip() {
		return vip;
	}

	/**
	 * Sets the supplier's VIP status.
	 * 
	 * @param vip
	 *            the new supplier's VIP status
	 */
	public void setVip(boolean vip) {
		this.vip = vip;
	}
}