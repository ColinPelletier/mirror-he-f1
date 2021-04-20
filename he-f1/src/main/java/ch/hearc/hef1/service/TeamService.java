package ch.hearc.hef1.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.repository.CarRepository;
import ch.hearc.hef1.tools.FileUploadUtil;

@Service("teamService")
public class TeamService {

    @Autowired
    CarRepository carRepository;

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

    public void setCarsUrlsInModel(Team team, Map<String, Object> model) {
        List<Car> teamCars = carRepository.findByTeam(team);

        model.put("carId1", teamCars.get(0).getId());
        model.put("carUrl1", "/team/" + team.getId() + "/car/" + teamCars.get(0).getId());

        model.put("carId2", teamCars.get(1).getId());
        model.put("carUrl2", "/team/" + team.getId() + "/car/" + teamCars.get(1).getId());
    }

}
