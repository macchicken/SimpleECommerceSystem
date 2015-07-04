package controller;

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
	 * @return - a new instance of Cart 
	 */
	@ModelAttribute("cart")
	public Cart createCart(){
		return new Cart();
	}

	/**
	 * initialize a order while it is null
	 * @see Order
	 * @return - a new instance of Order
	 */
	@ModelAttribute("order")
	public Order createOrder(){
		return new Order();
	}
	
	/**
	 * load a partical page of displaying the cart
	 * @param model - spring model pass to frontController
	 * @param cart - a cart instance in the spring model
	 * @see Cart
	 * @return view page name - cart_partial
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String loadCart(Model model, @ModelAttribute("cart") Cart cart){
		return "cart_partial";
	}

	/**
	 * Add a product to the shopping cart
	 * @param productId - unique id for a product
	 * @param model - spring model pass to frontController
	 * @param cart - a cart instance in the spring model
	 * @param sesison - spring http session
	 * @see Cart
	 * @return view page name - cart_partial
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
	 * @param productId - unique id for a product
	 * @param model - spring model pass to frontController
	 * @param cart - a cart instance in the spring model
	 * @param sesison - spring http session
	 * @see Cart
	 * @return view page name - cart_partial
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
	
	/**
	 * proceed to checkout page
	 * @param model - spring model pass to frontController
	 * @param cart - a cart instance in the spring model
	 * @param order - a order instance in the spring model
	 * @return view page name - checkout
	 */
	@RequestMapping("/checkout")
	public String checkout(Model model,@ModelAttribute("cart") Cart cart,@ModelAttribute("order") Order order){
		return "checkout";
	}

	/**
	 * Discard this order before submit to checkout
	 * @param model - spring model pass to frontController
	 * @param cart - a cart instance in the spring model
	 * @param order - a order instance in the spring model
	 * @param sessionStatus - state of spring http session
	 * @see Cart
	 * @see Order
	 * @return view page name - messagePage
	 */
	@RequestMapping("/discard")
	public String disCard(Model model,@ModelAttribute("cart") Cart cart,@ModelAttribute("order") Order order,SessionStatus sessionStatus){
		sessionStatus.setComplete();
		model.addAttribute("messageContent", "you have discarded this order");
		return "messagePage";
	}

	/**
	 * checkout this order with extra detail of this order
	 * @param model - spring model pass to frontController
	 * @param cart - a cart instance in the spring model
	 * @param order - a order instance in the spring model
	 * @param result validation result object relate to objects with Valid annotation
	 * @param sessionStatus - state of spring http session
	 * @param sesison - spring http session
	 * @see Cart
	 * @see Order
	 * @return view page name - checkout if validation is not passed, else messagePage
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
		SimpleUser user=(SimpleUser) sesison.getAttribute("currentUser");
		order.setUser(user.getName());
		cart.cleanCart();order.setCart(cart);
		String success=transform(cart);
		success+="<p>order "+newId+" is processing to "+order.getCity()+", shipping cost is $"+shippingCost+" final cost is $"+order.getTotalCost()+"</p>";
		model.addAttribute("messageContent", success);
		odao.addNewOrder(order);
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
