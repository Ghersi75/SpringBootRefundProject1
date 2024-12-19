package project1.app.Exceptions.Status401;

public class InvalidOrMissingJWTExeptions extends Status401Exception {
  public InvalidOrMissingJWTExeptions(String msg) {
    super(msg);
  }
}
