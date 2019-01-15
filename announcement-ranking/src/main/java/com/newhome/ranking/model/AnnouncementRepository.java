package com.newhome.ranking.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>{

	public List<Announcement> findAllByOrderByScoreDesc();
	
	public List<Announcement> findAllByOrderByCreationDate();
	
}
