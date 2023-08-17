package com.example.demo.model.object;

import com.example.demo.model.PublicityModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class PublicityDateCorruselModel {
    private PublicityModel publicitySelect;
    private int count;

    public PublicityModel getPublicitySelect() {
        return publicitySelect;
    }

    public void setPublicitySelect(PublicityModel publicitySelect) {
        this.publicitySelect = publicitySelect;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
