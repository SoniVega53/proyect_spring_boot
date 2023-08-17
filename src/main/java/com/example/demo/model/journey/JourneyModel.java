package com.example.demo.model.journey;

import com.example.demo.UtilsTools;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity
@Table(name = "journey")
@Getter
@Setter
public class JourneyModel {
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

    @Column(name = "status")
    private String status;

    @Column(name = "count")
    private int count;

    public JourneyModel() {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

    

}
