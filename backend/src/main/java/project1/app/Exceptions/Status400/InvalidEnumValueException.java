package project1.app.Exceptions.Status400;

public class InvalidEnumValueException extends Status400Exception {
  public InvalidEnumValueException(String msg) {
    super(msg);
  }
}
