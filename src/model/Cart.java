package model;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cart{
	
	private Map<String,CartItem> carts;

	public Cart(){
		carts = new HashMap<String,CartItem>();
	}

	public void addItem(Product p){
		String productId = p.getProductId();
		CartItem ci = carts.get(productId);
		if (ci == null){
			carts.put(productId, new CartItem(p,1)); // add the new product			
		}else{ // increase the quantity
			ci.increaseQuantity();
		}
	}

	public void addItem(Product p,int quantity){
		carts.put(p.getProductId(), new CartItem(p,quantity));
	}

	public void deleteItem(String productId){
		CartItem ci = carts.get(productId);
		if (ci!=null){
			if (ci.getQuantity()>0){
				ci.decreaseQuantity();
			}
		}
	}

	
	public Collection<CartItem> getItems() {
		return carts.values();
	}

	public CartItem getItem(String productId) {
		return carts.get(productId);
	}
	
	public double getTotal(){
		double total = 0.0;
		for (CartItem ci: carts.values()){
			total += ci.getQuantity() * ci.getProduct().getPrice();
		}
		return total;
	}

	public int getTotalItem(){
		int totalI=0;
		for (CartItem ci: carts.values()){
			totalI += ci.getQuantity();
		}
		return totalI;
	}

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
