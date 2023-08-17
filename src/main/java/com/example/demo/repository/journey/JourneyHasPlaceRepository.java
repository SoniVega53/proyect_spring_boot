package com.example.demo.repository.journey;

import com.example.demo.model.journey.JourneyHasPlaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JourneyHasPlaceRepository extends JpaRepository<JourneyHasPlaceModel,Integer> {

    List<JourneyHasPlaceModel> findAllByJourney_Id(int id);
    JourneyHasPlaceModel findAllByJourney_IdAndType(int id,String type);

}
