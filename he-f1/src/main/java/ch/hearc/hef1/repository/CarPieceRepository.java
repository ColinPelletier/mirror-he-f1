package ch.hearc.hef1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.CarPiece;
import ch.hearc.hef1.model.Piece;

@Repository("CarPieceRepository")
public interface CarPieceRepository extends JpaRepository<CarPiece, Long> {
    // Find car pieces
    public List<CarPiece> findByCar(Car car);

    // Find a specific piece for a car
    public CarPiece findByCarAndPiece(Car car, Piece piece);
}
