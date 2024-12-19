package project1.app.Exceptions.Status400;

public class InvalidUserIdException extends Status400Exception {
  public InvalidUserIdException(String msg) {
    super(msg);
  }
}
