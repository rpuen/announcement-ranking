package com.newhome.ranking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.newhome.ranking.model.enums.Quality;

import lombok.Data;

@Entity
@Data
public class Picture {

	@Id
	@GeneratedValue
	private Long id;
	private String url;
	private Quality quality;
	
	public Picture() {}
	
	public Picture (String url, Quality quality) {
		this.url=url;
		this.quality=quality;
	}
}
