package com.idealista.ranking.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idealista.ranking.model.Announcement;
import com.idealista.ranking.model.AnnouncementRepository;
import com.idealista.ranking.model.enums.Quality;
import com.idealista.ranking.model.enums.Typology;

@Service
public class AnnouncementService {

	@Autowired
	private AnnouncementRepository repo;

	public void save(List<Announcement> announcements) {
		announcements.stream()
		.forEach(announcement ->{
			picturesScore(announcement);
			isDescription(announcement);
			flatDescription(announcement);		
			chaletDescription(announcement);
			keywordsScore(announcement);	
			completionScore(announcement);
			scoreAdjust(announcement);
			announcement.setCreationDate(new Date());
			repo.save(announcement);
		});
	}

	/**
	 * Función que ajusta la puntuación, entre 0 y 100
	 * @param announcement
	 */
	public void scoreAdjust(Announcement announcement) {
		if (announcement.getScore() < 0)
			announcement.setScore(0);
		if (announcement.getScore() > 100)
			announcement.setScore(100);
	}

	/**
	 * Función que evalúa la complitud de un anuncio
	 * @param announcement
	 */
	public void completionScore(Announcement announcement) {
		if (announcement.getDescription() != "" &&
				!(announcement.getPictures() == null)) {

			if (announcement.getTypology() == Typology.FLAT && 
					announcement.getHouseSize() != 0)
				announcement.setScore(announcement.getScore()+40);
			if (announcement.getTypology() == Typology.CHALET && 
					announcement.getHouseSize() != 0 &&
					announcement.getGardenSize() != 0)
				announcement.setScore(announcement.getScore()+40);
		}
		if (announcement.getTypology() == Typology.GARAGE &&
				!(announcement.getPictures()==null))
			announcement.setScore(announcement.getScore()+40);
	}

	/**
	 * Función que evalúa si el anuncio contiene ciertas palabras clave
	 * @param announcement
	 */
	public void keywordsScore(Announcement announcement) {
		if (announcement.getDescription().toLowerCase().contains("luminoso"))
			announcement.setScore(announcement.getScore()+5);
		if (announcement.getDescription().toLowerCase().contains("nuevo"))
			announcement.setScore(announcement.getScore()+5);
		if (announcement.getDescription().toLowerCase().contains("céntrico"))
			announcement.setScore(announcement.getScore()+5);
		if (announcement.getDescription().toLowerCase().contains("reformado"))
			announcement.setScore(announcement.getScore()+5);
		if (announcement.getDescription().toLowerCase().contains("ático"))
			announcement.setScore(announcement.getScore()+5);
	}

	/**
	 * Fución que evalúa la descripción de un anuncio tipo Chalet
	 * @param announcement
	 */
	public void chaletDescription(Announcement announcement) {
		if (announcement.getTypology() == Typology.CHALET) {
			if (announcement.getDescription().length() > 50)
				announcement.setScore(announcement.getScore()+20);
		}
	}

	/**
	 * Fución que evalúa la descripción de un anuncio tipo Flat
	 * @param announcement
	 */
	public void flatDescription(Announcement announcement) {
		if (announcement.getTypology() == Typology.FLAT) {
			if (announcement.getDescription().length() >= 20 && announcement.getDescription().length() < 50) {
				announcement.setScore(announcement.getScore()+10);
			} else if (announcement.getDescription().length() >= 50){
				announcement.setScore(announcement.getScore()+30);
			}
		}
	}

	/**
	 * Fución que evalúa que un anuncio tenga descripción
	 * @param announcement
	 */
	public void isDescription(Announcement announcement) {
		if (announcement.getDescription() != "")
			announcement.setScore(announcement.getScore()+5);
	}

	/**
	 * Función que evalúa las imágenes de un anuncio, así como su calidad
	 * @param announcement
	 */
	public void picturesScore(Announcement announcement) {
		if (announcement.getPictures() == null) {
			announcement.setScore(announcement.getScore()-10);
		}
		else {
			announcement.getPictures().stream()
			.forEach(picture -> {
				if(picture.getQuality().equals(Quality.SD)) {
					announcement.setScore(announcement.getScore()+10);
				} else {
					announcement.setScore(announcement.getScore()+20);
				}
			});
		}
	}

	public Announcement saveOne(Announcement announcement) {
		picturesScore(announcement);
		isDescription(announcement);
		flatDescription(announcement);		
		chaletDescription(announcement);
		keywordsScore(announcement);	
		completionScore(announcement);
		scoreAdjust(announcement);
		announcement.setCreationDate(new Date());
		repo.save(announcement);
		return announcement;
	}
}
