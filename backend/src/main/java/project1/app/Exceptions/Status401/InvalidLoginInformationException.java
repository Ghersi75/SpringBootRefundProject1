package project1.app.Exceptions.Status401;

public class InvalidLoginInformationException extends Status401Exception {
  public InvalidLoginInformationException(String msg) {
    super(msg);
  }
}
