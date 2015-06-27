package service;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import model.Cart;
import model.Order;
import model.RestMessage;

/**
 * a singleton service bridge of communicating with remote service
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
public class RemoteServiceBridge {

	/**
	 * local client
	 */
	private Client client;
	/**
	 * remote address of the service
	 */
	private String remote;
	
	private static class RemoteServiceBridgeHolder{
		private static final RemoteServiceBridge INSTANCE=new RemoteServiceBridge();
	}

	/**
	 * private constructor to be used in this singleton
	 */
	private RemoteServiceBridge(){
		client = ClientBuilder.newClient();
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream("/systemconfig.properties"));
			remote=properties.getProperty("rest.remote");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("exception on reading systemconfig.properties, using local service");
			remote="http://localhost:10080/SimpleECommerceSystem";
		}
	}
	
	public static RemoteServiceBridge getInstance(){
		return RemoteServiceBridgeHolder.INSTANCE;
	}
	
	/**
	 * call the remote calculating service
	 * @param order - an instance of Order
	 * @return RestMessage - response message from rest service
	 * @see Order
	 */
	public RestMessage calculate(Order order){
		WebTarget root = client.target(remote+"/rest/calculate/");
		Cart cart=order.getCart();
		RestMessage r=root.path(order.getCity()+"-"+cart.getTotalItem()+"-"+order.getId()).request().get(Response.class).readEntity(RestMessage.class);
		return r;
	}

}
