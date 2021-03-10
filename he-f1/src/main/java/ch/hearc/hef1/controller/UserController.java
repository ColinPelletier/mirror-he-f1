package ch.hearc.hef1.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.hef1.model.User;
import ch.hearc.hef1.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Select the login view to render by returning its name
	 */
	@GetMapping("/login")
	public String login(Map<String, Object> model) {	
		model.put("author", new User());
		return "login";
	}
	
//	@GetMapping("/login")
//	public String login(Model model) {
//		model.addAttribute("person", new Person());
//		
//		return "login";
//	}
	

	@GetMapping("/signup")
	public String signup(Map<String, Object> model) {
		User user = new User();
		model.put("user", user);
		
		return "signup";
	}

	@PostMapping("/signup")
	public String createAuthor(@Valid User author, BindingResult bindingResult, Map<String, Object> model) {

		User userExists = userService.findUserByEmail(author.getEmail());

		if (userExists != null) {
			return ("redirect:/signup?error=true");
		}
		
		if (bindingResult.hasErrors()) 
		{
			return ("redirect:/signup?error=true");
		} 
		else 
		{
			userService.saveUser(author);
			model.put("msg", "User has been registered successfully!");
			model.put("author", new User());

			return "login";
		}
	}

}