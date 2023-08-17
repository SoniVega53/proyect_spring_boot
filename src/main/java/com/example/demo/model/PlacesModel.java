package com.example.demo.model;

import jakarta.persistence.*;


@Entity
@Table(name = "place")
public class PlacesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name_place")
    private String namePlace;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_image", referencedColumnName = "id")
    private ImageModel idImage;

    public ImageModel getIdImage() {
        return idImage;
    }

    public void setIdImage(ImageModel idImage) {
        this.idImage = idImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
