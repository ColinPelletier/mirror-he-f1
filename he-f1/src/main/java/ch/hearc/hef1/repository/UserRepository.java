package ch.hearc.hef1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.User;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> 
{
	User findByEmail(String email);
}
