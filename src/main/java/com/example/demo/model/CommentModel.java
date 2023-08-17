package com.example.demo.model;

import com.example.demo.UtilsTools;
import com.example.demo.model.journey.JourneyModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "comment")
public class CommentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "date")
    @JsonFormat(pattern = UtilsTools.FORMAT_DATETIME)
    private Date date;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserModel user;
    @ManyToOne
    @JoinColumn(name = "Journey_id", referencedColumnName = "id")
    private JourneyModel journey;

    public CommentModel() {
    }

    public CommentModel(Date date, String description, UserModel user, JourneyModel journey) {
        this.date = date;
        this.description = description;
        this.user = user;
        this.journey = journey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public JourneyModel getJourney() {
        return journey;
    }

    public void setJourney(JourneyModel journey) {
        this.journey = journey;
    }
}
