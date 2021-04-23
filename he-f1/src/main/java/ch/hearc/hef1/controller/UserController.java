package ch.hearc.hef1.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;

import ch.hearc.hef1.model.User;
import ch.hearc.hef1.model.UserRole;
import ch.hearc.hef1.repository.UserRepository;
import ch.hearc.hef1.service.UserService;

@Controller
public class UserController {

	private static final String REDIRECT_ERROR = "redirect:/error";

	@Autowired
	private UserService userService;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/signup")
	public String signup(Map<String, Object> model) {
		User user = new User();
		model.put("user", user);

		List<Enum> roles = new ArrayList<Enum>(EnumSet.allOf(UserRole.class));

		model.put("roles", roles);

		return "signup";
	}

	@PostMapping("/signup")
	public String createUser(@Valid User user, BindingResult bindingResult, Map<String, Object> model) {

		User userExists = userService.findUserByEmail(user.getEmail());
		model.put("roles", UserRole.values());

		if (userExists != null) {
			return ("redirect:/signup?error=true");
		}

		if (bindingResult.hasErrors()) {
			return ("redirect:/signup?error=true");
		} else {
			userService.saveUser(user);
			model.put("msg", "User has been registered successfully!");
			model.put("user", new User());

			return ("redirect:/");
		}
	}

	@GetMapping("/recruiting")
	public String recruiting(@RequestParam(value = "selectedRole", required = false) String strRole,
			Map<String, Object> model) {

		if (strRole != null) {
			UserRole role = UserRole.valueOf(strRole);
			model.put("users", userRepository.findByRole(role));
		} else {
			model.put("users", userRepository.findAll());
		}

		List<UserRole> roles = new ArrayList<UserRole>(EnumSet.allOf(UserRole.class));
		model.put("roles", roles);

		return "recruiting";
	}

	@PostMapping("/recruit/{strUserId}")
	public String recruit(@PathVariable String strUserId, Map<String, Object> model) {

		// Check id validity
		long userId;

		try {
			userId = Long.parseLong(strUserId);
		} catch (NumberFormatException e) {
			System.err.println("id must be an integer");
			return REDIRECT_ERROR;
		}

		Optional<User> user = userRepository.findById(userId);

		if (user.isPresent()) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User authenticatedUser = userService.findUserByUsername(auth.getName());

			user.get().setTeam(authenticatedUser.getTeam());
			userService.saveUser(user.get());
			model.put("msg", "User has been recruited successfully!");

			return ("redirect:/recruiting?recruited=true");

		} else {
			return REDIRECT_ERROR;
		}
	}

}