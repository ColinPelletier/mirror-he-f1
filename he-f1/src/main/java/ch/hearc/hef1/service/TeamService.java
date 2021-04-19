package ch.hearc.hef1.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.tools.FileUploadUtil;

@Service("teamService")
public class TeamService {
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

}
