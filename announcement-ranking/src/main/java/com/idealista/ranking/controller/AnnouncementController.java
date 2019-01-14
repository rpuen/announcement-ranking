package com.idealista.ranking.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idealista.ranking.controller.assembler.AnnouncementResourceAssembler;
import com.idealista.ranking.exception.AnnouncementNotFoundException;
import com.idealista.ranking.model.Announcement;
import com.idealista.ranking.model.AnnouncementRepository;
import com.idealista.ranking.service.AnnouncementService;

@RestController
public class AnnouncementController {

	private final AnnouncementRepository repo;
	
	private final AnnouncementService repoSer;

	private final AnnouncementResourceAssembler assembler;
	
	public AnnouncementController(AnnouncementRepository repo, AnnouncementService repoSer, 
			AnnouncementResourceAssembler assembler) {
		this.repo = repo;
		this.repoSer = repoSer;
		this.assembler = assembler;
	}

	/**
	 * Método que devuelve todos los anuncios ordenados por ID
	 * @return List<Announcements>
	 */
	@GetMapping("/announcements")
	public Resources<Resource<Announcement>> all(){
		
		List<Resource<Announcement>> announcements = repo.findAll().stream()
				.map(assembler :: toResource)
				.collect(Collectors.toList());
		
		return new Resources<>(announcements,
				linkTo(methodOn(AnnouncementController.class).all()).withSelfRel());
	}

	/**
	 * Método que devuelve el anuncio cuyo id se pasa como parámetro
	 * @param id
	 * @return
	 */
	@GetMapping("/announcements/{id}")
	public Resource<Announcement> one(@PathVariable Long id) {

		Announcement announcement = repo.findById(id)
				.orElseThrow(() -> new AnnouncementNotFoundException(id));
		
		return assembler.toResource(announcement);
	}

	/**
	 * Método que devuelve todos los anuncios irrelevantes para el encargado de calidad
	 * ordenados por fecha de creación
	 * @return
	 */
	@GetMapping("/admin/announcements")
	public Resources<Resource<Announcement>> allIrrelevants() {
		
		List<Resource<Announcement>> announcements = repo.findAllByOrderByCreationDate().stream()
				.filter(announcement -> announcement.getScore() < 40)
				.map(assembler :: toResource)
				.collect(Collectors.toList());
		
		return new Resources<> (announcements,
				linkTo(methodOn(AnnouncementController.class).allIrrelevants()).withSelfRel());
	}
	
	/**
	 * Método que devuelve los anuncios relevantes para los usuarios
	 * ordenados por puntuación
	 * @return
	 */
	@GetMapping("/user/announcements")
	public Resources<Resource<Announcement>> allRelevants(){
		
		List<Resource<Announcement>> announcements = repo.findAllByOrderByScoreDesc().stream()
				.filter(announce -> announce.getScore() > 40)
				.map(assembler :: toResource)
				.collect(Collectors.toList());
		
		return new Resources<> (announcements,
				linkTo(methodOn(AnnouncementController.class).allIrrelevants()).withSelfRel());
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
	
	@PostMapping("/announcements")
	public Announcement newAnnouncement(@RequestBody Announcement newAnnouncement) {
		return repoSer.saveOne(newAnnouncement);
	}

	
}
