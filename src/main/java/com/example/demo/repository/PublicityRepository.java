package com.example.demo.repository;

import com.example.demo.model.ImageModel;
import com.example.demo.model.PublicityModel;
import com.example.demo.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PublicityRepository extends JpaRepository<PublicityModel,Integer> {
    List<PublicityModel> findByDateInitial(Date initial);

}
