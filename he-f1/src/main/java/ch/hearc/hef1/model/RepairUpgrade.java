package ch.hearc.hef1.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "repair_upgrades")
public class RepairUpgrade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	@OneToOne()
	@JoinColumn(name = "car_piece_id")
	private CarPiece carPiece;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column
	private boolean isRepair; // true : repair, false : upgrade

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column
	private Date startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column
	private Date endDate;

	/**
	 * Default constructor
	 */
	public RepairUpgrade() {
		// empty
	}

	/**
	 * @param id
	 * @param carPiece
	 * @param user
	 * @param isRepair
	 * @param startDate
	 * @param endDate
	 */
	public RepairUpgrade(int id, CarPiece carPiece, User user, boolean isRepair, Date startDate, Date endDate) {
		this.id = id;
		this.carPiece = carPiece;
		this.user = user;
		this.isRepair = isRepair;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public RepairUpgrade(CarPiece carPiece, User user, boolean isRepair, Date startDate, Date endDate) {
		this.carPiece = carPiece;
		this.user = user;
		this.isRepair = isRepair;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public CarPiece getCarPiece() {
		return carPiece;
	}

	public void setCarPiece(CarPiece carPiece) {
		this.carPiece = carPiece;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isRepair() {
		return isRepair;
	}

	public void setRepair(boolean isRepair) {
		this.isRepair = isRepair;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
