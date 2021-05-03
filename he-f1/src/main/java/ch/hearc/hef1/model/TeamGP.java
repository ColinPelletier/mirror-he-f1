package ch.hearc.hef1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "team_gps")
public class TeamGP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "gp_id")
    private GP gp;

    /**
     * Default constructor
     */
    public TeamGP() {
        // empty
    }

    /**
     * @param id
     * @param team
     * @param gp
     */
    public TeamGP(int id, Team team, GP gp) {
        this.id = id;
        this.team = team;
        this.gp = gp;
    }

    /**
     * @param team
     * @param gp
     */
    public TeamGP(Team team, GP gp) {
        this.team = team;
        this.gp = gp;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public GP getGP() {
        return gp;
    }

    public void setGP(GP gp) {
        this.gp = gp;
    }
}
