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
@Table(name = "piece_category")
public class PieceCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	@Column
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pieceCategory")
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
	 * @param name
	 */
	public PieceCategory(int id, String name) {
		this.id = id;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Piece> getPieces() {
		return this.pieces;
	}
}
