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
	public static int NB_CARS_BY_TEAM = 2;

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

	@Column(nullable = true, length = 64)
	private String carPicture;

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
	 * @param carPicture
	 */
	public Team(int id, String name, double budget, String carPicture) {
		this.id = id;
		this.name = name;
		this.budget = budget;
		this.carPicture = carPicture;
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

	public String getCarPicture() {
		return carPicture;
	}

	public String getCarImagePath() {
		if (carPicture != null) {
			return "/teams-car-picture/" + id + "/" + carPicture;
		}
		return null;
	}

	public void setCarPicture(String carPicture) {
		this.carPicture = carPicture;
	}

	@Override
	public boolean equals(Object obj) {
		Team team = (Team) obj;
		return this.id == team.id && this.name.equals(team.name);
	}

	public boolean validate() {
		return (!this.name.isEmpty());
	}

}
