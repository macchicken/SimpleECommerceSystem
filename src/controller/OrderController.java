package controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import model.Cart;
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

import commons.RemoteServiceBridge;

import dao.DaoFactory;
import dao.OrderDao;

@Controller
@RequestMapping("/eco/orders")
@SessionAttributes("morder")
public class OrderController {

	private RemoteServiceBridge rb=RemoteServiceBridge.getInstance();
	private OrderDao odao=DaoFactory.getInstance().getOrderDao();


	@RequestMapping(method = RequestMethod.GET)
	public String loadOrders(Model model,HttpSession session){
		SimpleUser user=(SimpleUser) session.getAttribute("currentUser");
		List<Order> result=odao.getUserOrders(user.getName());
		model.addAttribute("orders",result);
		return "view_order";
	}

	@RequestMapping(value="/detail/{orderId}",method = RequestMethod.GET)
	public String getOrder(@PathVariable String orderId,Model model){
		Order order=odao.getOrderDetail(orderId);
		model.addAttribute("morder",order);
		model.addAttribute("cart",order.getCart());
		return "order_detail";
	}

	@RequestMapping(value="/addItem/{productId}",method = RequestMethod.GET)
	public String addItem(@PathVariable String productId,Model model,@ModelAttribute("morder") Order order){
		Cart cart=order.getCart();
		Product p = cart.getItem(productId).getProduct();
		cart.addItem(p);
		model.addAttribute("cart",cart);
		return "cart_partial";
	}

	@RequestMapping(value="/removeItem/{productId}",method = RequestMethod.GET)
	public String removeItem(@PathVariable String productId,Model model,@ModelAttribute("morder") Order order){
		Cart cart=order.getCart();
		cart.deleteItem(productId);
		model.addAttribute("cart",cart);
		return "cart_partial";
	}

	@RequestMapping(value="/complete",method = RequestMethod.POST)
	public String completeOrder(Model model,@Valid @ModelAttribute("morder") Order order,BindingResult result,SessionStatus sessionStatus){
		Cart cart=order.getCart();
		if (result.hasErrors()){
			model.addAttribute("cart",cart);
			return "order_detail";
		}
		RestMessage r=rb.calculate(order);
		String status=r.getStatus();
		if ("fail".equals(status)){
			result.rejectValue("city", "error.morder", "sorry,we can not post to "+order.getCity());
			model.addAttribute("cart",cart);
			return "order_detail";
		}
		Map<String,Object> mresult=r.getResult();
		double shippingCost=(double) mresult.get("total");
		order.setShippingCost(shippingCost);
		order.setTotalCost(shippingCost+cart.getTotal());
		model.addAttribute("success", " order "+order.getId()+" is processing to "+order.getCity());
		odao.modifyOrder(order);
		sessionStatus.setComplete();
		return "messagePage";
	}


}
