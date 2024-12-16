package project1.app.Enums;

public enum TicketStatus {
  PENDING("PENDING"),
  APPROVED("APPROVED"),
  DENIED("DENIED");

  private final String value;

  TicketStatus(String value) {
    this.value = value;
  }

  public static TicketStatus fromString(String value) {
    if (value == null) {
      return null;
    }
    System.out.println(value);
    for ( TicketStatus status : TicketStatus.values() ) {
      if ( status.value.equalsIgnoreCase(value) ) {
        return status;
      }
    }

    throw new IllegalArgumentException("Invalid TicketStatus value. Value must be Pending, Approved or Denied, case insensitive");
  }
}
