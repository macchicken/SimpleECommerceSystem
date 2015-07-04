package dao;


import model.Order;
import model.PageModel;

/**
 * interface of order dao service
 * @author Barry
 *
 */
public interface IOrderDao {

	/**
	 * Return a list of order from an user
	 * @param user - user name
	 * @param pageNumber - desired page number
	 * @return list of orders
	 * @see Order
	 * @see PageModel
	 */
	public PageModel<Order> getUserOrders(String user,String pageNumber);
	/**
	 * Return details of an order
	 * @param orderId - id of an order
	 * @param pageIndex - id of an order with in a page
	 * @return an intance of Order
	 */
	public Order getOrderDetail(String orderId,String pageIndex);
	/**
	 * save information of an order
	 * @param order - an instance of Order
	 * @see Order
	 */
	public void addNewOrder(Order order);
	/**
	 * modify information of an order
	 * @param order - an instance of Order
	 * @see Order
	 */
	public void modifyOrder(Order order);
	/**
	 * retrieve all orders by pages
	 * @param pageNumber - desired page number 
	 * @return list of orders
	 * @see Order
	 * @see PageModel
	 */
	public PageModel<Order> getAllOrders(String pageNumber);
	/**
	 * update state of an order
	 * @param orderId - id of an order
	 * @param pageIndex - id of an order with in a page
	 * @param state - state of an order
	 * @return boolean - success flag
	 */
	public boolean updateOrderState(String orderId,String pageIndex,String state);

}
