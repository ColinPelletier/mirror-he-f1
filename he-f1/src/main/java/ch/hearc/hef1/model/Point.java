package ch.hearc.hef1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "points")
public class Point {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	@Column
	private int position;

	@Column
	private int nbPoints;

	@Column
	private int money;

	/**
	 * Default constructor
	 */
	public Point() {
		// empty
	}

	/**
	 * @param id
	 * @param position
	 * @param nbPoints
	 * @param money
	 */
	public Point(int id, int position, int nbPoints, int money) {
		this.id = id;
		this.position = position;
		this.nbPoints = nbPoints;
		this.money = money;
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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getNbPoints() {
		return nbPoints;
	}

	public void setNbPoints(int nbPoints) {
		this.nbPoints = nbPoints;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
