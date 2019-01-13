package com.idealista.ranking.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
@Entity
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
	
	public Announcement() {}
	
	public Announcement (String description, Typology typology, int houseSize, int gardenSize, List<Picture> pictures){
		this.description = description;
		this.typology = typology;
		this.houseSize = houseSize;
		this.gardenSize = gardenSize;
		this.pictures = pictures;
	}
}