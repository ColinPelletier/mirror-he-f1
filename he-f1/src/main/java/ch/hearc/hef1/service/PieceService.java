package ch.hearc.hef1.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.hearc.hef1.model.Car;
import ch.hearc.hef1.model.CarPiece;
import ch.hearc.hef1.model.Piece;
import ch.hearc.hef1.model.PieceCategory;
import ch.hearc.hef1.repository.CarPieceRepository;
import ch.hearc.hef1.repository.PieceRepository;

@Service("pieceService")
public class PieceService {

    @Autowired
    PieceRepository pieceRepository;

    @Autowired
    CarPieceRepository carPieceRepository;

    /**
     * Create all pieces for a new car
     * 
     * @param carList
     */
    public void createAndSaveCarPieces(List<Car> carList) {
        List<CarPiece> carPieceList = new LinkedList<CarPiece>();

        for (Car car : carList) {
            for (Piece piece : this.createAndSaveBasePieceList()) {
                carPieceList.add(new CarPiece(piece, car));
            }
        }

        carPieceRepository.saveAll(carPieceList);
    }

    private List<Piece> createAndSaveBasePieceList() {
        List<Piece> pieceList = new LinkedList<Piece>();

        pieceList.add(new Piece(PieceCategory.AERODYNAMICS, "Front wing", 50000, 2, 50000, 5));
        pieceList.add(new Piece(PieceCategory.AERODYNAMICS, "Rear wing", 60000, 2, 50000, 5));
        pieceList.add(new Piece(PieceCategory.AERODYNAMICS, "Bargeboard", 50000, 3, 100000, 10));
        pieceList.add(new Piece(PieceCategory.ENINGE, "MGU-H", 20000, 5, 1200000, 10));
        pieceList.add(new Piece(PieceCategory.ENINGE, "MGU-K", 20000, 5, 1200000, 10));
        pieceList.add(new Piece(PieceCategory.SENSORS, "Tyre heat sensors", 5000, 1, 10000, 5));
        pieceList.add(new Piece(PieceCategory.COOLING, "Oil System", 5000, 1, 100000, 2));

        return pieceRepository.saveAll(pieceList);
    }

}
