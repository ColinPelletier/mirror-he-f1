package ch.hearc.hef1.controller;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.RuntimeErrorException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.repository.CarRepository;
import ch.hearc.hef1.repository.TeamRepository;
import ch.hearc.hef1.service.CarService;
import ch.hearc.hef1.service.UserService;

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

		model.put("teamToCreate", new Team());

		model.put("teams", teamRepository.findAll());

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

				model.put("teamToCreate", new Team());
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
	public String createTeam(@Valid @ModelAttribute Team team, BindingResult errors, Model model) {
		// TODO check access

		Team createdTeam = teamRepository.save(team);

		// TODO : create 2 cars and every pieces for these cars

		return "redirect:/team"; // redirect to /team controller method
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
	@PostMapping("/createteam")
	public String saveTeam(@Validated @ModelAttribute Team team, BindingResult errors, Model model,
			RedirectAttributes redirAttrs) {

		if (!errors.hasErrors()) {
			if (team.validate())
			else
				teamRepository.save(team);
				throw new RuntimeException("The team is not complete ! Please fill all the fields");
		}
	}
		return ((errors.hasErrors()) ? "team" : "redirect:/");
}