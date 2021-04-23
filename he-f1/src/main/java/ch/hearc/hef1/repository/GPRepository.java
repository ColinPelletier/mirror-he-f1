package ch.hearc.hef1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.GP;

@Repository("GPRepository")
public interface GPRepository extends JpaRepository<GP, Long> {

}
