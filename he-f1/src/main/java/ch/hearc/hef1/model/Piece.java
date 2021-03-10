package ch.hearc.hef1.model;

public class Piece {

	private int id;

	//fk category
	private int categoryId;
	
	private String nom;
	
	private double baseRepairPrice;
	
	private double baseRepairTime;
	
	private double baseUpgradePrice;
	
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
	 * @param nom
	 * @param baseRepairPrice
	 * @param baseRepairTime
	 * @param baseUpgradePrice
	 * @param baseUpgradeTime
	 */
	public Piece(int id, int categoryId, String nom, double baseRepairPrice, double baseRepairTime,
			double baseUpgradePrice, double baseUpgradeTime) {
		this.id = id;
		this.categoryId = categoryId;
		this.nom = nom;
		this.baseRepairPrice = baseRepairPrice;
		this.baseRepairTime = baseRepairTime;
		this.baseUpgradePrice = baseUpgradePrice;
		this.baseUpgradeTime = baseUpgradeTime;
	}
	
	
	/*
	 * Getters and Setters 
	 */
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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
