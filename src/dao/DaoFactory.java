package dao;

/**
 * a singleton factory of data accesssing services
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
public class DaoFactory {

	/**
	 * order dao service interface
	 */
	private IOrderDao odao;
	
	private static class DaoFactoryHolder{
		private static final DaoFactory INSTANCE = new DaoFactory();
	}

	/**
	 * prive constructor to be used in this singleton
	 */
	private DaoFactory(){
		odao=new OrderDao();
	}
	

	public static DaoFactory getInstance(){
		return DaoFactoryHolder.INSTANCE;
	}

	/**
	 * Return a service interface of order dao service
	 * @return IOrderDao - an orderdao interface
	 */
	public IOrderDao getOrderDao() {
		return odao;
	}


}
