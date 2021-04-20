package ch.hearc.hef1.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import ch.hearc.hef1.model.Piece;
import ch.hearc.hef1.model.RepairUpgrade;
import ch.hearc.hef1.model.User;
import ch.hearc.hef1.repository.CarPieceRepository;
import ch.hearc.hef1.repository.CarRepository;
import ch.hearc.hef1.repository.PieceRepository;
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

    @PostMapping("/car/upgrade/{strPieceId}/{strTeamId}/{strCarId}")
    public String upgradePiece(@PathVariable String strPieceId, @PathVariable String strTeamId,
            @PathVariable String strCarId, Map<String, Object> model) {

        long pieceId;
        int teamId;
        int carId;
        // float upgradePrice = 0;

        Date startDate = new Date();
        Date endDate = new Date(startDate.getMinutes() * ONE_MINUTE + ONE_MINUTE);

        try {
            pieceId = Long.parseLong(strPieceId);
            teamId = Integer.parseInt(strTeamId);
            carId = Integer.parseInt(strCarId);
        } catch (NumberFormatException e) {
            System.err.println("id must be an integer");
            return REDIRECT_ERROR;
        }
        // Get car Piece
        Optional<CarPiece> carPiece = carPieceRepository.findById(pieceId);

        // Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userService.findUserByUsername(auth.getName());
        System.out.println("=========================================================================== 1");
        System.out.println("pieceId = " + pieceId);
        System.out.println("teamId = " + teamId);
        System.out.println("carId = " + carId);
        System.out.println("carPiece = " + carPiece.isPresent());
        // Create and save repairUpgrade
        if (carPiece.isPresent()) {
            // Get price
            // Piece piece = pieceRepository.
            System.out.println("=========================================================================== 2");
            RepairUpgrade repairUpgrade = new RepairUpgrade(carPiece.get(), authenticatedUser, false, startDate,
                    endDate);
            repairUpgradeService.saveRepairUpgrade(repairUpgrade);
        }

        // CarPiece carPiece, User user, boolean isRepair, Date startDate, Date endDate

        // Team createdTeam = teamRepository.save(team);

        // model.put("title", homeTitle);
        // // Put the todo list by the given author
        // model.put("todos", todo.getAllTodosByAuthor(strAuthor));
        // model.put("author", strAuthor);
        // model.put("totalTime", todo.getTotalTimeByAuthor(strAuthor));

        // // Return the page "home.html"
        // return "todoByAuthor";
        return ("redirect:/team/" + teamId + "/car/" + carId);
    }
}
