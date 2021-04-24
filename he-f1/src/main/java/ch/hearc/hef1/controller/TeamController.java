package ch.hearc.hef1.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.CarPiece;
import ch.hearc.hef1.model.RepairUpgrade;
import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.model.User;
import ch.hearc.hef1.model.UserRole;
import ch.hearc.hef1.repository.CarPieceRepository;
import ch.hearc.hef1.repository.CarRepository;
import ch.hearc.hef1.repository.RepairUpgradeRepository;
import ch.hearc.hef1.repository.TeamRepository;
import ch.hearc.hef1.service.CarService;
import ch.hearc.hef1.service.RepairUpgradeService;
import ch.hearc.hef1.service.TeamService;
import ch.hearc.hef1.service.UserService;

@Controller
public class TeamController {
	@Autowired
	TeamRepository teamRepository;

	@Autowired
	TeamService teamService;

	@Autowired
	UserService userService;

	@Autowired
	RepairUpgradeRepository repairUpgradeRepository;

	@Autowired
	RepairUpgradeService repairUpgradeService;

	@Autowired
	CarPieceRepository carPieceRepository;

	@Autowired
	CarRepository carRepository;

	@Autowired
	CarService carService;

	/**
	 * Select the team view to render by returning its name
	 */
	@GetMapping("/team")
	public String team(Map<String, Object> model) {
		/*
		 * To create a team : Create an empty object and pass it to the form. In the
		 * form, map this object attributes with the corresponding form properties. The
		 * model will then be available in the PostMapping method an could easily be
		 * updated in the DB
		 */
		User authenticatedUser = userService.getAuthenticatedUser();

		if (userService.isAuthenticated(authenticatedUser)) {
			Team team = authenticatedUser.getTeam();
			if (team != null) {
				Car car = team.getCars().get(0);
				return "redirect:/team/" + team.getId() + "/car/" + car.getId();
			}
		} else {
			return "redirect:/signup";
		}

		model.put("teamToCreate", new Team());
		model.put("authenticatedUserRole", userService.getAuthenticatedUserRole().getDescription());
		return "team";
	}

	@GetMapping("/team/{strTeamId}/car/{strCarId}")
	public String teamCar(@PathVariable String strTeamId, @PathVariable String strCarId, Map<String, Object> model) {
		User authenticatedUser = userService.getAuthenticatedUser();

		if (userService.isAuthenticated(authenticatedUser)) {
			// Check id validity
			long teamId;
			long carId;
			try {
				teamId = Long.parseLong(strTeamId);
				carId = Long.parseLong(strCarId);
			} catch (NumberFormatException e) {
				throw new RuntimeException("IDs must be integers.");
			}
			Optional<Team> team = teamRepository.findById(teamId);
			Optional<Car> car = carRepository.findById(carId);

			checkRepairUpgradePiece(authenticatedUser);

			// check wether the teams owns this car
			if (team.isPresent() && car.isPresent()) {
				if (team.get() == authenticatedUser.getTeam()) {
					if (carService.isTeamOwner(car.get(), team.get())) {
						List<CarPiece> carPieces = carService.findCarPieces(car.get());

						model.put("team", team.get());
						model.put("car", car.get());
						model.put("carPieces", carPieces);
						model.put("userRole", authenticatedUser.getRole().toString());

						teamService.setCarsUrlsInModel(team.get(), model);
					} else {
						throw new RuntimeException("Car " + carId + " is not owned by team " + teamId + ".");
					}
				} else {
					throw new RuntimeException(
							"User " + authenticatedUser.getUsername() + " is not a member of team " + teamId + ".");
				}
			} else {
				throw new RuntimeException("Invalid id for car or team.");
			}
			return "team";
		}
		throw new RuntimeException("User need to be authenticated.");
	}

	private void checkRepairUpgradePiece(User authenticatedUser) {
		// Get RepairUpgrade list
		List<RepairUpgrade> listRepairUpgrades = repairUpgradeService.findUserRepairUpgrade(authenticatedUser);
		Date now = new Date();

		// check if repairUpgrade finished
		for (RepairUpgrade repairUpgrade : listRepairUpgrades) {
			if (repairUpgrade.getEndDate().compareTo(now) < 0) {
				CarPiece carPiece = repairUpgrade.getCarPiece();
				if (repairUpgrade.isRepair()) {
					// Upgrade car piece
					carPiece.setLevel(carPiece.getLevel() + 1);
				} else {
					// Repair car piece
					carPiece.setWear(0);
				}
				carPieceRepository.save(carPiece);
				repairUpgradeRepository.delete(repairUpgrade);
			}
		}
	}

	@PostMapping("/team/create")
	public String createTeam(@Valid @ModelAttribute Team team, BindingResult errors,
			@RequestParam("image") MultipartFile multipartFile, @RequestParam("carName") String carName) {

		User authenticatedUser = userService.getAuthenticatedUser();

		if (userService.isAuthenticated(authenticatedUser)) {
			if (authenticatedUser.getRole().equals(UserRole.MANAGER)) {
				team.setBudget(Team.STARTING_BUDGET);
				team.setPoints(Team.STARTING_POINTS);
				team = teamRepository.save(team);

				boolean fileUploaded = teamService.uploadCarImage(team, multipartFile);
				if (fileUploaded) {
					team = teamRepository.save(team);

					carService.createAndSaveTeamCars(team, carName);

					authenticatedUser.setTeam(team);
					userService.updateUser(authenticatedUser);

					return "redirect:/team"; // redirect to /team controller method
				}
			} else {
				throw new RuntimeException("Only managers can create teams.");
			}
		}
		throw new RuntimeException("User needs to be authenticated.");
	}
}