package model;

import java.io.Serializable;

/**
 * A model of System user
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
public class SimpleUser implements Serializable {

	/**
	 * name of this user
	 */
	private String name;
	/**
	 * role this user has
	 */
	private String role;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Return a user with a name and role
	 * @param name
	 * @param role
	 */
	public SimpleUser(String name, String role) {
		super();
		this.name = name;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	@Override
	public String toString() {
		return "SimpleUser [name=" + name + ", role=" + role + "]";
	}

}
