package com.example.demo.repository;

import com.example.demo.model.ReservationModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.journey.JourneyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationModel,Integer> {
    List<ReservationModel> findAllByUser(UserModel user);
    List<ReservationModel> findAllByJourney(JourneyModel user);
    ReservationModel findAllByUserAndJourney(UserModel user, JourneyModel jouney);
}
