package ch.hearc.hef1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import ch.hearc.hef1.model.RepairUpgrade;
import ch.hearc.hef1.model.User;
import ch.hearc.hef1.repository.RepairUpgradeRepository;

@Service("repairUpgradeService")
public class RepairUpgradeService {

	@Autowired
	private RepairUpgradeRepository repairUpgradeRepository;

	public List<RepairUpgrade> findUserRepairUpgrade(User user) {
		return repairUpgradeRepository.findByUser(user);
	}

	public void saveRepairUpgrade(RepairUpgrade repairUpgrade) {
		repairUpgradeRepository.save(repairUpgrade);
	}
}
