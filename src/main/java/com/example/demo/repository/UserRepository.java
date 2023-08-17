package com.example.demo.repository;

import com.example.demo.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {
    UserModel findByEmailAndPassword(String email, String password);
    List<UserModel> findByRole(String role);
}
