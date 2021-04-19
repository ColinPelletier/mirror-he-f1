package ch.hearc.hef1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.RepairUpgrade;

@Repository("RepairUpgradeRepository")
public interface RepairUpgradeRepository extends JpaRepository<RepairUpgrade, Long> {

}
