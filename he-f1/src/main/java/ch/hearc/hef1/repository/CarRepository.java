package ch.hearc.hef1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.Team;

@Repository("CarRepository")
public interface CarRepository extends JpaRepository<Car, Long> {

    public List<Car> findByTeam(Team team);
    // Returns no car if the team doesn't own this car
    // public Optional<Car> findByTeam(Team team);
}
