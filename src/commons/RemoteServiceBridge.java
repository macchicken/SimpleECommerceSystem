package commons;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import model.Cart;
import model.Order;
import model.RestMessage;


public class RemoteServiceBridge {

	private static Client client;
	
	private static class RemoteServiceBridgeHolder{
		private static final RemoteServiceBridge INSTANCE=new RemoteServiceBridge();
	}
	
	private RemoteServiceBridge(){client = ClientBuilder.newClient();}
	
	public static RemoteServiceBridge getInstance(){
		return RemoteServiceBridgeHolder.INSTANCE;
	}
	
	public RestMessage calculate(Order order){
		WebTarget root = client.target("http://localhost:10080/SimpleECommerceSystem/rest/calculate/");
		Cart cart=order.getCart();
		RestMessage r=root.path(order.getCity()+"-"+cart.getTotalItem()+"-"+order.getId()).request().get(Response.class).readEntity(RestMessage.class);
		return r;
	}

}
