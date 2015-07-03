package controller;

import java.util.List;

import model.Order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.DaoFactory;
import dao.IOrderDao;

/**
 * Controller of the operations of an administrator
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
@Controller
@RequestMapping("/eco/admin")
public class AdminController {

	/**
	 * dao service of order
	 * @see IOrderDao
	 */
	private IOrderDao odao=DaoFactory.getInstance().getOrderDao();
	
	/**
	 * load all orders from data source
	 * @param model - spring model pass to frontController
	 * @return view_order - view page name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String loadOrders(Model model){
		List<Order> result=odao.getAllOrders();
		model.addAttribute("orders", result);
		return "view_order";
	}

	/**
	 * update the state of an order to shipped
	 * @param orderId - unique id of an order
	 * @param model - spring model pass to frontController
	 * @return messagePage - view page name
	 */
	@RequestMapping(value="/update/{orderId}",method = RequestMethod.GET)
	public String updateState(@PathVariable String orderId,Model model){
		boolean success=odao.updateOrderState(orderId, "shipped");
		if (success) {
			model.addAttribute("success", "order " + orderId+ " has been shipped");
		}else{
			model.addAttribute("success", "order " + orderId+ " failed to update");
		}
		return "messagePage";
	}

}
