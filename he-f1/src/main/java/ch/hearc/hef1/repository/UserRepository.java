package ch.hearc.hef1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.User;
import ch.hearc.hef1.model.UserRole;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);

	public User findByUsername(String username);

	List<User> findByRole(UserRole role);
}
