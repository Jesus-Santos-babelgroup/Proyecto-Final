package com.helloworld.renting.exceptions.handler;

import com.helloworld.renting.exceptions.attributes.InvalidEmployedDataDtoException;
import com.helloworld.renting.exceptions.attributes.InvalidSelfEmployedDataDtoException;
import com.helloworld.renting.exceptions.attributes.InvalidClientDtoException;
import com.helloworld.renting.exceptions.db.DuplicateModel;
import com.helloworld.renting.exceptions.notfound.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateModel.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateModel(DuplicateModel ex, WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.CONFLICT.value());
        errorResponse.put("error", "Conflict");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("path", ((ServletWebRequest)request).getRequest().getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidEmployedDataDtoException.class)
    public ResponseEntity<String> handleInvalidEmployedDataDtoException(InvalidEmployedDataDtoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error de integridad de datos: " + ex.getMessage());
    }

    @ExceptionHandler(InvalidSelfEmployedDataDtoException.class)
    public ResponseEntity<String> handleInvalidSelfEmployedDataDtoException(InvalidSelfEmployedDataDtoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error de integridad de datos: " + ex.getMessage());
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(org.springframework.dao.DataIntegrityViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error de integridad de base de datos: " + ex.getRootCause().getMessage());
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    @ExceptionHandler(InvalidClientDtoException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidClientDtoException(InvalidClientDtoException ex, WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("path", ((ServletWebRequest)request).getRequest().getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}