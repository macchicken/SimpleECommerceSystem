package dao;

import java.util.List;

import model.Order;

/**
 * interface of order dao service
 * @author Barry
 *
 */
public interface IOrderDao {

	/**
	 * Return a list of order from an user
	 * @param user
	 * @return
	 */
	public List<Order> getUserOrders(String user);
	/**
	 * Return details of an order
	 * @param orderId	id of an order
	 * @return
	 */
	public Order getOrderDetail(String orderId);
	/**
	 * save an order
	 * @param order
	 * @see Order
	 */
	public void addNewOrder(Order order);
	/**
	 * modify an order
	 * @param order
	 * @see Order
	 */
	public void modifyOrder(Order order);
	/**
	 * retrieve all orders
	 * @return
	 * @see Order
	 */
	public List<Order> getAllOrders();
	/**
	 * update state of an order
	 * @param orderId
	 * @param state
	 * @return
	 */
	public boolean updateOrderState(String orderId,String state);

}
