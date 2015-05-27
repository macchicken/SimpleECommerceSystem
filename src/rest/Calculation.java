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
 * Representing the calculate in Rest web Services 
 */
@Path("/calculate")
public class Calculation {

	@Context UriInfo uriInfo; //like an instance variable definition
	
	private CalculateSetting setting=CalculateSetting.getInstance();

	@GET
	@Path("{city}-{items}-{id}")
	@Produces(MediaType.TEXT_XML)
	public RestMessage calculateFee(@PathParam("city") String city,@PathParam("items") int items,@PathParam("id") String id) {
		if("0".equals(id)){
			id=java.util.UUID.randomUUID().toString().replaceAll("-", "");
		}
		System.out.println("receiving order "+id+" with "+items+" shipped to "+city);
		RestMessage mess=new RestMessage();
		String cost=setting.getCost(city);
		if (cost==null){
			mess.setStatus("fail");
			System.out.println("invalid address "+city);
		}else{
			String[] t=cost.split(",");
			double perItemC=Double.valueOf(t[1]);
			double deliverC=Double.valueOf(t[0]);
			Map<String,Object> result=new HashMap<String,Object>();
			result.put("perItem", perItemC);
			result.put("deliver", deliverC);
			double totalF=perItemC*items+deliverC;
			result.put("total", totalF);
			result.put("newId", id);
			mess.setResult(result);
			System.out.print("the shipping fee for order "+id+" is: $"+totalF);
		}
		return mess;
	}


}
