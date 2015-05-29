package model;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * represent a shopping cart
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
public class Cart{
	
	/**
	 * the data structure of storing the shopping items
	 */
	private Map<String,CartItem> carts;

	/**
	 * initialize the carts
	 */
	public Cart(){
		carts = new HashMap<String,CartItem>();
	}

	/**
	 * add a product to the shopping cart
	 * @param p
	 */
	public void addItem(Product p){
		String productId = p.getProductId();
		CartItem ci = carts.get(productId);
		if (ci == null){
			carts.put(productId, new CartItem(p,1)); // add the new product			
		}else{ // increase the quantity
			ci.increaseQuantity();
		}
	}

	/**
	 * add a certain amount of product to the shopping cart
	 * 
	 * @param p
	 * @param quantity
	 */
	public void addItem(Product p,int quantity){
		carts.put(p.getProductId(), new CartItem(p,quantity));
	}

	/**
	 * decrease the quantity of product in the cart
	 * 
	 * @param productId
	 */
	public void deleteItem(String productId){
		CartItem ci = carts.get(productId);
		if (ci!=null){
			if (ci.getQuantity()>0){
				ci.decreaseQuantity();
			}
		}
	}

	/**
	 * get all items in the cart
	 * 
	 * @return	Collection<CartItem>
	 */
	public Collection<CartItem> getItems() {
		return carts.values();
	}

	/**
	 * retrieve a item of the cart with that product id
	 * 
	 * @param productId
	 * @return CartItem
	 * @see CartItem
	 */
	public CartItem getItem(String productId) {
		return carts.get(productId);
	}
	
	/**
	 * Return total of price of items in the cart
	 * 
	 * @return double
	 */
	public double getTotal(){
		double total = 0.0;
		for (CartItem ci: carts.values()){
			total += ci.getQuantity() * ci.getProduct().getPrice();
		}
		return total;
	}

	/**
	 * Return total quantity of items in the cart
	 * 
	 * @return int
	 */
	public int getTotalItem(){
		int totalI=0;
		for (CartItem ci: carts.values()){
			totalI += ci.getQuantity();
		}
		return totalI;
	}

	/**
	 * remove any item with zero quantity
	 */
	public void cleanCart(){
		Iterator<CartItem> it=carts.values().iterator();
		while(it.hasNext()){
			CartItem ci=it.next();
			if (ci.getQuantity()==0){
				it.remove();
			}
		}
	}

}
