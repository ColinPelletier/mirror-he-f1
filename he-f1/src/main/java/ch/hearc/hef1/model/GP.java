package ch.hearc.hef1.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "grand_prix")
public class GP {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	@Column
	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column
	private Date date;

	@Column
	private String country;

	/**
	 * Default constructor
	 */
	public GP() {
		// empty
	}

	/**
	 * @param id
	 * @param name
	 * @param date
	 * @param country
	 */
	public GP(int id, String name, Date date, String country) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.country = country;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
