package ch.hearc.hef1.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/car/pieces")
    public String carPieces(Map<String, Object> model) {
        Car car = carRepository.findById((long) 1).get();
        List<CarPiece> carPieces = carService.findCarPieces(car);

        model.put("model", car);
        model.put("carPieces", carPieces);

        return "test-display";
    }
}
