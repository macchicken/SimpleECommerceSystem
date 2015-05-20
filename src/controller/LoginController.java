package controller;

import model.SimpleUser;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("currentUser")
public class LoginController {

	@RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

	@RequestMapping("/loginError")
    public String loginError(Model model) {
        return "loginError";
    }

	@RequestMapping("/logout")
	public String logout(Model model) {
		return "/login";
	}

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
		}
		return "mainPage";
	}
}
