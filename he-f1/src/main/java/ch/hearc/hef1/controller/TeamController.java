package ch.hearc.hef1.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	// private TeamService teamService;
	private static final String REDIRECT_ERROR = "redirect:/error";

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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User authenticatedUser = userService.findUserByUsername(auth.getName());

		if (authenticatedUser != null) {
			Team team = authenticatedUser.getTeam();
			if (team != null) {
				Car car = team.getCars().get(0);
				return "redirect:/team/" + team.getId() + "/car/" + car.getId();
			}
		} else {
			return "redirect:/signup";
		}
		model.put("teamToCreate", new Team());
		return "team";
	}

	@GetMapping("/team/{strTeamId}/car/{strCarId}")
	public String teamCar(@PathVariable String strTeamId, @PathVariable String strCarId, Map<String, Object> model) {
		// TODO : check if the user is a member of this team
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User authenticatedUser = userService.findUserByUsername(auth.getName());
		if (authenticatedUser != null) {
			// Check id validity
			long teamId;
			long carId;
			try {
				teamId = Long.parseLong(strTeamId);
				carId = Long.parseLong(strCarId);
			} catch (NumberFormatException e) {
				System.err.println("id must be an integer");
				return REDIRECT_ERROR;
			}
			Optional<Team> team = teamRepository.findById(teamId);
			Optional<Car> car = carRepository.findById(carId);

			checkRepairUpgradePiece(authenticatedUser);

			// check wether the teams owns this car
			if (team.isPresent() && car.isPresent()) {
				if (carService.isTeamOwner(car.get(), team.get())) {
					List<CarPiece> carPieces = carService.findCarPieces(car.get());
					// model.put("teamToCreate", new Team());
					model.put("team", team.get());
					model.put("car", car.get());
					model.put("carPieces", carPieces);
				} else {
					System.err.println("Car " + carId + " is not owned by team " + teamId);
					return REDIRECT_ERROR;
				}
			} else {
				System.err.println("Invalid id for car or team");
				return REDIRECT_ERROR;
			}

			return "team";
		}
		System.err.println("User need to be authenticated");
		return REDIRECT_ERROR;
	}

	private void checkRepairUpgradePiece(User authenticatedUser) {
		// Get RepairUpgrade list
		List<RepairUpgrade> listRepairUpgrades = repairUpgradeService.findUserRepairUpgrade(authenticatedUser);
		Date now = new Date();

		listRepairUpgrades.stream().filter(ru -> ru.getEndDate().getSeconds() < now.getSeconds())
				.forEach(ur -> System.out.println(
						"======================================= yoyoy yoyo ==================================================="));

		// Sort list to get finished repairUpgrade
		List<RepairUpgrade> listFinishedRepairUpgrades = listRepairUpgrades.stream()
				.filter(ru -> ru.getEndDate().getSeconds() < now.getSeconds()).collect(Collectors.toList());

		for (RepairUpgrade repairUpgrade : listFinishedRepairUpgrades) {
			// Upgrade car piece
			CarPiece carPiece = repairUpgrade.getCarPiece();
			carPiece.setLevel(carPiece.getLevel() + 1);
			carPieceRepository.save(carPiece);

			repairUpgradeRepository.delete(repairUpgrade);
		}
	}

	@PostMapping("/team/create")
	public String createTeam(@Valid @ModelAttribute Team team, BindingResult errors,
			@RequestParam("image") MultipartFile multipartFile) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User authenticatedUser = userService.findUserByUsername(auth.getName());

		if (authenticatedUser != null) {

			team.setBudget(Team.STARTING_BUDGET);
			team = teamRepository.save(team);

			boolean fileUploaded = teamService.uploadCarImage(team, multipartFile);
			if (fileUploaded) {
				team = teamRepository.save(team);

				String carName = "TODO ADD CAR NAME IN FORM";
				carService.createAndSaveTeamCars(team, carName);

				authenticatedUser.setTeam(team);
				userService.updateUser(authenticatedUser);

				return "redirect:/team"; // redirect to /team controller method
			}
		}
		System.err.println("User need to be authenticated");
		return REDIRECT_ERROR;
	}
}