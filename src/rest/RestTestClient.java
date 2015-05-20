package rest;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import model.Cart;
import model.Order;
import model.Product;
import model.RestMessage;



public class RestTestClient {
	
	private static Client client = ClientBuilder.newClient();

	public static void main(String argv[]){
		WebTarget root = client.target("http://localhost:10080/SimpleECommerceSystem/rest/calculate/"); 
		Order order=new Order("321321","31231321","sydney");
		Cart cart=new Cart();
		cart.addItem(new Product("1","322131","232132131",1));
		cart.addItem(new Product("2","322131","232132131",1));
		cart.addItem(new Product("3","322131","232132131",1));
		order.setId("0001");
		order.setCart(cart);
		RestMessage r=root.path(order.getCity()+"-"+cart.getTotalItem()+"-"+order.getId()).request().get(Response.class).readEntity(RestMessage.class);
		Map<String,Object> result=r.getResult();
		System.out.println(result.get("total"));
		double totalItemCost=cart.getTotal();
		order.setShippingCost((double) result.get("total"));
		System.out.println(totalItemCost+order.getShippingCost());
		System.out.println("3212321321");
	}
}
