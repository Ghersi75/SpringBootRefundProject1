package project1.app.Exceptions.Status400;

public class InvalidTicketIdException extends Status400Exception {
  public InvalidTicketIdException(String msg) {
    super(msg);
  }
}
