package project1.app.Exceptions;

public class InvalidOrMissingJWT extends RuntimeException {
  public InvalidOrMissingJWT(String msg) {
    super(msg);
  }
}
