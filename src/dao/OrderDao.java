package dao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Cart;
import model.CartItem;
import model.Order;
import model.PageModel;
import model.Product;

/**
 * Mysql implementation of IOrderDao service
 * @author Barry
 *
 */
public class OrderDao implements IOrderDao{

	private ConnectionPool pool=ConnectionPool.getInstance();

	private String getOrderByIdSQL = "SELECT id,address1,address2,city,shippingCost,totalCost,state,user from simple_order where id = ?",
			getOrderDetailByIdSQL = "SELECT t1.id as itemId,t1.order_id,t1.quantity,t1.product,t.id as id,t.address1,t.address2,t.city,t.shippingCost,t.totalCost,t.state,t.user,t.created_time FROM simple_order_item t1 right join simple_order t on(t1.order_id=t.id) where t.id=? and t.autoId=?",
			getOrdersByUserSQL= "SELECT SQL_CALC_FOUND_ROWS autoId pageindex,id,address1,address2,city,shippingCost,totalCost,state,user,created_time from simple_order where user=? and autoId<301 order by id asc limit ?,10",
			getAllOrderSQL = "SELECT SQL_CALC_FOUND_ROWS autoId pageindex,id,address1,address2,city,shippingCost,totalCost,state,user,created_time from simple_order where autoId<301 order by user asc limit ?,10",
			deleteOrderSQL = "DELETE from simple_order where id = ?" ,
			insertOrderSQL = "INSERT into simple_order(id,address1,address2,city,shippingCost,totalCost,state,user) values (?,?,?,?,?,?,?,?)" ,
			insertOrderItemSQL = "INSERT into simple_order_item(id,order_id,quantity,product) values (?,?,?,?)" ,
			updateOrderSQL = "UPDATE simple_order set address1=?,address2=?,city=?,shippingCost=?,totalCost=?,updated_time=sysdate() where id=? and autoId=?",
			updateOrderItemSql="INSERT into simple_order_item (id,order_id,quantity,product) values(?,?,?,?) ON DUPLICATE KEY UPDATE quantity=?",
			updateOrderState="UPDATE simple_order set state=?,updated_time=sysdate() where id=? and autoId=?",
			getRoundRows="SELECT FOUND_ROWS() totalr";
	
	public OrderDao(){
		super();
	}
	
	@Override
	public PageModel<Order> getUserOrders(String user,String pageNumber){
		Connection conn=null;PreparedStatement ps=null,ps2=null;ResultSet rs=null,rs2=null;List<Order> orders=null;
		int pageN=Integer.parseInt(pageNumber);int total=0;
		try {
			int limit=0+(pageN-1)*10;
			conn = pool.getConnection();
			ps=conn.prepareStatement(getOrdersByUserSQL);
			ps.setString(1,user);
			ps.setInt(2, limit);
			rs=ps.executeQuery();
			orders=new ArrayList<Order>();
			while(rs.next()){
				Order t=new Order(rs.getString("id"),rs.getString("address1"),rs.getString("address2"),rs.getString("city"));
				t.setShippingCost(rs.getDouble("shippingCost"));
				t.setTotalCost(rs.getDouble("totalCost"));
				t.setState(rs.getString("state"));
				t.setUser(rs.getString("user"));
				t.setCreatedTime(rs.getTimestamp("created_time"));
				t.setPageIndex(rs.getInt("pageindex"));
				orders.add(t);
			}
			ps2=conn.prepareStatement(getRoundRows);
			rs2=ps2.executeQuery();
			rs2.next();
			total=rs2.getInt("totalr");
			int tempt=total;
			total=total/10;
			if (tempt%10>0){total=total+1;}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (rs!=null) {
					rs.close();
				}
				if (ps!=null) {
					ps.close();
				}
				if (rs2!=null) {
					rs2.close();
				}
				if (ps2!=null) {
					ps2.close();
				}
				if (conn!=null) {
					pool.freeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new PageModel<Order>(total,pageN,orders);
	}
	
	@Override
	public Order getOrderDetail(String orderId,String pageIndex){
		Connection conn=null;PreparedStatement ps=null;ResultSet rs=null;Order order=null;
		ObjectInputStream oips = null;
		try {
			int pageI=Integer.parseInt(pageIndex);
			conn = pool.getConnection();
			ps=conn.prepareStatement(getOrderDetailByIdSQL);
			ps.setString(1,orderId);
			ps.setInt(2, pageI);
			rs=ps.executeQuery();
			Cart cart=new Cart();
			rs.next();
			oips = new ObjectInputStream(rs.getBinaryStream("product"));
			Product p=(Product) oips.readObject();
			cart.addItem(p, rs.getInt("quantity"));
			order=new Order(rs.getString("id"),rs.getString("address1"),rs.getString("address2"),rs.getString("city"));
			order.setShippingCost(rs.getDouble("shippingCost"));
			order.setTotalCost(rs.getDouble("totalCost"));
			order.setState(rs.getString("state"));
			order.setUser(rs.getString("user"));
			order.setCreatedTime(rs.getTimestamp("created_time"));
			while(rs.next()){
				oips = new ObjectInputStream(rs.getBinaryStream("product"));
				Product temp=(Product) oips.readObject();
				cart.addItem(temp, rs.getInt("quantity"));
			}
			order.setCart(cart);
			order.setPageIndex(pageI);
		} catch (SQLException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				if (oips!=null){
					oips.close();
				}
				if (rs!=null) {
					rs.close();
				}
				if (ps!=null) {
					ps.close();
				}
				if (conn!=null) {
					pool.freeConnection(conn);
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		return order;
	}

	@Override
	public void addNewOrder(Order order){
		Connection conn=null;PreparedStatement ps=null;PreparedStatement ps2=null;
		try {
			conn = pool.getConnection();
			ps=conn.prepareStatement(insertOrderSQL);
			conn.setAutoCommit(false);
			String orderId=order.getId();
			ps.setString(1, orderId);
			ps.setString(2, order.getAddress1());
			ps.setString(3, order.getAddress2());
			ps.setString(4, order.getCity());
			ps.setDouble(5, order.getShippingCost());
			ps.setDouble(6, order.getTotalCost());
			ps.setString(7, "processing");
			ps.setString(8, order.getUser());
			Collection<CartItem> cart=order.getCart().getItems();
			ps2=conn.prepareStatement(insertOrderItemSQL);
			for (CartItem ci:cart){
				ps2.setString(1, ci.getProduct().getProductId());
				ps2.setString(2,orderId);
				ps2.setInt(3,ci.getQuantity());
				ps2.setObject(4, ci.getProduct());
				ps2.addBatch();
			}
			ps.execute();
			ps2.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (ps!=null) {
					ps.close();
				}
				if (conn!=null) {
					pool.freeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void modifyOrder(Order order){
		Connection conn=null;PreparedStatement ps=null;PreparedStatement ps2=null;
		try {
			conn = pool.getConnection();
			ps=conn.prepareStatement(updateOrderSQL);
			conn.setAutoCommit(false);
			String orderId=order.getId();
			int pageIndex=order.getPageIndex();
			ps.setString(1, order.getAddress1());
			ps.setString(2, order.getAddress2());
			ps.setString(3, order.getCity());
			ps.setDouble(4, order.getShippingCost());
			ps.setDouble(5, order.getTotalCost());
			ps.setString(6, orderId);
			ps.setInt(7,pageIndex);
			Collection<CartItem> cart=order.getCart().getItems();
			ps2=conn.prepareStatement(updateOrderItemSql);
			for (CartItem ci:cart){
				ps2.setString(1, ci.getProduct().getProductId());
				ps2.setString(2,orderId);
				ps2.setInt(3,ci.getQuantity());
				ps2.setObject(4, ci.getProduct());
				ps2.setInt(5, ci.getQuantity());
				ps2.addBatch();
			}
			ps.execute();
			ps2.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (ps!=null) {
					ps.close();
				}
				if (conn!=null) {
					pool.freeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public PageModel<Order> getAllOrders(String pageNumber){
		List<Order> result=null;Connection conn=null;PreparedStatement ps=null,ps2=null;ResultSet rs=null,rs2=null;
		int pageN=Integer.parseInt(pageNumber);int total=0;
		try {
			int limit=0+(pageN-1)*10;
			conn = pool.getConnection();
			ps=conn.prepareStatement(getAllOrderSQL);
			ps.setInt(1, limit);
			rs=ps.executeQuery();
			result=new ArrayList<Order>();
			while(rs.next()){
				Order temp=new Order(rs.getString("id"),rs.getString("address1"),rs.getString("address2"),rs.getString("city"));
				temp.setShippingCost(rs.getDouble("shippingCost"));
				temp.setTotalCost(rs.getDouble("totalCost"));
				temp.setState(rs.getString("state"));
				temp.setUser(rs.getString("user"));
				temp.setCreatedTime(rs.getTimestamp("created_time"));
				temp.setPageIndex(rs.getInt("pageindex"));
				result.add(temp);
			}
			ps2=conn.prepareStatement(getRoundRows);
			rs2=ps2.executeQuery();
			rs2.next();
			total=rs2.getInt("totalr");
			int tempt=total;
			total=total/10;
			if (tempt%10>0){total=total+1;}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (rs!=null){
					rs.close();
				}
				if (ps!=null) {
					ps.close();
				}
				if (rs2!=null){
					rs2.close();
				}
				if (ps2!=null) {
					ps2.close();
				}
				if (conn!=null) {
					pool.freeConnection(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new PageModel<Order>(total,pageN,result);
	}

	@Override
	public boolean updateOrderState(String orderId,String pageIndex,String state){
		Connection conn=null;PreparedStatement ps=null;
		try {
			conn = pool.getConnection();
			ps=conn.prepareStatement(updateOrderState);
			ps.setString(1,state);
			ps.setString(2,orderId);
			ps.setInt(3,Integer.parseInt(pageIndex));
			int count=ps.executeUpdate();
			if (count>0){return true;}
		} catch (SQLException e) {
			e.printStackTrace();
		}try {
			if (ps!=null) {
				ps.close();
			}
			if (conn!=null) {
				pool.freeConnection(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
