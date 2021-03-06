package ch.hearc.hef1.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.GP;
import ch.hearc.hef1.model.Point;
import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.repository.GPRepository;
import ch.hearc.hef1.repository.TeamRepository;

@Service("gpService")
public class GPService {

    @Autowired
    GPRepository gpRepository;

    @Autowired
    CarService carService;

    @Autowired
    PointService pointService;

    @Autowired
    TeamRepository teamRepository;

    private static int DNF_RATE = 10;

    public List<GP> findAll() {
        return gpRepository.findAllByOrderByDateAsc();
    }

    public List<GP> findByNameContaining(String name) {
        return gpRepository.findByNameContaining(name);
    }

    public Page<GP> findPaginated(Pageable pageable, String strName) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<GP> gps;

        if (strName != null) {
            gps = findByNameContaining(strName);
        } else {
            gps = findAll();
        }

        List<GP> list;

        if (gps.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, gps.size());
            list = gps.subList(startItem, toIndex);
        }

        Page<GP> gpsPage = new PageImpl<GP>(list, PageRequest.of(currentPage, pageSize), gps.size());

        return gpsPage;
    }

    /**
     * Simulate a GP within all teams. - Compute car efficiency - Add random
     * penalities to add more suspense - Attribute points and rewards to teams
     * according to their cars results
     * 
     * @param teams
     */
    public void simulateGP(List<Team> teams) {
        Random rnd = new Random();

        List<Car> cars = teams.stream().flatMap(t -> t.getCars().stream()).collect(Collectors.toList());

        List<Point> points = pointService.getPoints();

        // add a random DNF penalty (DNF = Do Not Finish = so 0 efficiency)
        Function<Car, Float> carEfficiency = car -> rnd.nextInt(100) < DNF_RATE ? 0f
                : carService.computeCarEfficiency(car);

        // sort cars by efficiency.
        cars = cars.stream().sorted(Comparator.comparing(carEfficiency, Float::compare)).collect(Collectors.toList());

        int position = cars.size();

        for (Car car : cars) {
            Point point = points.get(position - 1);
            Team team = car.getTeam();

            team.addPoints(point.getNbPoints());
            team.addMoney(point.getMoney());

            teamRepository.save(team);
            --position;
        }
    }
}
