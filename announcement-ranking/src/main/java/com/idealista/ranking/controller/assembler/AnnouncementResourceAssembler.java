package com.idealista.ranking.controller.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.idealista.ranking.controller.AnnouncementController;
import com.idealista.ranking.model.Announcement;

@Component
public class AnnouncementResourceAssembler implements ResourceAssembler<Announcement, Resource<Announcement>>{

	@Override
	public Resource<Announcement> toResource(Announcement announ) {
		// TODO Auto-generated method stub
		return new Resource<>(announ, 
				linkTo(methodOn(AnnouncementController.class).one(announ.getId())).withSelfRel(),
				linkTo(methodOn(AnnouncementController.class).all()).withRel("announcements"));
	}

}
