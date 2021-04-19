package ch.hearc.hef1.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.CarPiece;
import ch.hearc.hef1.repository.CarRepository;
import ch.hearc.hef1.service.CarService;

@Controller
public class CarController {

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

    @PostMapping("/car/update/{strPieceId}")
    public String updatePiece(@PathVariable String strPieceId, Map<String, Object> model) {
        long carId = Long.parseLong(strPieceId);
        System.out.println("piece id = " + carId);
        // model.put("title", homeTitle);

        // // Put the todo list by the given author
        // model.put("todos", todo.getAllTodosByAuthor(strAuthor));
        // model.put("author", strAuthor);
        // model.put("totalTime", todo.getTotalTimeByAuthor(strAuthor));

        // // Return the page "home.html"
        // return "todoByAuthor";
        return ("redirect:/recruiting?recruited=true");
    }
}
