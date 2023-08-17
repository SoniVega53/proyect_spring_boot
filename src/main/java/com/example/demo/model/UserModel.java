package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "email",nullable = false, length = 50,unique = true)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "lastname",nullable = false)
    private String lastname;
    @Column(name = "phone")
    private String phone;
    @Column(name = "carnet")
    private String carnet;
    @Column(name = "university")
    private String university;
    @Column(name = "course")
    private String course;
    @Column(name = "role",nullable = false)
    private String role;

    public UserModel() {
    }

    public UserModel(int id, String email, String password, String name, String lastname, String phone, String carnet, String university, String course, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.carnet = carnet;
        this.university = university;
        this.course = course;
        this.role = role;
    }

    public UserModel(String email, String password, String name, String lastname, String phone, String carnet, String university, String course, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.carnet = carnet;
        this.university = university;
        this.course = course;
        this.role = role;
    }

    public UserModel(String email, String password, String name, String lastname, String phone, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.role = role;
    }

    public int getIdUser() {
        return id;
    }

    public void setIdUser(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
