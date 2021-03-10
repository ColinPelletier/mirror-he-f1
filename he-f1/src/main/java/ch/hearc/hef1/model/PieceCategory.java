package ch.hearc.hef1.model;

public class PieceCategory {
	
	private int id;
	
	private String nom;
	
	/**
	 * Default constructor
	 */
	public PieceCategory() {
		// empty
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param nom
	 */
	public PieceCategory(int id, String nom) {
		this.id = id;
		this.nom = nom;
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
}
