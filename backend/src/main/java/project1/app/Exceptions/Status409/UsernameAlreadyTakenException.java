package project1.app.Exceptions.Status409;

public class UsernameAlreadyTakenException extends Status409Exception {
  public UsernameAlreadyTakenException(String msg) {
    super(msg);
  }
}
