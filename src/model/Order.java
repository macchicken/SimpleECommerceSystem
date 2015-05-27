package model;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import commons.SpecialCharacter;

@XmlRootElement
public class Order{

	private String id="0";
	
	@NotEmpty(message="You must supply a adrress")
	@SpecialCharacter
	private String address1;
	
	@NotEmpty(message="You must supply a adrress")
	@SpecialCharacter
	private String address2;
	
	@NotEmpty(message="You must identify a destination city")
	@SpecialCharacter
	private String city;

	private Cart cart;
	
	private double shippingCost;
	private double totalCost;
	private String user;
	private String state;
	private Timestamp createdTime;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Order(String address1, String address2, String city) {
		super();
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
	}

	public Order() {
		super();
	}

	
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

	public void setId(String id) {
		this.id = id;
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
