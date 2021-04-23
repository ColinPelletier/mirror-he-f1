package ch.hearc.hef1.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.hearc.hef1.model.GP;
import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.service.GPService;
import ch.hearc.hef1.service.TeamService;

@Controller
public class GPController {
    private static final String REDIRECT_ERROR = "redirect:/error";

    @Autowired
    private GPService gpService;

    @Autowired
    private TeamService teamService;

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

    // @PostMapping("/grand-prix/simulating")
    // public String simulateGP(@Valid @ModelAttribute GP gp) {
    @GetMapping("/grand-prix/simulating")
    public String simulateGP() {
        List<Team> teams = teamService.getRandomTeams(GP.NB_TEAMS_IN_GP);

        if (teams != null) {
            gpService.simulateGP(teams);

            return "grand-prix";
        }

        System.out.println("Unable to simulate grand prix. Please sign in.");
        return REDIRECT_ERROR;
    }

}
