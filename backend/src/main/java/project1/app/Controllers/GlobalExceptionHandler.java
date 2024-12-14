package project1.app.Controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

// Not too familiar with this, but it's typically used for Exception Handler classes
@RestControllerAdvice
public class GlobalExceptionHandler {
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
