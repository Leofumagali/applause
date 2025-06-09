package com.applause.backend.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex) {
    String path = ex.getRequestURL();
    if (path.equals("/swagger.yaml")) {
        return ResponseEntity.notFound().build();
    }

    Map<String, Object> error = new HashMap<>();
    error.put("status", 404);
    error.put("error", "Not Found");
    error.put("message", "The requested endpoint does not exist.");
    error.put("path", path);

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
}