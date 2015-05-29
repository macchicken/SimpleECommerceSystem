package controller;

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

import service.RemoteServiceBridge;
import dao.DaoFactory;
import dao.IOrderDao;

/**
 * Controller of manipulating a shopping cart
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
@Controller
@RequestMapping("/eco/carts")
@SessionAttributes({"cart","order"})
public class CartAjaxController {

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
	 * initialize a cart while it is null
	 * @see Cart
	 * @return
	 */
	@ModelAttribute("cart")
	public Cart createCart(){
		return new Cart();
	}

	/**
	 * initialize a order while it is null
	 * @see Order
	 * @return
	 */
	@ModelAttribute("order")
	public Order createOrder(){
		return new Order();
	}
	
	/**
	 * load a partical page of displaying the cart
	 * @param model
	 * @param cart
	 * @see Cart
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String loadCart(Model model, @ModelAttribute("cart") Cart cart){
		return "cart_partial";
	}

	/**
	 * Add a product to the shopping cart
	 * @param productId
	 * @param model
	 * @param cart
	 * @param sesison
	 * @see Cart
	 * @return
	 */
	@RequestMapping("/add/{productId}")
	public String add(@PathVariable String productId, Model model, @ModelAttribute("cart") Cart cart,HttpSession sesison){
		Map<String,Product> sproducts=(Map<String, Product>) sesison.getAttribute("sproducts");
		Product p = sproducts.get(productId);
		cart.addItem(p);
		return "cart_partial";
	}

	/**
	 * decrease the quantity of the product in the cart
	 * @param productId
	 * @param model
	 * @param cart
	 * @param sesison
	 * @see Cart
	 * @return
	 */
	@RequestMapping("/remove/{productId}")
	public String remove(@PathVariable String productId, Model model,@ModelAttribute("cart") Cart cart,HttpSession sesison){
		if (cart.getItems().size()>0){
			if (cart.getItem(productId)!=null){
				cart.deleteItem(productId);
			}
		}
		return "cart_partial";
	}
	
	@RequestMapping("/checkout")
	public String checkout(Model model,@ModelAttribute("cart") Cart cart,@ModelAttribute("order") Order order){
		return "checkout";
	}

	/**
	 * Discard this order before submit to checkout
	 * @param model
	 * @param cart
	 * @param order
	 * @param sessionStatus
	 * @see Cart
	 * @see Order
	 * @return
	 */
	@RequestMapping("/discard")
	public String disCard(Model model,@ModelAttribute("cart") Cart cart,@ModelAttribute("order") Order order,SessionStatus sessionStatus){
		sessionStatus.setComplete();
		model.addAttribute("success", "you have discarded this order");
		return "messagePage";
	}

	/**
	 * checkout this order with extra detail of this order
	 * @param model
	 * @param cart
	 * @param order
	 * @param result
	 * @param sessionStatus
	 * @param sesison
	 * @see Cart
	 * @see Order
	 * @return
	 */
	@RequestMapping(value="/complete",method = RequestMethod.GET)
	public String complete(Model model,@ModelAttribute("cart") Cart cart,@Valid @ModelAttribute("order") Order order,BindingResult result, SessionStatus sessionStatus,HttpSession sesison){
		if (result.hasErrors()){
			return "checkout";
		}
		order.setCart(cart);
		RestMessage r=rb.calculate(order);
		Map<String,Object> mresult=r.getResult();
		String newId=(String) mresult.get("newId");
		order.setId(newId);
		String status=r.getStatus();
		if ("fail".equals(status)){
			result.rejectValue("city", "error.order", "sorry,we can not post to "+order.getCity());
			return "checkout";
		}
		double shippingCost=(double) mresult.get("total");
		order.setShippingCost(shippingCost);
		order.setTotalCost(shippingCost+cart.getTotal());
		model.addAttribute("success", " order "+newId+" is processing to "+order.getCity()+", shipping cost is $"+shippingCost+" final cost is $"+order.getTotalCost());
		SimpleUser user=(SimpleUser) sesison.getAttribute("currentUser");
		order.setUser(user.getName());
		cart.cleanCart();order.setCart(cart);
		odao.addNewOrder(order);
		sessionStatus.setComplete();
		return "messagePage";
	}
	
}
