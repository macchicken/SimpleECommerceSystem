package dao;

public class DaoFactory {

	private OrderDao odao;
	
	private static class DaoFactoryHolder{
		private static final DaoFactory INSTANCE = new DaoFactory();
	}

	private DaoFactory(){
		odao=new OrderDao();
	}
	

	public static DaoFactory getInstance(){
		return DaoFactoryHolder.INSTANCE;
	}

	public OrderDao getOrderDao() {
		return odao;
	}


}
