package ch.hearc.hef1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.CarPiece;
import ch.hearc.hef1.model.Piece;
import ch.hearc.hef1.repository.CarPieceRepository;

@Service("carService")
public class CarService {

    @Autowired
    CarPieceRepository carPieceRepository;

    public List<CarPiece> findCarPieces(Car car) {
        return carPieceRepository.findByCar(car);
    }

    public CarPiece findCarPiece(Car car, Piece piece) {
        return carPieceRepository.findByCarAndPiece(car, piece);
    }
}
