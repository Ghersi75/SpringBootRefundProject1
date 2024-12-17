package project1.app.Exceptions.Status500;

public class InvalidJWTException extends RuntimeException {
  public InvalidJWTException(String msg) {
    super(msg);
  }
}
