package model;

import java.io.Serializable;


public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	
	private String description,imageUrl;
	private double price;
	private String productId; //default id will be modified by the storage
	private String originImg;
	
	public Product(){		
	}
	
	public Product(String title, String description, double price){
		this.title = title;
		this.description = description;
		this.price = price;
	}
	
	public Product(String productId, String title, String description, double price){
		this.productId = productId;
		this.title = title;
		this.description = description;
		this.price = price;
	}
	public Product(String productId, String title, String description, String imgUrl, double price){
		this.productId = productId;
		this.title = title;
		this.description = description;
		this.imageUrl = imgUrl;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	

	public String getOriginImg() {
		return originImg;
	}

	public void setOriginImg(String originImg) {
		this.originImg = originImg;
	}

	public String toString(){
		return "Product " + productId + ": " + title + "@" + price;
	}

}
