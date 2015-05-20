package model;

import java.io.Serializable;

public class SimpleUser implements Serializable {

	private String name;
	private String role;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
