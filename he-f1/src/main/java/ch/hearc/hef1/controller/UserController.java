package ch.hearc.hef1.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.management.relation.Role;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.hef1.model.User;
import ch.hearc.hef1.model.UserRole;
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

	@GetMapping("/signup")
	public String signup(Map<String, Object> model) {
		User user = new User();
		model.put("user", user);

		List<Enum> roles = new ArrayList<Enum>(EnumSet.allOf(UserRole.class));

		model.put("roles", roles);

		return "signup";
	}

	@PostMapping("/signup")
	public String createAuthor(@Valid User author, BindingResult bindingResult, Map<String, Object> model) {

		User userExists = userService.findUserByEmail(author.getEmail());
		model.put("roles", UserRole.values());
		// System.out.println("name = " + author.getUsername());
		System.out.println("================================================");
		System.out.println(author.getRole());
		System.out.println("================================================");

		if (userExists != null) {
			return ("redirect:/signup?error=true");
		}

		if (bindingResult.hasErrors()) {
			return ("redirect:/signup?error=true");
		} else {
			userService.saveUser(author);
			model.put("msg", "User has been registered successfully!");
			model.put("author", new User());

			return "login";
		}
	}

}