package ch.hearc.hef1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.hearc.hef1.model.Point;

@Repository("pointRepository")
public interface PointRepository extends JpaRepository<Point, Long> {
    public List<Point> findAll();

    public Point findByPosition(int position);
}
