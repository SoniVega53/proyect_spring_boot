package com.example.demo.model.journey;

import com.example.demo.model.ImageModel;
import com.example.demo.model.PlacesModel;
import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "journey_has_place")
public class JourneyHasPlaceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "journey_id", referencedColumnName = "id")
    private JourneyModel journey;
    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private PlacesModel place;
    @Column(name = "type")
    private String type;

    public JourneyHasPlaceModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JourneyModel getJourney() {
        return journey;
    }

    public void setJourney(JourneyModel journey) {
        this.journey = journey;
    }

    public PlacesModel getPlace() {
        return place;
    }

    public void setPlace(PlacesModel place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
