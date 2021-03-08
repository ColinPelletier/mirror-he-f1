package ch.hearc.hef1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.Role;

@Repository("RoleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>
{
	Role findByRole(String role);
}