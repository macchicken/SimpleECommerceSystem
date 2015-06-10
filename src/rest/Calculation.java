package rest;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import model.RestMessage;

import commons.CalculateSetting;
/**
 * Representing the calculation of e-commerce system in Rest web Services
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
@Path("/calculate")
public class Calculation {

	/**
	 * like an instance variable definition
	 */
	@Context UriInfo uriInfo;
	
	/**
	 * setting of calculaiton
	 */
	private CalculateSetting setting=CalculateSetting.getInstance();

	/**
	 * Returns the shipping fee of certain amount of items deliver to a city
	 * 
	 * @param city 		destination city to deliver
	 * @param items		amount of items to deliver
	 * @param id		order id, 0 if it is a new order
	 * @return			RestMessage containing result of calculation
	 * @see				RestMessage
	 * @since			29/05/2015
	 */
	@GET
	@Path("{city}-{items}-{id}")
	@Produces(MediaType.TEXT_XML)
	public RestMessage calculateFee(@PathParam("city") String city,@PathParam("items") int items,@PathParam("id") String id) {
		if("0".equals(id)){
			id=java.util.UUID.randomUUID().toString().replaceAll("-", "");
		}
		System.out.println("receiving order "+id+" with "+items+" items shipped to "+city);
		RestMessage mess=new RestMessage();
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("newId", id);
		String cost=setting.getCost(city);
		if (cost==null){
			mess.setStatus("fail");
			System.out.println("invalid address "+city);
		}else{
			String[] t=cost.split(",");
			double perItemC=Double.valueOf(t[1]);
			double deliverC=Double.valueOf(t[0]);
			result.put("perItem", perItemC);
			result.put("deliver", deliverC);
			double totalF=perItemC*items+deliverC;
			result.put("total", totalF);
			System.out.println("the shipping fee for order "+id+" is: $"+totalF);
		}
		mess.setResult(result);
		return mess;
	}


}
