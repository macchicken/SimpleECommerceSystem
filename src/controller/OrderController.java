package controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import model.Cart;
import model.CartItem;
import model.Order;
import model.Product;
import model.RestMessage;
import model.SimpleUser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import service.RemoteServiceBridge;
import dao.DaoFactory;
import dao.IOrderDao;

/**
 * Controller of manipulating an existing order
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
@Controller
@RequestMapping("/eco/orders")
@SessionAttributes("morder")
public class OrderController {

	/**
	 * a service of communicating with remote service
	 * @see RemoteServiceBridge
	 */
	private RemoteServiceBridge rb=RemoteServiceBridge.getInstance();
	/**
	 * dao service of order
	 * @see IOrderDao
	 */
	private IOrderDao odao=DaoFactory.getInstance().getOrderDao();


	/**
	 * retrieve orders from current login user
	 * @param model - spring model pass to frontController
	 * @param session - spring http session
	 * @return view page name - view_order
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String loadOrders(Model model,HttpSession session){
		SimpleUser user=(SimpleUser) session.getAttribute("currentUser");
		List<Order> result=odao.getUserOrders(user.getName());
		model.addAttribute("orders",result);
		return "view_order";
	}

	/**
	 * retrieve the details of an order
	 * @param orderId - unique id of an order
	 * @param model - spring model pass to frontController
	 * @return view page name - order_detail
	 */
	@RequestMapping(value="/detail/{orderId}",method = RequestMethod.GET)
	public String getOrder(@PathVariable String orderId,Model model){
		Order order=odao.getOrderDetail(orderId);
		model.addAttribute("morder",order);
		model.addAttribute("cart",order.getCart());
		return "order_detail";
	}

	/**
	 * increase the quantity of an item in the order
	 * @param productId - unique id of a product
	 * @param model - spring model pass to frontController
	 * @param order - a order instance in the spring model
	 * @see Order
	 * @return view page name - cart_partial
	 */
	@RequestMapping(value="/addItem/{productId}",method = RequestMethod.GET)
	public String addItem(@PathVariable String productId,Model model,@ModelAttribute("morder") Order order){
		Cart cart=order.getCart();
		Product p = cart.getItem(productId).getProduct();
		cart.addItem(p);
		model.addAttribute("cart",cart);
		return "cart_partial";
	}

	/**
	 * decrease the quantity of an item in the order
	 * @param productId - unique id of a product
	 * @param model - spring model pass to frontController
	 * @param order - a order instance in the spring model
	 * @see Order
	 * @return view page name - cart_partial
	 */
	@RequestMapping(value="/removeItem/{productId}",method = RequestMethod.GET)
	public String removeItem(@PathVariable String productId,Model model,@ModelAttribute("morder") Order order){
		Cart cart=order.getCart();
		cart.deleteItem(productId);
		model.addAttribute("cart",cart);
		return "cart_partial";
	}

	/**
	 * process the modification of this order
	 * @param model - spring model pass to frontController
	 * @param order - a order instance in the spring model
	 * @param result - validation result object relate to objects with Valid annotation
	 * @param sessionStatus - spring http session
	 * @see Order
	 * @return view page name view page name - order_cartholder if validation is not passed, else messagePage
	 */
	@RequestMapping(value="/complete",method = RequestMethod.GET)
	public String completeOrder(Model model,@Valid @ModelAttribute("morder") Order order,BindingResult result,SessionStatus sessionStatus){
		Cart cart=order.getCart();
		if (result.hasErrors()){
			model.addAttribute("cart",cart);
			return "order_cartholder";
		}
		RestMessage r=rb.calculate(order);
		String status=r.getStatus();
		if ("fail".equals(status)){
			result.rejectValue("city", "error.morder", "sorry,we can not post to "+order.getCity());
			model.addAttribute("cart",cart);
			return "order_cartholder";
		}
		Map<String,Object> mresult=r.getResult();
		double shippingCost=(double) mresult.get("total");
		order.setShippingCost(shippingCost);
		order.setTotalCost(shippingCost+cart.getTotal());
		String success=transform(cart);
		success+="<p>order "+order.getId()+" is processing to "+order.getCity()+", shipping cost is $"+shippingCost+" final cost is $"+order.getTotalCost()+"</p>";
		model.addAttribute("success", success);
		odao.modifyOrder(order);
		sessionStatus.setComplete();
		return "messagePage";
	}

	private String transform(Cart cart){
		String result="";
		for (CartItem ci:cart.getItems()){
			if (ci.getQuantity()>0) {
				result += "<p>" + ci.getProduct().getTitle() + " quantity: "+ ci.getQuantity()+"</p>";
			}
		}
		return result;
	}

}
