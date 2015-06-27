package service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * an local test client of generating hash code
 * 
 * @author Barry
 * @version 1.0
 * @since 27/06/2015
 */
public class HashCode {

	/**
	 * generating hash code for an password using BCryption
	 * 
	 * @param password - a predefined password
	 * @return String - hashcode of the password
	 */
	public String getHashPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

}
