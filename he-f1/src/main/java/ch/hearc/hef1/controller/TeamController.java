package ch.hearc.hef1.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.repository.TeamRepository;
import ch.hearc.hef1.service.UserService;

@Controller
public class TeamController {
	// private TeamService teamService;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	private UserService userService;

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

		model.put("teamToCreate", new Team());

		model.put("teams", teamRepository.findAll());

		return "team";
	}

	@PostMapping("/team/create")
	public String createTeam(@Valid @ModelAttribute Team team, BindingResult errors, Model model) {

		teamRepository.save(team);

		return "redirect:/team"; // redirect to /team controller method
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
}