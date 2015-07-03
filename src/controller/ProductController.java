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
import model.Products;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import service.flk.PhotoQuerySearch;

/**
 * Controller of retreiving products
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
@Controller
@RequestMapping("/eco/products")
@SessionAttributes("sproducts")
public class ProductController {

	/**
	 * service of querying products
	 * @see PhotoQuerySearch
	 */
	private PhotoQuerySearch qs=PhotoQuerySearch.getInstance();

	/**
	 * retreiving products with default search key word
	 * @param model - spring model pass to frontController
	 * @param session - spring http session
	 * @return view page name - productlist
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String listAll(Model model,HttpSession session){
		Products products=qs.getProducts("tiger","1");
		List<Product> result=products.getProList();
		Map<String,Product> sproducts=new HashMap<String,Product>();
		for (Product p:result){
			sproducts.put(p.getProductId(), p);
		}
		model.addAttribute("sproducts", sproducts);
		model.addAttribute("products", products);
		Cart c=(Cart) session.getAttribute("cart");
		if (c!=null){
			model.addAttribute("cart", c);
		}
		return "productlist";
	}

	/**
	 * retreiving products with a search key word
	 * @param keyword - search key word
	 * @param pageNumber - desired search page number
	 * @param continued - a flag for indicating its searching is continuing for next page
	 * @param model - spring model pass to frontController
	 * @param session - spring http session
	 * @return view page name - productlist
	 */
	@RequestMapping(value="/search",method = RequestMethod.GET)
	public String searchProducts(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") String pageNumber,
			@RequestParam(value = "continued", required = false, defaultValue = "false") String continued,
			Model model, HttpSession session) {
		System.out.println("search keyword "+keyword);
		Cart c=(Cart) session.getAttribute("cart");
		List<Product> result=null;
		Products products=null;
		boolean needSearch=true;
		if ("".equals(keyword.trim())){
			keyword="tiger";
			if (c!=null&&c.getTotalItem()!=0){
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
			products=qs.getProducts(keyword,pageNumber);
			result=products.getProList();
			Map<String,Product> sproducts=new HashMap<String,Product>();
			if (result!=null) {
				for (Product p : result) {
					sproducts.put(p.getProductId(), p);
				}
			}
			model.addAttribute("sproducts", sproducts);
		}else{
			result=new ArrayList<Product>();
			int perPage=Integer.parseInt(commons.Constants.DEFAULT_NUMBER);
			int totalPage=c.getItems().size()/perPage;
			if (c.getItems().size()%perPage!=0){totalPage=totalPage+1;}
			int currentPage=Integer.parseInt(pageNumber)-1;
			int count=0;
			for (CartItem ci:c.getItems()){
				if ((count/perPage)==currentPage){
					result.add(ci.getProduct());}
				if (result.size()==perPage){break;}
				count++;
			}
			currentPage=currentPage+1;
			products=new Products(totalPage,currentPage,"",result);
		}
		model.addAttribute("products",products);
		if (c!=null){
			model.addAttribute("cart", c);
		}
		if ("true".equals(continued)){return "productCatalogue";}
		else{return "productlist";}
	}
	
}
