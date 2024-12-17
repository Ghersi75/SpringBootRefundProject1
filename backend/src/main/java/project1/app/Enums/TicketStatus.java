package project1.app.Enums;

import project1.app.Exceptions.Status400.InvalidEnumValueException;

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

    for ( TicketStatus status : TicketStatus.values() ) {
      if ( status.value.equalsIgnoreCase(value) ) {
        return status;
      }
    }

    throw new InvalidEnumValueException("Invalid ticket status. Value must be Pending, Approved or Denied, case insensitive");
  }
}
