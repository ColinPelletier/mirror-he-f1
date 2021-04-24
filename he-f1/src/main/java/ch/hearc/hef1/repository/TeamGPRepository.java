package ch.hearc.hef1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.model.TeamGP;

@Repository("TeamGPRepository")
public interface TeamGPRepository extends JpaRepository<TeamGP, Long> {

    public List<TeamGP> findByTeam(Team team);

}
