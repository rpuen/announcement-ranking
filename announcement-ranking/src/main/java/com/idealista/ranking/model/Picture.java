package com.idealista.ranking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Picture {

	@Id
	@GeneratedValue
	private Long id;
	private String url;
	private String quality;
	
	
	public Picture (String url, String quality) {
		this.url=url;
		this.quality=quality;
	}
}
