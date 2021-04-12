package ch.hearc.hef1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.Team;

@Repository("TeamRepository")
public interface TeamRepository extends JpaRepository<Team, Long> {
    // Find team by name
    public Team findByName(String name);
}
