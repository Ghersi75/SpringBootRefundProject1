package project1.app.Controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import project1.app.Exceptions.Status409.Status409Exception;
import project1.app.Exceptions.Status500.Status500Exception;

import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

// Not too familiar with this, but it's typically used for Exception Handler classes
@RestControllerAdvice
public class GlobalExceptionHandler {
  // Status 409 - Conflict
  // Thrown if a unique user field is already taken
  // Example: User SignUp username or email already taken
  @ExceptionHandler(Status409Exception.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public Map<String, String> Status409Handler(Status409Exception e) {
    return Map.of("error", e.getMessage());
  }

  // Status 500 - Internal Server Error
  // Thrown if something outside of user control goes wrong
  // Example: User creation goes wrong
  @ExceptionHandler(Status500Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, String> Status500Handler(Status500Exception e) {
    return Map.of("error", e.getMessage());
  }

  // Status 400 - Bad Request
  // This will hit if any of the request body is invalid
  // Each field is checked by jakarta and appropriate messages will be returned
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleInvalidArguments(MethodArgumentNotValidException e) {
    // Don't understand this well, but BindingResult essentially keeps track of errors, if any
    // getFieldErrors() returns a List<FieldError> and since this method is hit, there must be at least 1 item in the List, thus .get(0) returns it
    FieldError firstError = e.getBindingResult().getFieldErrors().get(0);
    // Not sure why it's called DefaultMessage, but this works as intended
    return Map.of("error", firstError.getDefaultMessage());
  }
}
