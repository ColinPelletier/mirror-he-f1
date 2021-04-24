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
	public static long STARTING_BUDGET = 1000000;
	public static int STARTING_POINTS = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	@Column
	private String name;

	@Column
	private String driver1; // driver 1 name

	@Column
	private String driver2; // driver 2 name

	@Column
	private long budget;

	@Column
	private int points;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
	private List<Car> cars;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
	private List<Notification> notifications;

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
	 * @param driver1
	 * @param driver2
	 * @param budget
	 * @param carPicture
	 * @param points
	 */
	public Team(int id, String name, String driver1, String driver2, long budget, String carPicture, int points) {
		this.id = id;
		this.name = name;
		this.driver1 = driver1;
		this.driver2 = driver2;
		this.budget = budget;
		this.carPicture = carPicture;
		this.points = points;
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

	public String getDriver1() {
		return driver1;
	}

	public void setDriver1(String driver1) {
		this.driver1 = driver1;
	}

	public String getDriver2() {
		return driver2;
	}

	public void setDriver2(String driver2) {
		this.driver2 = driver2;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void addPoints(int points) {
		this.points += points;
	}

	public long getBudget() {
		return budget;
	}

	public void setBudget(long budget) {
		this.budget = budget;
	}

	public void addMoney(long money) {
		this.budget += money;
	}

	public List<Car> getCars() {
		return cars;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public String getCarPicture() {
		return carPicture;
	}

	public void setCarPicture(String carPicture) {
		this.carPicture = carPicture;
	}

	public String getCarImagePath() {
		if (carPicture != null) {
			return "/teams-car-picture/" + id + "/" + carPicture;
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		Team team = (Team) obj;
		return this.id == team.getId() && this.name.equals(team.getName());
	}
}
