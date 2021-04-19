package ch.hearc.hef1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pieces")
public class Piece {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	@ManyToOne
	// @JoinColumn(name = "piece_category")
	private PieceCategory pieceCategory;

	@Column
	private String name;

	@Column
	private double baseRepairPrice;

	@Column
	private double baseRepairTime;

	@Column
	private double baseUpgradePrice;

	@Column
	private double baseUpgradeTime;

	/**
	 * Default constructor
	 */
	public Piece() {
		// empty
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param categoryId
	 * @param name
	 * @param baseRepairPrice
	 * @param baseRepairTime
	 * @param baseUpgradePrice
	 * @param baseUpgradeTime
	 */
	public Piece(int id, PieceCategory pieceCategory, String name, double baseRepairPrice, double baseRepairTime,
			double baseUpgradePrice, double baseUpgradeTime) {
		this(pieceCategory, name, baseRepairPrice, baseRepairTime, baseUpgradePrice, baseUpgradeTime);
		this.id = id;
	}

	/**
	 * Constuctor for Piece objects created directly in the backend and not get from
	 * the DB
	 * 
	 * @param pieceCategory
	 * @param name
	 * @param baseRepairPrice
	 * @param baseRepairTime
	 * @param baseUpgradePrice
	 * @param baseUpgradeTime
	 */
	public Piece(PieceCategory pieceCategory, String name, double baseRepairPrice, double baseRepairTime,
			double baseUpgradePrice, double baseUpgradeTime) {
		this.pieceCategory = pieceCategory;
		this.name = name;
		this.baseRepairPrice = baseRepairPrice;
		this.baseRepairTime = baseRepairTime;
		this.baseUpgradePrice = baseUpgradePrice;
		this.baseUpgradeTime = baseUpgradeTime;
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

	public PieceCategory getCategoryId() {
		return pieceCategory;
	}

	public void setCategoryId(PieceCategory pieceCategory) {
		this.pieceCategory = pieceCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBaseRepairPrice() {
		return baseRepairPrice;
	}

	public void setBaseRepairPrice(double baseRepairPrice) {
		this.baseRepairPrice = baseRepairPrice;
	}

	public double getBaseRepairTime() {
		return baseRepairTime;
	}

	public void setBaseRepairTime(double baseRepairTime) {
		this.baseRepairTime = baseRepairTime;
	}

	public double getBaseUpgradePrice() {
		return baseUpgradePrice;
	}

	public void setBaseUpgradePrice(double baseUpgradePrice) {
		this.baseUpgradePrice = baseUpgradePrice;
	}

	public double getBaseUpgradeTime() {
		return baseUpgradeTime;
	}

	public void setBaseUpgradeTime(double baseUpgradeTime) {
		this.baseUpgradeTime = baseUpgradeTime;
	}

}
