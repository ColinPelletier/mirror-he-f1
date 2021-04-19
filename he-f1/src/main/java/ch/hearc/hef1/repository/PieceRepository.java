package ch.hearc.hef1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.Piece;

@Repository("PieceRepository")
public interface PieceRepository extends JpaRepository<Piece, Long> {
    // empty
}
