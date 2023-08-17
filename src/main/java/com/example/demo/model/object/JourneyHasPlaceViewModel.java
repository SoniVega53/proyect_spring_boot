package com.example.demo.model.object;

import com.example.demo.model.PlacesModel;
import com.example.demo.model.journey.JourneyModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JourneyHasPlaceViewModel {
    private JourneyModel journey;
    private PlacesModel placeOri;
    private PlacesModel placeDes;

    public JourneyHasPlaceViewModel() {
    }

    public JourneyHasPlaceViewModel(JourneyModel journey, PlacesModel placeOri, PlacesModel placeDes) {
        this.journey = journey;
        this.placeOri = placeOri;
        this.placeDes = placeDes;
    }

	public JourneyModel getJourney() {
		return journey;
	}

	public void setJourney(JourneyModel journey) {
		this.journey = journey;
	}

	public PlacesModel getPlaceOri() {
		return placeOri;
	}

	public void setPlaceOri(PlacesModel placeOri) {
		this.placeOri = placeOri;
	}

	public PlacesModel getPlaceDes() {
		return placeDes;
	}

	public void setPlaceDes(PlacesModel placeDes) {
		this.placeDes = placeDes;
	}
    
    
}
