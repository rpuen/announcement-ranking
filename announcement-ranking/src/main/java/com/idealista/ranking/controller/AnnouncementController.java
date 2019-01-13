package com.idealista.ranking.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.idealista.ranking.exception.AnnouncementNotFoundException;
import com.idealista.ranking.model.Announcement;
import com.idealista.ranking.model.AnnouncementRepository;
import com.idealista.ranking.model.PictureRepository;

@RestController
public class AnnouncementController {

	private final AnnouncementRepository repo;
	private final PictureRepository picRepo;

	public AnnouncementController(AnnouncementRepository repo, PictureRepository picRepo) {
		this.repo = repo;
		this.picRepo=picRepo;
	}

	@GetMapping("/announcements")
	public List<Announcement> all(){
		return repo.findAll();
	}

	
	@GetMapping("/announcements/{id}")
	public Announcement one(@PathVariable Long id) {

		return repo.findById(id)
				.orElseThrow(() -> new AnnouncementNotFoundException(id));
	}

	@GetMapping("/admin/announcements")
	public List<Announcement> allIrrelevants() {
		return repo.findAll().stream()
				.filter(announce -> announce.getScore()<40)
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
