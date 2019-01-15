package com.newhome.ranking.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.Temporal;

import com.newhome.ranking.model.enums.Typology;

import lombok.Data;

@Entity
@Data
public class Announcement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Min(value = 0)
	@Max(value = 100)
	private int score;
	private String description;
	private Typology typology;
	private int houseSize;
	private int gardenSize;
	
	@ManyToMany(fetch = FetchType.EAGER,
			cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private List<Picture> pictures;
	
	@CreatedDate
	private Date creationDate;
	

	
	public Announcement() {}
	
	public Announcement (String description, Typology typology, int houseSize, int gardenSize, List<Picture> pictures){
		this.description = description;
		this.typology = typology;
		this.houseSize = houseSize;
		this.gardenSize = gardenSize;
		this.pictures = pictures;
	}
	
}