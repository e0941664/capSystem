package team2.capSystem.controller;

import javax.servlet.http.HttpSession;
// import javax.validation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import team2.capSystem.model.User;
import team2.capSystem.services.AdminService;

@Controller
public class LoginController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/")
	public String standard(Model model, HttpSession session) {
		if(session.getAttribute("validated") != null || session.getAttribute("admvalidated") !=null) {
			return "logout";
		}
		model.addAttribute("user", new User());
		return "login";
	}
	
	@RequestMapping("/login/authenticate")
	public String login(@ModelAttribute("user") User user, HttpSession session, @RequestParam(name="LoginAs") String LoginAs) {
		System.out.println(LoginAs);
		
		if (LoginAs.equals("admin"))
		{
			System.out.println("entered if");
			System.out.println(user);
			
			User u = adminService.findAdminByEmail(user.getUsername());
			
			System.out.println("Got user");
			
			if((u.getEmail().equals(user.getUsername())) && (u.getPassword().equals((user.getPassword()))))
			{
				System.out.println("Entered log out if");
				return "logout";
			}
			
		}
		
		return "login";
	}
}
