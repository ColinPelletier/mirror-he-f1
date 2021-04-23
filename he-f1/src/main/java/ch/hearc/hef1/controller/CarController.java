package ch.hearc.hef1.controller;

import java.lang.StackWalker.Option;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.CarPiece;
import ch.hearc.hef1.model.RepairUpgrade;
import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.model.User;
import ch.hearc.hef1.repository.CarPieceRepository;
import ch.hearc.hef1.repository.CarRepository;
import ch.hearc.hef1.repository.PieceRepository;
import ch.hearc.hef1.repository.TeamRepository;
import ch.hearc.hef1.service.CarService;
import ch.hearc.hef1.service.RepairUpgradeService;
import ch.hearc.hef1.service.UserService;

@Controller
public class CarController {
    private static final String REDIRECT_ERROR = "redirect:/error";
    static final long ONE_MINUTE = 60000;

    @Autowired
    CarPieceRepository carPieceRepository;

    @Autowired
    PieceRepository pieceRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserService userService;

    @Autowired
    RepairUpgradeService repairUpgradeService;

    @Autowired
    CarService carService;

    @Autowired
    CarRepository carRepository;

    @GetMapping("/car/{strCarId}/pieces")
    public String carPieces(@PathVariable String strCarId, Map<String, Object> model) {
        // TODO check access
        long carId = Long.parseLong(strCarId);
        Car car = carRepository.findById(carId).get();

        List<CarPiece> carPieces = carService.findCarPieces(car);

        model.put("model", car);
        model.put("carPieces", carPieces);

        return "test-display";
    }

    @PostMapping("/car/repair/{strPieceId}/{strTeamId}/{strCarId}")
    public String repairPiece(@PathVariable String strPieceId, @PathVariable String strTeamId,
            @PathVariable String strCarId, Map<String, Object> model) {

        long pieceId;
        long teamId;
        long carId;
        long repairPrice = 0;

        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();

        Date startDate = new Date();

        try {
            pieceId = Long.parseLong(strPieceId);
            teamId = Long.parseLong(strTeamId);
            carId = Long.parseLong(strCarId);
        } catch (NumberFormatException e) {
            System.err.println("id must be an integer");
            return REDIRECT_ERROR;
        }
        // Get car Piece
        Optional<CarPiece> carPiece = carPieceRepository.findById(pieceId);
        Optional<Team> team = teamRepository.findById((long) teamId);

        // Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userService.findUserByUsername(auth.getName());

        // Create and save repairUpgrade
        if (carPiece.isPresent() && team.isPresent()) {
            // Get price
            repairPrice = (long) (carPiece.get().getPiece().getBaseRepairPrice() * carPiece.get().getLevel());

            // Get end date
            double timeInHour = carPiece.get().getPiece().getBaseRepairTime() * carPiece.get().getLevel();
            Date endDate = new Date(t + (int) (timeInHour * ONE_MINUTE * 60));

            // If carPiece is weared and team have enought budget, repair the carPiece
            if (carPiece.get().getWear() > 0 && team.get().getBudget() - repairPrice > 0) {
                RepairUpgrade repairUpgrade = new RepairUpgrade(carPiece.get(), authenticatedUser, true, startDate,
                        endDate);
                team.get().setBudget(team.get().getBudget() - repairPrice);
                teamRepository.save(team.get());
                repairUpgradeService.saveRepairUpgrade(repairUpgrade);
            }
        }
        return ("redirect:/team/" + teamId + "/car/" + carId);
    }

    @PostMapping("/car/upgrade/{strPieceId}/{strTeamId}/{strCarId}")
    public String upgradePiece(@PathVariable String strPieceId, @PathVariable String strTeamId,
            @PathVariable String strCarId, Map<String, Object> model) {

        long pieceId;
        long teamId;
        long carId;
        long upgradePrice = 0;

        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();

        Date startDate = new Date();

        try {
            pieceId = Long.parseLong(strPieceId);
            teamId = Long.parseLong(strTeamId);
            carId = Long.parseLong(strCarId);
        } catch (NumberFormatException e) {
            System.err.println("id must be an integer");
            return REDIRECT_ERROR;
        }
        // Get car Piece
        Optional<CarPiece> carPiece = carPieceRepository.findById(pieceId);
        Optional<Team> team = teamRepository.findById((long) teamId);

        // Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userService.findUserByUsername(auth.getName());

        // Create and save repairUpgrade
        if (carPiece.isPresent() && team.isPresent()) {
            // Get price
            upgradePrice = (long) (carPiece.get().getPiece().getBaseUpgradePrice() * carPiece.get().getLevel());

            // Get end date
            double timeInHour = carPiece.get().getPiece().getBaseUpgradeTime() * carPiece.get().getLevel();
            Date endDate = new Date(t + (int) (timeInHour * ONE_MINUTE * 60));

            // If team have enought budget, upgrade the carpiece
            if (team.get().getBudget() - upgradePrice > 0) {
                RepairUpgrade repairUpgrade = new RepairUpgrade(carPiece.get(), authenticatedUser, false, startDate,
                        endDate);
                team.get().setBudget(team.get().getBudget() - upgradePrice);
                teamRepository.save(team.get());
                repairUpgradeService.saveRepairUpgrade(repairUpgrade);
            }
        }
        return ("redirect:/team/" + teamId + "/car/" + carId);
    }
}
