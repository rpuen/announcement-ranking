package com.idealista.ranking.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping("/announcements")
	public Announcement newAnnouncement(@RequestBody Announcement newAnnouncement) {
		return repo.save(newAnnouncement);
	}

	@GetMapping("/announcements/{id}")
	public Announcement one(@PathVariable Long id) {

		return repo.findById(id)
				.orElseThrow(() -> new AnnouncementNotFoundException(id));
	}

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

	@GetMapping("/puntuacion")
	public List<Announcement> getGlobalRanking(){
		repo.findAll().stream()
		.map(announcement -> {
			if(announcement.getPictures()==null) {
				announcement.setScore(announcement.getScore()-10);
			}else {
				for (Long pic=0; pic<announcement.getPictures().length;pic++){
					if(picRepo.findById(pic).get().getQuality()=="SD") {
						announcement.setScore(announcement.getScore()+10);
					}else {
						announcement.setScore(announcement.getScore()+20);
					}
			}
			return repo.save(announcement);
		});	
	}
}
