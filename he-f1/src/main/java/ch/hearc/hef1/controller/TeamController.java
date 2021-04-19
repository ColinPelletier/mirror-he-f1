package ch.hearc.hef1.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.CarPiece;
import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.model.User;
import ch.hearc.hef1.repository.CarRepository;
import ch.hearc.hef1.repository.TeamRepository;
import ch.hearc.hef1.repository.UserRepository;
import ch.hearc.hef1.service.CarService;
import ch.hearc.hef1.service.UserService;
import ch.hearc.hef1.tools.FileUploadUtil;

@Controller
public class TeamController {
	// private TeamService teamService;
	private static final String REDIRECT_ERROR = "redirect:/error";

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	UserService userService;

	@Autowired
	CarService carService;

	@Autowired
	CarRepository carRepository;

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
		// TODO check access

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User authenticatedUser = userService.findUserByUsername(auth.getName());

		if (authenticatedUser != null) {
			Team team = authenticatedUser.getTeam();
			if (team != null) {
				Car car = team.getCars().get(0);
				return ("redirect:/team/" + team.getId() + "/car/" + car.getId());
			}
		} else {
			return ("redirect:/signup");
		}
		model.put("teamToCreate", new Team());
		return "team";
	}

	@GetMapping("/team/{strTeamId}/car/{strCarId}")
	public String teamCar(@PathVariable String strTeamId, @PathVariable String strCarId, Map<String, Object> model) {
		// TODO : check if the user is a member of this team

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

	@PostMapping("/team/create")
	public String createTeam(@Valid @ModelAttribute Team team, BindingResult errors,
			@RequestParam("image") MultipartFile multipartFile) {
		// TODO check access

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User authenticatedUser = userService.findUserByUsername(auth.getName());
		if (authenticatedUser != null) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			team.setCarPicture(fileName);

			team = teamRepository.save(team);

			String carName = "TODO ADD CAR NAME IN FORM";
			carService.createAndSaveTeamCars(team, carName);

			// authenticatedUser.setTeam(team);
			// userService.saveUser(authenticatedUser);

			String uploadDir = "teams-car-picture/" + team.getId();

			try {
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			} catch (IOException ioe) {
				System.out.println("Unable to save file...");
				System.out.println(ioe);
				return REDIRECT_ERROR;
			}

			// TODO : create 2 cars and every pieces for these cars

			return "redirect:/team"; // redirect to /team controller method
		}
		return REDIRECT_ERROR;
	}

	@GetMapping("/team/test-display")
	public String testTeam(Map<String, Object> model) {
		/*
		 * To create a team : Create an empty object and pass it to the form. In the
		 * form, map this object attributes with the corresponding form properties. The
		 * model will then be available in the PostMapping method an could easily be
		 * updated in the DB
		 */
		// TODO check access
		model.put("teamToCreate", new Team());

		model.put("teams", teamRepository.findAll());

		return "test-display";
	}

	@GetMapping("/team/test-form")
	public String testForm(Map<String, Object> model) {
		// TODO check access
		/*
		 * To create a team : Create an empty object and pass it to the form. In the
		 * form, map this object attributes with the corresponding form properties. The
		 * model will then be available in the PostMapping method an could easily be
		 * updated in the DB
		 */

		model.put("teamToCreate", new Team());

		model.put("teams", teamRepository.findAll());

		return "test-form";
	}

	// @GetMapping("/team")
	// public String team(Map<String, Object> model) {
	//
	// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	// User user = userService.findUserByEmail(auth.getName());
	// Optional<Team> team = teamRepository.findById((long) user.getTeam().getId());
	//
	// if(team.isPresent()) {
	// model.put("title", team.get().getName());
	//
	// // Put the todo list by the given author
	// model.put("teams", team);
	//
	// // Return the page "home.html"
	// return "team";
	// }
	// else {
	// return ("redirect:/notfound");
	// }
	//
	//
	//
	// }

	// @PostMapping("/createteam")
	// public String saveTeam(@Validated @ModelAttribute Team team, BindingResult
	// errors, Model model,
	// RedirectAttributes redirAttrs) {

	// if (!errors.hasErrors()) {
	// if (team.validate())
	// else
	// teamRepository.save(team);
	// throw new RuntimeException("The team is not complete ! Please fill all the
	// fields");
	// }
	// }return((errors.hasErrors())?"team":"redirect:/");
}