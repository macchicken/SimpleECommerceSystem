package controller;

import model.SimpleUser;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import commons.LogUtils;

/**
 * Controller of an user login
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
@Controller
@SessionAttributes("currentUser")
public class LoginController {

	private LogUtils logger=LogUtils.getInstance();
	
	/**
	 * to login page
	 * @param model - spring model pass to frontController
	 * @return view page name - login
	 */
	@RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

	/**
	 * to login error page
	 * @param model - spring model pass to frontController
	 * @return view page name - loginError
	 */
	@RequestMapping("/loginError")
    public String loginError(Model model) {
        return "loginError";
    }

	/**
	 * logout process
	 * @param model - spring model pass to frontController
	 * @return view page name - login
	 */
	@RequestMapping("/logout")
	public String logout(Model model) {
		return "/login";
	}

	/**
	 * to main page after login successfully and store the user information in the session
	 * @param model - spring model pass to frontController
	 * @return view page name - mainPage
	 */
	@RequestMapping("/eco/mainPage")
	public String simplePage(Model model) {
		if (!model.containsAttribute("currentUser")) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String name = auth.getName();
			String roles = "";
			for (GrantedAuthority g : auth.getAuthorities()) {
				roles += g.getAuthority()+" ; ";
			}
			SimpleUser my = new SimpleUser(name, roles);
			model.addAttribute("currentUser", my);
			logger.trace(name+" user login");
		}
		return "mainPage";
	}
}
