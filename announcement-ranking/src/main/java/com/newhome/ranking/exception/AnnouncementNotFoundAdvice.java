package com.newhome.ranking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AnnouncementNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(AnnouncementNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String announcementNotFoundHandler(AnnouncementNotFoundException ex) {
		return ex.getMessage();
	}
}
