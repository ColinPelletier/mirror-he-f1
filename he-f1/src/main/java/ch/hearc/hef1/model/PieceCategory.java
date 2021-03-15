package ch.hearc.hef1.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="piece_category")
public class PieceCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	
	@Column
	private String nom;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="pieceCategory")
	private List<Piece> pieces;
	
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
	
	public List<Piece> getPieces()
	{
		return this.pieces;
	}
}
