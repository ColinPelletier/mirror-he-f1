package ch.hearc.hef1.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.CarPiece;
import ch.hearc.hef1.model.Piece;
import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.repository.CarPieceRepository;
import ch.hearc.hef1.repository.CarRepository;

@Service("carService")
public class CarService {

    @Autowired
    CarPieceRepository carPieceRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    PieceService pieceService;

    public List<CarPiece> findCarPieces(Car car) {
        return carPieceRepository.findByCar(car);
    }

    public CarPiece findCarPiece(Car car, Piece piece) {
        return carPieceRepository.findByCarAndPiece(car, piece);
    }

    public boolean isTeamOwner(Car car, Team team) {
        return team.equals(car.getTeam());
    }

    public List<Car> createAndSaveTeamCars(Team team, String carName) {
        List<Car> carList = new LinkedList<Car>();

        for (int i = 0; i < Team.NB_CARS_BY_TEAM; ++i) {
            Car car = new Car();
            car.setName(carName);
            car.setTeam(team);
            carList.add(car);
        }

        carList = carRepository.saveAll(carList);
        pieceService.createAndSaveCarPieces(carList);

        return carList;
    }
}
