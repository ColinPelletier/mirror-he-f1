package ch.hearc.hef1.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "car_pieces")
public class CarPiece {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	@ManyToOne
	@JoinColumn(name = "piece_id")
	private Piece piece;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "repair_upgrade_id")
	private RepairUpgrade repairUpgrade;

	@Column
	private int wear;

	@Column
	private int level;

	/**
	 * Default constructor
	 */
	public CarPiece() {
		// empty
	}

	/**
	 * @param id
	 * @param piece
	 * @param car
	 * @param repairUpgrade
	 * @param wear
	 * @param level
	 */
	public CarPiece(int id, Piece piece, Car car, RepairUpgrade repairUpgrade, int wear, int level) {
		this.id = id;
		this.piece = piece;
		this.car = car;
		this.repairUpgrade = repairUpgrade;
		this.wear = wear;
		this.level = level;
	}

	/**
	 * Constructor for a CarPiece created directly from the backend, without id
	 * value
	 * 
	 * @param piece
	 * @param car
	 * @param repairUpgrade
	 * @param wear
	 * @param level
	 */
	public CarPiece(Piece piece, Car car, RepairUpgrade repairUpgrade, int wear, int level) {
		this.piece = piece;
		this.car = car;
		this.repairUpgrade = repairUpgrade;
		this.wear = wear;
		this.level = level;
	}

	/**
	 * Constructor for CarPiece for a new car piece with default values
	 * 
	 * @param piece
	 * @param car
	 */
	public CarPiece(Piece piece, Car car) {
		this(piece, car, null, 0, 1);
	}

	/*
	 * Getters and Setters
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public RepairUpgrade getRepairUpgrade() {
		return repairUpgrade;
	}

	public void setRepairUpgrade(RepairUpgrade repairUpgrade) {
		this.repairUpgrade = repairUpgrade;
	}

	public double getWear() {
		return wear;
	}

	public void setWear(int wear) {
		this.wear = wear;
	}

	public double getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
