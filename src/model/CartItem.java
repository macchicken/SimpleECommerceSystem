package model;

/**
 * represent a shopping item in the cart
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
public class CartItem{
	
		/**
		 * represent a product
		 */
		private Product product;
		/**
		 * quantity of this product
		 */
		private int quantity;
		
		public CartItem(){}
		
		/**
		 * initialize a cartitem with a quantity 
		 * 
		 * @param p product
		 * @param q quanity
		 * 
		 * @see Product
		 */
		public CartItem(Product p, int q){
			this.product = p;
			this.quantity = q;
		}
		
		/**
		 * 
		 * @return Product
		 * @see Product
		 */
		public Product getProduct() {
			return product;
		}
		/**
		 * 
		 * @param product
		 * @see Product
		 */
		public void setProduct(Product product) {
			this.product = product;
		}
		/**
		 * Return quantity 
		 * 
		 * @return int
		 */
		public int getQuantity() {
			return quantity;
		}
		/**
		 * Set quantity
		 * 
		 * @param quantity
		 */
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		/**
		 * Increase the quantity by 1
		 */
		public void increaseQuantity(){
			quantity++;
		}
		
		/**
		 * Decrease the quantity by 1
		 */
		public void decreaseQuantity(){
			quantity--;
		}


}
