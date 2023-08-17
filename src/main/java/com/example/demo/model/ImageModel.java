package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "description")
    private String description;
    @Lob
    @Column(name = "url")
    private String url;
    @Column(name = "type")
    private String type;

    public ImageModel() {
    }

    public ImageModel(String description, String url, String type) {
        this.description = description;
        this.url = url;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
