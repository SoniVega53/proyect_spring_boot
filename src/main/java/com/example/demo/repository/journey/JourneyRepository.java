package com.example.demo.repository.journey;

import com.example.demo.model.journey.JourneyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JourneyRepository extends JpaRepository<JourneyModel,Integer> {

    List<JourneyModel> findAllByStatusOrderByDateInitialDesc(String status);

}
