package ch.hearc.hef1.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.GP;
import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.model.TeamGP;
import ch.hearc.hef1.model.User;
import ch.hearc.hef1.repository.CarRepository;
import ch.hearc.hef1.repository.TeamGPRepository;
import ch.hearc.hef1.repository.TeamRepository;
import ch.hearc.hef1.tools.FileUploadUtil;

@Service("teamService")
public class TeamService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamGPRepository teamGPRepository;

    @Autowired
    UserService userService;

    /**
     * Upload car image in folder "teams-car-picture" And assign filename to team
     * 
     * @param team
     * @param multipartFile
     * @return
     */
    public boolean uploadCarImage(Team team, MultipartFile multipartFile) {
        String uploadDir = "teams-car-picture/" + team.getId();
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        team.setCarPicture(fileName);

        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            return true;
        } catch (IOException ioe) {
            System.out.println("Unable to save file...");
            System.out.println(ioe);
        }
        return false;
    }

    /**
     * Create urls to switch between team cars. The carId attributes are used to
     * know on which page the user is : the first or the second car of the team.
     * 
     * @param team
     * @param model
     */
    public void setCarsUrlsInModel(Team team, Map<String, Object> model) {
        List<Car> teamCars = carRepository.findByTeam(team);

        model.put("carId1", teamCars.get(0).getId());
        model.put("carUrl1", "/team/" + team.getId() + "/car/" + teamCars.get(0).getId());

        model.put("carId2", teamCars.get(1).getId());
        model.put("carUrl2", "/team/" + team.getId() + "/car/" + teamCars.get(1).getId());
    }

    public List<Team> getRandomTeams(int nbTeams) {
        User authenticatedUser = userService.getAuthenticatedUser();

        if (userService.isAuthenticated(authenticatedUser)) {

            Team userTeam = authenticatedUser.getTeam();
            List<Team> randomTeams = teamRepository.findRandom(nbTeams - 1, userTeam.getId());

            randomTeams.add(userTeam);

            return randomTeams;
        }

        return null;
    }

    public boolean checkParticipation(GP gp) {
        User authenticatedUser = userService.getAuthenticatedUser();

        if (userService.isAuthenticated(authenticatedUser)) {

            Team userTeam = authenticatedUser.getTeam();
            List<TeamGP> teamGPs = teamGPRepository.findByTeam(userTeam);

            if (gp.getDate().after(new Date())) {
                if (!containsTeamGP(teamGPs, gp.getId(), userTeam.getId())) {
                    TeamGP teamGP = new TeamGP(userTeam, gp);
                    teamGPRepository.save(teamGP);
                    return false;
                }
            }
        }

        return true;
    }

    private boolean containsTeamGP(final List<TeamGP> list, final long gpId, final long teamId) {
        return list.stream().filter(o -> o.getGP().getId() == gpId && o.getTeam().getId() == teamId).findFirst()
                .isPresent();
    }

}
