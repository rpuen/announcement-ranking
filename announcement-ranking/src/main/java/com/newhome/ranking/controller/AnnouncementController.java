package com.newhome.ranking.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newhome.ranking.controller.assembler.AnnouncementResourceAssembler;
import com.newhome.ranking.exception.AnnouncementNotFoundException;
import com.newhome.ranking.model.Announcement;
import com.newhome.ranking.model.AnnouncementRepository;
import com.newhome.ranking.service.AnnouncementService;

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
	public ResponseEntity<?> replaceAnnouncement(@RequestBody Announcement newAnnouncement,
			@PathVariable Long id) throws URISyntaxException{
		
		Announcement updatedAnnouncement = repo.findById(id)
				.map(announcement -> {
					announcement.setDescription(newAnnouncement.getDescription());
					announcement.setTypology(newAnnouncement.getTypology());
					announcement.setHouseSize(newAnnouncement.getHouseSize());
					announcement.setGardenSize(newAnnouncement.getGardenSize());
					announcement.setPictures(newAnnouncement.getPictures());
					return repoSer.saveOne(announcement);
				})
				.orElseGet(() -> {
					newAnnouncement.setId(id);
					return repoSer.saveOne(newAnnouncement);
				});
		
		Resource<Announcement> resource = assembler.toResource(updatedAnnouncement);
		
		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}

	@DeleteMapping("/announcements/{id}")
	public ResponseEntity<?> deleteAnnouncement(@PathVariable Long id) {
		repo.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/announcements")
	public ResponseEntity<?> newAnnouncement(@RequestBody Announcement newAnnouncement) throws URISyntaxException {
		
		Resource<Announcement> resource = assembler.toResource(repoSer.saveOne(newAnnouncement));
		
		return ResponseEntity
				.created(new URI(resource.getId().expand().getHref()))
				.body(resource);
	}
}
