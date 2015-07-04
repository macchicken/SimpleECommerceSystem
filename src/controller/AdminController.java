package controller;


import model.Order;
import model.PageModel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	 * @return view page name - view_order
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String loadOrders(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") String pageNumber,
			Model model) {
		PageModel<Order> result = odao.getAllOrders(pageNumber);
		model.addAttribute("orders", result);
		return "view_order";
	}

	/**
	 * update the state of an order to shipped
	 * @param orderId - unique id of an order
	 * @param model - spring model pass to frontController
	 * @return view page name - messagePage
	 */
	@RequestMapping(value="/update/{orderId}",method = RequestMethod.GET)
	public String updateState(@PathVariable String orderId,@RequestParam(value = "pageIndex",required = true) String pageIndex,Model model){
		boolean success=odao.updateOrderState(orderId,pageIndex,"shipped");
		if (success) {
			model.addAttribute("messageContent", "order " + orderId+ " has been shipped");
		}else{
			model.addAttribute("messageContent", "order " + orderId+ " failed to update");
		}
		return "messagePage";
	}

}
