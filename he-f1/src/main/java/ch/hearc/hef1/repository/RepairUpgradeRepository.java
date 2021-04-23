package ch.hearc.hef1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.RepairUpgrade;
import ch.hearc.hef1.model.User;

@Repository("RepairUpgradeRepository")
public interface RepairUpgradeRepository extends JpaRepository<RepairUpgrade, Long> {
	// Find RepairUpgrade by user
	public List<RepairUpgrade> findByUser(User user);
}
