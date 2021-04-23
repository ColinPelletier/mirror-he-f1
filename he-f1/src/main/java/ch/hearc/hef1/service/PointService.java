package ch.hearc.hef1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.hef1.model.Point;
import ch.hearc.hef1.repository.PointRepository;

@Service("pointService")
public class PointService {

    @Autowired
    PointRepository pointRepository;

    /**
     * Get all Points order by position
     * 
     * @return
     */
    public List<Point> getPoints() {
        return pointRepository.findAll()//
                .stream() //
                .sorted((p1, p2) -> Integer.compare(p1.getPosition(), p2.getPosition()))//
                .collect(Collectors.toList());
    }

    public Point getByPosition(int position) {
        return pointRepository.findByPosition(position);
    }
}
