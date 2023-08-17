package com.example.demo.model;

import com.example.demo.UtilsTools;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "publicity")
@Getter
@Setter
public class PublicityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "date_initial")
    @JsonFormat(pattern = UtilsTools.FORMAT_DATE)
    private Date dateInitial;

    @Column(name = "date_end")
    @JsonFormat(pattern = UtilsTools.FORMAT_DATE)
    private Date dateEnd;

    @Column(name = "type")
    private String type;

    @Column(name = "hour_initial")
    private Time hourInitial;

    @Column(name = "hour_end")
    private Time hourEnd;

    @ManyToOne
    @JoinColumn(name = "id_image", referencedColumnName = "id")
    private ImageModel idImage;

    public PublicityModel() {
    }

    public PublicityModel(String description, Date dateInitial, Date dateEnd, String type, Time hourInitial, Time hourEnd, ImageModel idImage) {
        this.description = description;
        this.dateInitial = dateInitial;
        this.dateEnd = dateEnd;
        this.type = type;
        this.hourInitial = hourInitial;
        this.hourEnd = hourEnd;
        this.idImage = idImage;
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

	public Date getDateInitial() {
		return dateInitial;
	}

	public void setDateInitial(Date dateInitial) {
		this.dateInitial = dateInitial;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Time getHourInitial() {
		return hourInitial;
	}

	public void setHourInitial(Time hourInitial) {
		this.hourInitial = hourInitial;
	}

	public Time getHourEnd() {
		return hourEnd;
	}

	public void setHourEnd(Time hourEnd) {
		this.hourEnd = hourEnd;
	}

	public ImageModel getIdImage() {
		return idImage;
	}

	public void setIdImage(ImageModel idImage) {
		this.idImage = idImage;
	}

    
    
}
