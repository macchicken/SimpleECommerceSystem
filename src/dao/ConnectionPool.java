package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * a singleton pool holding the DataSource
 * 
 * @author Barry
 * @version 1.0
 * @since 27/06/2015
 */
public class ConnectionPool {

	 private static DataSource ds = null;

	 private ConnectionPool(){
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			// Look up our data source
			ds = (DataSource) envCtx.lookup("jdbc/ShoppingCart");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class ConnectionPoolHolder{
			private static final ConnectionPool INSTANCE=new ConnectionPool();
	}
	 
	public static ConnectionPool getInstance(){
		return ConnectionPoolHolder.INSTANCE;
	}

	/**
	 * retrieve a connection in the datasource pool
	 * 
	 * @return Connection - return a connection in the datasource pool
	 */
	public Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
	}

	/**
	 * close a connection
	 * 
	 * @param conn - a database connection
	 */
	public void freeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}


}
