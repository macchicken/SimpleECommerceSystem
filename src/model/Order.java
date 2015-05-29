package model;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import commons.SpecialCharacter;

/**
 * Represent an Order
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
@XmlRootElement
public class Order{

	/**
	 * id of an order
	 */
	private String id="0";
	
	/**
	 * deliver address of this order
	 */
	@NotEmpty(message="You must supply a adrress")
	@SpecialCharacter
	private String address1;
	
	/**
	 * deliver address of this order
	 */
	@NotEmpty(message="You must supply a adrress")
	@SpecialCharacter
	private String address2;
	
	/**
	 * deliver city of this order
	 */
	@NotEmpty(message="You must identify a destination city")
	@SpecialCharacter
	private String city;

	/**
	 * bought item in the order
	 * @see Cart
	 */
	private Cart cart;
	
	/**
	 * deliver cost
	 */
	private double shippingCost;
	
	/**
	 * total cost including
	 */
	private double totalCost;
	/**
	 * owner of this order
	 */
	private String user;
	/**
	 * state of this order
	 */
	private String state;
	/**
	 * placed time of this order
	 */
	private Timestamp createdTime;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Return an order with an address
	 * 
	 * @param address1
	 * @param address2
	 * @param city
	 */
	public Order(String address1, String address2, String city) {
		super();
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
	}

	public Order() {
		super();
	}

	/**
	 * Return an order with an id and an address
	 * 
	 * @param id
	 * @param address1
	 * @param address2
	 * @param city
	 */
	public Order(String id,String address1, String address2, String city) {
		super();
		this.id=id;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
	}

	public String getId() {
		return id;
	}

	/**
	 * Set an id to this order only if id is zero
	 * @param id
	 */
	public void setId(String id) {
		if ("0".equals(this.id)) {
			this.id = id;
		}
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTotalCost(double totalCost){
		this.totalCost=totalCost;
	}
	
	public double getTotalCost(){
		return totalCost;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

}
