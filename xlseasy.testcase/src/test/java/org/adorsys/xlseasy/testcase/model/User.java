package org.adorsys.xlseasy.testcase.model;

/**
 * The class User.
 *
 * @author Marius Guede
 */
public class User {
	
	/** Counts the number of instantiated users. */
	private static Integer userCounter = 0;

	/** The user's ID. */
	private Integer id;
	
	/** The user's pseudo. */
	private String pseudo;
	
	/** The user's name. */
	private String name;
	
	/** The user's email. */
	private String email;
	
	/** The user's password. */
	private String password;
	
	/**
	 * Instantiates a new user.
	 */
	public User() {
		super();
	}
	
	/**
	 * Instantiates a new user.
	 *
	 * @param pseudo the pseudo
	 * @param name the name
	 * @param email the email
	 * @param password the password
	 */
	public User(String pseudo, String name, String email, String password) {
		super();
		this.id = ++userCounter;
		this.pseudo = pseudo;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	/**
	 * Gets the user's ID.
	 *
	 * @return the user's ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the user's ID.
	 *
	 * @param userID the new user's ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the user's pseudo.
	 *
	 * @return the user's pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Sets the user's pseudo.
	 *
	 * @param pseudo the new user's pseudo
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * Gets the user's name.
	 *
	 * @return the user's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the user's name.
	 *
	 * @param name the new user's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the user's email.
	 *
	 * @return the user's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's email.
	 *
	 * @param email the new user's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the user's password.
	 *
	 * @return the user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's password.
	 *
	 * @param password the new user's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Encodes the user's password.
	 *
	 * @return the encoded password
	 */
	public String encodedPassword() {
		String tmp = new String();
		for (int i = 0; i < password.length(); i++) {
			tmp += "*";
		}
		return tmp;
	}
}