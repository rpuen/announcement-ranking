package com.newhome.ranking.exception;

public class AnnouncementNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AnnouncementNotFoundException(Long id) {
		super("Could not find announcement " + id);
	}
}
