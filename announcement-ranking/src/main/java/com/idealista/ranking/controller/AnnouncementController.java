package com.idealista.ranking.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.idealista.ranking.exception.AnnouncementNotFoundException;
import com.idealista.ranking.model.Announcement;
import com.idealista.ranking.model.AnnouncementRepository;

@RestController
public class AnnouncementController {

	private final AnnouncementRepository repo;

	public AnnouncementController(AnnouncementRepository repo) {
		this.repo = repo;
	}

	/**
	 * Método que devuelve todos los anuncios ordenados por ID
	 * @return List<Announcements>
	 */
	@GetMapping("/announcements")
	public List<Announcement> all(){
		return repo.findAll();
	}

	/**
	 * Método que devuelve el anuncio cuyo id se pasa como parámetro
	 * @param id
	 * @return
	 */
	@GetMapping("/announcements/{id}")
	public Announcement one(@PathVariable Long id) {

		return repo.findById(id)
				.orElseThrow(() -> new AnnouncementNotFoundException(id));
	}

	/**
	 * Método que devuelve todos los anuncios irrelevantes para el encargado de calidad
	 * @return
	 */
	@GetMapping("/admin/announcements")
	public List<Announcement> allIrrelevants() {
		return repo.findAll().stream()
				.filter(announce -> announce.getScore()<40)
				.collect(Collectors.toList());
	}
	
	/**
	 * Método que devuelve los anuncios relevantes para los usuarios
	 * @return
	 */
	@GetMapping("/user/announcements")
	public List<Announcement> allRelevants(){
		return repo.findAllByOrderByScoreDesc().stream()
				.filter(announce -> announce.getScore()>40)
				.collect(Collectors.toList());
	}
	
	/*
	@PutMapping("/announcements/{id}")
	public Announcement replaceAnnouncement(@RequestBody Announcement newAnnouncement,
			@PathVariable Long id) {
		return repo.findById(id)
				.map(announcement -> {
					announcement.setDescription(newAnnouncement.getDescription());
					announcement.setTypology(newAnnouncement.getTypology());
					announcement.setHouseSize(newAnnouncement.getHouseSize());
					announcement.setGardenSize(newAnnouncement.getGardenSize());
					announcement.setPictures(newAnnouncement.getPictures());
					return repo.save(announcement);
				})
				.orElseGet(() -> {
					newAnnouncement.setId(id);
					return repo.save(newAnnouncement);
				});
	}

	@DeleteMapping("/announcements/{id}")
	public void deleteAnnouncement(@PathVariable Long id) {
		repo.deleteById(id);
	}
	
	@PostMapping("/announcements")
	public Announcement newAnnouncement(@RequestBody Announcement newAnnouncement) {
		return repo.save(newAnnouncement);
	}
*/
	
}
