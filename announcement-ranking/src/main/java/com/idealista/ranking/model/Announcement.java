package com.idealista.ranking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Announcement {

	@Id
	@GeneratedValue
	private Long id;
	private String description;
	private String typology;
	private int houseSize;
	private int gardenSize;
	private int [] pictures;
	
	public Announcement() {}
	
	public Announcement (String description, String typology, int houseSize, int gardenSize, int [] pictures){
		this.description = description;
		this.typology = typology;
		this.houseSize = houseSize;
		this.gardenSize = gardenSize;
		this.pictures = pictures;
	}
	
	public Announcement (String description, String typology, int houseSize, int gardenSize){
		this.description = description;
		this.typology = typology;
		this.houseSize = houseSize;
		this.gardenSize = gardenSize;
	}
	
	public Announcement (String description, String typology, int houseSize, int [] pictures){
		this.description = description;
		this.typology = typology;
		this.houseSize = houseSize;
		this.pictures = pictures;
	}
}
