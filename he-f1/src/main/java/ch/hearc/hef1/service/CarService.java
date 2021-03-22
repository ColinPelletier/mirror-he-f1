package ch.hearc.hef1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.repository.CarPieceRepository;

@Service("carService")
public class CarService {
	
	@Autowired
	private CarPieceRepository carPieceRepository;
	
	public List<CarPieces> findPiecesByCar(Car car)
	{
		return carPieceRepository.findByCar(car);
	}
}
