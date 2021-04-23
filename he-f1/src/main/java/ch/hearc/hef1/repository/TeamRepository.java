package ch.hearc.hef1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ch.hearc.hef1.model.Team;

@Repository("TeamRepository")
public interface TeamRepository extends JpaRepository<Team, Long> {
    // Find team by name
    public Team findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM team ORDER BY RAND() LIMIT :nbTeams", nativeQuery = true)
    public List<Team> findRandom(@Param("nbTeams") int nbTeams);

}
