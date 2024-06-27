package com.myfitnessapp.handler;

import com.myfitnessapp.exceptions.InvalidReferenceException;
import com.myfitnessapp.exceptions.ObjectNotValidException;
import com.myfitnessapp.exceptions.SerieNoValidaException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<?> handleException(IllegalStateException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<?> handleException(EntityNotFoundException e) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(InvalidReferenceException.class)
  public ResponseEntity<?> handleException(InvalidReferenceException e) {
    return ResponseEntity.badRequest().body(e.getErrorMessage());
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleException(MethodArgumentNotValidException e){
    Set<String> errorMessages = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toSet());
    return ResponseEntity.badRequest().body(errorMessages);
  }

  @ExceptionHandler(SerieNoValidaException.class)
  public ResponseEntity<?> handleException(SerieNoValidaException e) {
    return ResponseEntity.badRequest().body(e.getErrorMessage());
  }



}
