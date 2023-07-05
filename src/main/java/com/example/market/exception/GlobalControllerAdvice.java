package com.example.market.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		Map<String, List<String>> errorMessages = new HashMap<>();
		for (ConstraintViolation<?> violation : violations) {
			errorMessages.computeIfAbsent("message", key -> new ArrayList<>()).add(violation.getMessage());
		}
		return ResponseEntity.badRequest().body(errorMessages.toString());
	}
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<?> applicationHandler(ApplicationException e){
		log.error("Error occurs {}", e.toString());
		return ResponseEntity.status(e.getErrorCode().getStatus())
			.body(e.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> applicationHandler(RuntimeException e){
		log.error("Error occurs {}", e.toString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(e.getMessage());
	}

}
