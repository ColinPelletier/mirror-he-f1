package ch.hearc.hef1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.hearc.hef1.model.User;
import ch.hearc.hef1.model.UserRole;
import ch.hearc.hef1.repository.UserRepository;

@Service("userService")
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	public User getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return findUserByUsername(auth.getName());
	}

	public boolean isUserAuthenticated() {
		return getAuthenticatedUser() != null ? true : false;
	}

	public boolean isAuthenticated(User user) {
		return user != null ? true : false;
	}

	public UserRole getAuthenticatedUserRole() {
		return getAuthenticatedUser().getRole();
	}
}