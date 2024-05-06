package com.test.prices.infrastructure.rest;

import java.text.ParseException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.test.prices.application.PriceNotFoundException;


@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {


	@ExceptionHandler({ParseException.class})
	public ResponseEntity<String> handleParseError( ParseException ex) {
		return ResponseEntity.badRequest().body(ex.getLocalizedMessage());

	}
	
	@ExceptionHandler({PriceNotFoundException.class})
	public ResponseEntity<String> handleParseError( PriceNotFoundException ex) {
		return ResponseEntity.badRequest().body("Price not found");

	}

	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.badRequest().body(String.format("Missed parameter with name:%s", ex.getParameterName()));
	}

}
