package controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import model.Cart;
import model.CartItem;
import model.Product;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import flk.PhotoQuerySearch;


@Controller
@RequestMapping("/eco/products")
@SessionAttributes("sproducts")
public class ProductController {

	static Log log = LogFactory.getLog(ProductController.class);
	String classname = this.getClass().getName();
	private PhotoQuerySearch qs=PhotoQuerySearch.getInstance();

	@RequestMapping(method = RequestMethod.GET)
	public String listAll(Model model,HttpSession session){
		List<Product> result=qs.getProducts("tiger");
		Map<String,Product> sproducts=new HashMap<String,Product>();
		for (Product p:result){
			sproducts.put(p.getProductId(), p);
		}
		model.addAttribute("sproducts", sproducts);
		model.addAttribute("products", result);
		Cart c=(Cart) session.getAttribute("cart");
		if (c!=null){
			model.addAttribute("cart", c);
		}
		return "productlist";
	}

	@RequestMapping(value="/search",method = RequestMethod.GET)
	public String searchProducts(@RequestParam(value="keyword", required=false, defaultValue="") String keyword,Model model,HttpSession session){
		System.out.println("search keyword "+keyword);
		Cart c=(Cart) session.getAttribute("cart");
		List<Product> result=null;
		boolean needSearch=true;
		if ("".equals(keyword.trim())){
			keyword="tiger";
			if (c!=null){
				needSearch=false;
			}
		}else{
			try {
				keyword=new String(keyword.getBytes("ISO-8859-1"), "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (needSearch){
			result=qs.getProducts(keyword);
			Map<String,Product> sproducts=new HashMap<String,Product>();
			if (result!=null) {
				for (Product p : result) {
					sproducts.put(p.getProductId(), p);
				}
			}
			model.addAttribute("sproducts", sproducts);
		}else{
			result=new ArrayList<Product>();
			for (CartItem ci:c.getItems()){
				result.add(ci.getProduct());
			}
		}
		model.addAttribute("products",result);
		if (c!=null){
			model.addAttribute("cart", c);
		}
		return "productlist";
	}
	
}
