package ch.hearc.hef1.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.model.User;
import ch.hearc.hef1.repository.TeamRepository;
import ch.hearc.hef1.service.UserService;


@Controller
public class TeamController {
	//private TeamService teamService;
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	private UserService userService;


	/**
	 * Select the team view to render by returning its name
	 */
	@GetMapping("/team")
	public String team(Map<String, Object> model) {		
		return "team";
	}
	
//	@GetMapping("/team")
//	public String team(Map<String, Object> model) {
//			
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			User user = userService.findUserByEmail(auth.getName());
//			Optional<Team> team = teamRepository.findById((long) user.getTeam().getId());
//			
//			if(team.isPresent()) {
//				model.put("title", team.get().getName());
//				
//				// Put the todo list by the given author
//				model.put("teams", team);
//				
//				// Return the page "home.html"
//				return "team";
//			}
//			else {
//				return ("redirect:/notfound");
//			}
//			
//	
//			
//		}
}