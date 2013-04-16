package org.adorsys.xlseasy.self;

/**
 * The Class Client.
 *
 * @author mariusguede
 */
public class Client {
	
	/** Counts and saves the number of Clients */
	private static int IDCounter = 0;

	/** Client's ID. */
	private int id;
	
	/** Client's name. */
	private String name;

	/** Client's street. */
	private String street;

	/** Client's zipcode. */
	private int zipcode;

	/** Client's city. */
	private String city;

	/** Client's country. */
	private String country;

	/**
	 * Instantiates a new client.
	 */
	public Client() {
		super();
	}

	/**
	 * Instantiates a new client.
	 *
	 * @param id the id
	 * @param name the name
	 * @param street the street
	 * @param zipcode the zipcode
	 * @param city the city
	 * @param country the country
	 * @param vip the vip
	 */
	public Client(String name, String street, int zipcode,
			String city, String country) {
		super();
		this.id = ++IDCounter;
		this.name = name;
		this.street = street;
		this.zipcode = zipcode;
		this.city = city;
		this.country = country;
	}

	/**
	 * Gets the client's ID.
	 *
	 * @return the client's ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the client's ID.
	 *
	 * @param id the new client's ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the client's name.
	 *
	 * @return the client's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the client's name.
	 *
	 * @param name the new client's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the client's street.
	 *
	 * @return the client's street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the client's street.
	 *
	 * @param street the new client's street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the client's zipcode.
	 *
	 * @return the client's zipcode
	 */
	public int getZipcode() {
		return zipcode;
	}

	/**
	 * Sets the client's zipcode.
	 *
	 * @param zipcode the new client's zipcode
	 */
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * Gets the client's city.
	 *
	 * @return the client's city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the client's city.
	 *
	 * @param city the new client's city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the client's country.
	 *
	 * @return the client's country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the client's country.
	 *
	 * @param country the new client's country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Gets the client's complete address.
	 *
	 * @return the client's address
	 */
	public String getAddress() {
		return street + ", " + zipcode + " " + city + " (" + country + ")";
	}
}