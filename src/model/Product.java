package model;

import java.io.Serializable;

/**
 * Serializable Product
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
public class Product implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * title of this product
	 */
	private String title;
	
	/**
	 * description and image url of this product
	 */
	private String description,imageUrl;
	/**
	 * unit price of this product
	 */
	private double price;
	/**
	 * the id of this product
	 */
	private String productId;
	/**
	 * url of original image of this product
	 */
	private String originImg;
	
	public Product(){}
	
	/**
	 * Return a product with title,description and a unit price
	 * @param title
	 * @param description
	 * @param price
	 */
	public Product(String title, String description, double price){
		this.title = title;
		this.description = description;
		this.price = price;
	}
	
	/**
	 * Return a product with title,description,a unit price and an id
	 * @param productId
	 * @param title
	 * @param description
	 * @param price
	 */
	public Product(String productId, String title, String description, double price){
		this.productId = productId;
		this.title = title;
		this.description = description;
		this.price = price;
	}
	/**
	 * Return a product with title,description,a unit price,an id and an url of product image
	 * @param productId
	 * @param title
	 * @param description
	 * @param imgUrl
	 * @param price
	 */
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
