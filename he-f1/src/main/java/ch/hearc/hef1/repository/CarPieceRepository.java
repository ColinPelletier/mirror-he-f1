package ch.hearc.hef1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.CarPiece;

@Repository("CarPieceRepository")
public interface CarPieceRepository extends JpaRepository<CarPiece, Long> {

	List<CarPiece> findByCar(Car car);
}
