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
@Table(name = "teams")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	@Column
	private String name;

	@Column
	private double budget;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
	private List<Car> cars;

	/**
	 * Default constructor
	 */
	public Team() {
		// empty
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param name
	 * @param budget
	 */
	public Team(int id, String name, double budget) {
		this.id = id;
		this.name = name;
		this.budget = budget;
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

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public List<Car> getCars() {
		return cars;
	}
}
