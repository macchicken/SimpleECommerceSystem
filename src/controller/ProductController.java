package controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import model.Cart;
import model.CartItem;
import model.PageModel;
import model.Product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import commons.LogUtils;
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
@SessionAttributes({"sproducts","searchKeywords"})
public class ProductController {

	/**
	 * service of querying products
	 * @see PhotoQuerySearch
	 */
	private PhotoQuerySearch qs=PhotoQuerySearch.getInstance();
	private LogUtils logger=LogUtils.getInstance();
	
	/**
	 * retreiving products with default search key word
	 * @param model - spring model pass to frontController
	 * @param session - spring http session
	 * @return view page name - productlist
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String listAll(Model model,HttpSession session){
		PageModel<Product> products=qs.getProducts("tiger","1");
		List<Product> result=products.getElementList();
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
	 * Retrieving products with a search key word
	 * @param keyword - search key word
	 * @param pageNumber - desired search page number
	 * @param continued - a flag for indicating its searching is continuing for next page
	 * @param model - spring model pass to frontController
	 * @param session - spring http session
	 * @return view page name - productlist or productCatalogue
	 */
	@RequestMapping(value="/search",method = RequestMethod.GET)
	public String searchProducts(
			@RequestParam(value = "keyword", required = false, defaultValue = "tiger") String keyword,
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") String pageNumber,
			@RequestParam(value = "continued", required = false, defaultValue = "false") String continued,
			Model model, HttpSession session) {
		String viewName=null;
		Cart c=(Cart) session.getAttribute("cart");
		List<Product> result=null;
		PageModel<Product> products=null;
		if ("true".equals(continued)){
			viewName="productCatalogue";
			keyword=(String) session.getAttribute("searchKeywords");
		}else{
			viewName="productlist";
			try {
				keyword=new String(keyword.getBytes("ISO-8859-1"), "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			model.addAttribute("searchKeywords",keyword);
		}
		logger.trace("search keyword "+keyword);
		products=qs.getProducts(keyword,pageNumber);
		result=products.getElementList();
		Map<String,Product> sproducts=new HashMap<String,Product>();
		if (result!=null) {
			for (Product p : result) {
				sproducts.put(p.getProductId(), p);
			}
		}
		model.addAttribute("sproducts", sproducts);
		model.addAttribute("products",products);
		if (c!=null){
			model.addAttribute("cart", c);
		}
		model.addAttribute("searchFlag","prod");
		return viewName;
	}
	
	/**
	 * Retrieving products from the shopping cart
	 * @param pageNumber - desired search page number
	 * @param continued - a flag for indicating its searching is continuing for next page
	 * @param model - spring model pass to frontController
	 * @param session - spring http session
	 * @return view page name - productlist or productCatalogue
	 */
	@RequestMapping(value="/search/cart",method = RequestMethod.GET)
	public String searchCartItems(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") String pageNumber,
			@RequestParam(value = "continued", required = false, defaultValue = "false") String continued,
			Model model, HttpSession session) {
		String viewName=null;
		Cart c=(Cart) session.getAttribute("cart");
		List<Product> result=new ArrayList<Product>();
		PageModel<Product> products=null;
		if ("true".equals(continued)){
			viewName="productCatalogue";
		}else{
			viewName="productlist";
		}
		if (c==null){
			products=new PageModel<Product>(0,0,result);
			model.addAttribute("products",products);
			return viewName;
		}
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
		products=new PageModel<Product>(totalPage,currentPage,result);
		model.addAttribute("products",products);
		model.addAttribute("cart", c);
		model.addAttribute("searchFlag","cartitems");
		return viewName;
	}
	
}
