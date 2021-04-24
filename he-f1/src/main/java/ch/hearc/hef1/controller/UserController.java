package ch.hearc.hef1.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.hef1.model.Notification;
import ch.hearc.hef1.model.User;
import ch.hearc.hef1.model.UserRole;
import ch.hearc.hef1.repository.NotificationRepository;
import ch.hearc.hef1.repository.UserRepository;
import ch.hearc.hef1.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	NotificationRepository notificationRepository;

	@GetMapping("login-error")
	public String loginError() {
		throw new RuntimeException("Username or password doesn't match...");
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
	public String createUser(@Valid User user, BindingResult bindingResult, Map<String, Object> model) {

		User userExists = userService.findUserByEmail(user.getEmail());
		model.put("roles", UserRole.values());

		if (userExists != null) {
			throw new RuntimeException("User allready exist.");
		}

		if (bindingResult.hasErrors()) {
			throw new RuntimeException("All fields are required!");
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

		if (userService.isUserAuthenticated()) {
			if (strRole != null) {
				UserRole role = UserRole.valueOf(strRole);
				model.put("users", userRepository.findByRole(role));
			} else {
				model.put("users", userRepository.findAll());
			}

			List<Enum> roles = new ArrayList<Enum>(EnumSet.allOf(UserRole.class));
			model.put("roles", roles);
			model.put("authenticatedUserRole", userService.getAuthenticatedUserRole().getDescription());

			return "recruiting";
		}

		throw new RuntimeException("User needs to be authenticated.");
	}

	@PostMapping("/recruit/{strUserId}")
	public String recruit(@PathVariable String strUserId, Map<String, Object> model) {

		// Check id validity
		long userId;

		try {
			userId = Long.parseLong(strUserId);
		} catch (NumberFormatException e) {
			throw new RuntimeException("ID must be an integer.");
		}

		if (userService.isUserAuthenticated()) {
			if (userService.getAuthenticatedUserRole().equals(UserRole.MANAGER)) {
				Optional<User> user = userRepository.findById(userId);

				if (user.isPresent()) {
					Notification notification = new Notification("The manager of this team want to recruit you.",
							userService.getAuthenticatedUser().getTeam(), user.get());

					notificationRepository.save(notification);
					// user.get().setTeam(userService.getAuthenticatedUser().getTeam());
					// userService.updateUser(user.get());
					model.put("msg", "User has been recruited successfully!");

					return ("redirect:/recruiting?recruited=true");

				} else {
					throw new RuntimeException("The user that you want to recruit doesn't exist.");
				}
			} else {
				throw new RuntimeException("Only managers can recruit members.");
			}
		}
		throw new RuntimeException("User needs to be authenticated.");
	}
}