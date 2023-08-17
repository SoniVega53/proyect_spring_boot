package com.example.demo.repository;


import com.example.demo.model.CommentModel;
import com.example.demo.model.journey.JourneyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<CommentModel,Integer> {
    List<CommentModel> findAllByJourneyOrderByDateDesc(JourneyModel journey);
}
