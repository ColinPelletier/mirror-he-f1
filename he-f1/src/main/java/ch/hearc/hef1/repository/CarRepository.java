package ch.hearc.hef1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.Car;

@Repository("CarRepository")
public interface CarRepository extends JpaRepository<Car, Long> {
    // TODO
}
