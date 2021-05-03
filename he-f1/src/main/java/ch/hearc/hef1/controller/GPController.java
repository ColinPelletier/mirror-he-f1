package ch.hearc.hef1.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/grand-prix", method = RequestMethod.GET)
    public String listGPs(Model model, @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam(value = "selectedName", required = false) String strName) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        Page<GP> gpPage = gpService.findPaginated(PageRequest.of(currentPage - 1, pageSize), strName);

        model.addAttribute("gps", gpPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("search", strName);

        int totalPages = gpPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "grand-prix";
    }

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
