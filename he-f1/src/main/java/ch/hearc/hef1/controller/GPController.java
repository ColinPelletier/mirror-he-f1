package ch.hearc.hef1.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.hef1.model.GP;
import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.model.UserRole;
import ch.hearc.hef1.service.GPService;
import ch.hearc.hef1.service.TeamService;
import ch.hearc.hef1.service.UserService;

@Controller
public class GPController {
    @Autowired
    private GPService gpService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @GetMapping("/grand-prix")
    public String getGPs(@RequestParam(value = "selectedName", required = false) String strName,
            Map<String, Object> model) {

        if (strName != null) {
            model.put("gps", gpService.findByNameContaining(strName));
        } else {
            model.put("gps", gpService.findAll());
        }

        return "grand-prix";
    }

    // @GetMapping("/grand-prix/simulating")
    // public String simulateGP() {
    @PostMapping("/grand-prix/simulating")
    public String simulateGP(@Valid @ModelAttribute GP gp, BindingResult errors) {
        if (gp != null) {
            if (userService.getAuthenticatedUserRole().equals(UserRole.MANAGER)) {
                List<Team> teams = teamService.getRandomTeams(GP.NB_TEAMS_IN_GP);

                if (teams != null) {
                    boolean allreadyParticipated = teamService.checkParticipation(gp);

                    if (!allreadyParticipated) {

                        gpService.simulateGP(teams);

                        return "redirect:/grand-prix";
                    } else {
                        throw new RuntimeException("You allready have participated to this Grand Prix !");
                    }
                }
            } else {
                throw new RuntimeException("Only managers can register a team in a Grand Prix.");
            }
        }

        throw new RuntimeException("Unable to simulate grand prix. Please sign in.");
    }

}
