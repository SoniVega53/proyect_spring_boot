package com.example.demo.model;

import com.example.demo.model.journey.JourneyModel;
import jakarta.persistence.*;


@Entity
@Table(name = "reservations")
public class ReservationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "count_people")
    private int countPeople;
    @Column(name = "observations")
    private String observations;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserModel user;
    @ManyToOne
    @JoinColumn(name = "journey_id",referencedColumnName = "id")
    private JourneyModel journey;

    public ReservationModel() {
    }

    public ReservationModel(int countPeople, String observations, String status, UserModel user, JourneyModel journey) {
        this.countPeople = countPeople;
        this.observations = observations;
        this.status = status;
        this.user = user;
        this.journey = journey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCountPeople() {
        return countPeople;
    }

    public void setCountPeople(int countPeople) {
        this.countPeople = countPeople;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public JourneyModel getJourney() {
        return journey;
    }

    public void setJourney(JourneyModel journey) {
        this.journey = journey;
    }
}
