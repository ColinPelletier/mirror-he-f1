package ch.hearc.hef1.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.GP;
import ch.hearc.hef1.model.Point;
import ch.hearc.hef1.model.Team;
import ch.hearc.hef1.repository.GPRepository;
import ch.hearc.hef1.repository.TeamRepository;
import ch.hearc.hef1.tools.MapUtil;

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
        return gpRepository.findAll();
    }

    public void simulateGP(List<Team> teams) {
        Map<Car, Float> carsEfficiency = new HashMap<Car, Float>();
        Random rnd = new Random();

        for (Team team : teams) {
            for (Car car : team.getCars()) {
                if (DNF_RATE > rnd.nextInt(100)) {
                    // DNF = Do Not Finish
                    carsEfficiency.put(car, 0f);
                } else {
                    carsEfficiency.put(car, carService.computeCarEfficiency(car));
                }
            }
        }

        var points = pointService.getPoints(); // points sorted by position

        Function<Car, Float> carEfficiency = c => rnd.nextInt(100) < DNF_RATE ? 0 : carService.computeEfficiency(c);

        List<Car> cars = carsEfficiency.stream().sorted(Comparator.compare(carEfficiency, Float::compare)).collect(Collectors.toList());

        int position = cars.size();

        foreach(Cars car : cars)
        {
            Point point = position.get(position - 1);
            car.getTeam().addPoints(point.getPoints);
            car.getTeam().addMoney(point.geetMoney);
        }

        // MapUtil.sortByValue(carsEfficiency);
        // int index = carsEfficiency.size();

        // for (Map.Entry<Car, Float> entry : carsEfficiency.entrySet()) {

        //     if (index <= 10) {
        //         Team team = entry.getKey().getTeam();
        //         Point point = pointService.getByPosition(index);
        //         team.addPoints(point.getNbPoints());
        //         team.addMoney(point.getMoney());

        //         teamRepository.save(team);
        //     }

        //     index--;
        // }
    }
}
