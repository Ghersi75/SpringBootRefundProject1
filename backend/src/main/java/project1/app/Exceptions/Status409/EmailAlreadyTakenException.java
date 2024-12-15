package project1.app.Exceptions.Status409;

public class EmailAlreadyTakenException extends Status409Exception {
  public EmailAlreadyTakenException(String msg) {
    super(msg);
  }
}
