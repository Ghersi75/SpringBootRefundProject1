package project1.app.Exceptions.Status500;

public class UserCreationError extends Status500Exception {
  public UserCreationError(String msg) {
    super(msg);
  }
}
