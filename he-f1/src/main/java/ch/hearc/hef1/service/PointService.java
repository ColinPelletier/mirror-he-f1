package ch.hearc.hef1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.hef1.model.Point;
import ch.hearc.hef1.repository.PointRepository;

@Service("pointService")
public class PointService {

    @Autowired
    PointRepository pointRepository;

    public List<Point> getPoints() {
        return pointRepository.findAll();
    }

    public Point getByPosition(int position) {
        return pointRepository.findByPosition(position);
    }
}
