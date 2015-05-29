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

@Controller
@RequestMapping("/eco/admin")
public class AdminController {

	private IOrderDao odao=DaoFactory.getInstance().getOrderDao();
	
	@RequestMapping(method = RequestMethod.GET)
	public String loadCart(Model model){
		List<Order> result=odao.getAllOrders();
		model.addAttribute("orders", result);
		return "view_order";
	}

	@RequestMapping(value="/update/{orderId}",method = RequestMethod.GET)
	public String updateState(@PathVariable String orderId,Model model){
		boolean success=odao.updateOrderState(orderId, "shipped");
		model.addAttribute("success", "order "+orderId+" has been shipped");
		return "messagePage";
	}

}
