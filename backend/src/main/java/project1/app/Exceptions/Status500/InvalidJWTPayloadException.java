package project1.app.Exceptions.Status500;

public class InvalidJWTPayloadException extends RuntimeException {
  public InvalidJWTPayloadException(String msg) {
    super(msg);
  }
}
