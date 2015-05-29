package dao;

import java.util.List;

import model.Order;

public interface IOrderDao {

	public List<Order> getUserOrders(String user);
	public Order getOrderDetail(String orderId);
	public void addNewOrder(Order order);
	public void modifyOrder(Order order);
	public List<Order> getAllOrders();
	public boolean updateOrderState(String orderId,String state);

}
