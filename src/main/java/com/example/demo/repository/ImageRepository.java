package com.example.demo.repository;

import com.example.demo.model.ImageModel;
import com.example.demo.model.PublicityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel,Integer> {
    List<ImageModel> findByType(String type);
}
