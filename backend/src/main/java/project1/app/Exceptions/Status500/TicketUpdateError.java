package project1.app.Exceptions.Status500;

public class TicketUpdateError extends Status500Exception {
  public TicketUpdateError(String msg) {
    super(msg);
  }
}
