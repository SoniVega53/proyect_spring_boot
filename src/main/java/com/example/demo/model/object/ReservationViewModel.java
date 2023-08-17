package com.example.demo.model.object;

import com.example.demo.model.PlacesModel;
import com.example.demo.model.journey.JourneyModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationViewModel {
    private int id;
    private int countPeople;
    private String observations;
    private JourneyModel journey;
    private PlacesModel place;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
    
    
}
