package ch.hearc.hef1.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.CarPiece;
import ch.hearc.hef1.model.RepairUpgrade;
import ch.hearc.hef1.repository.CarPieceRepository;
import ch.hearc.hef1.repository.CarRepository;
import ch.hearc.hef1.service.CarService;

@Controller
public class CarController {
    private static final String REDIRECT_ERROR = "redirect:/error";

    @Autowired
    CarPieceRepository carPieceRepository;

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

    @PostMapping("/car/update/{strPieceId}/{strTeamId}/{strCarId}")
    public String updatePiece(@PathVariable String strPieceId, @PathVariable String strTeamId,
            @PathVariable String strCarId, Map<String, Object> model) {

        long pieceId;
        int teamId;
        int carId;

        try {
            pieceId = Long.parseLong(strPieceId);
            teamId = Integer.parseInt(strTeamId);
            carId = Integer.parseInt(strCarId);
        } catch (NumberFormatException e) {
            System.err.println("id must be an integer");
            return REDIRECT_ERROR;
        }

        Optional<CarPiece> carPiece = carPieceRepository.findById(pieceId);
        // RepairUpgrade repairUpgrade = new RepairUpgrade(carPiece, );
        // CarPiece carPiece, User user, boolean isRepair, Date startDate, Date endDate

        System.out.println("id piece = " + pieceId);

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
