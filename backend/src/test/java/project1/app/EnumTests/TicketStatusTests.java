package project1.app.EnumTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import project1.app.Enums.TicketStatus;
import project1.app.Exceptions.Status400.InvalidEnumValueException;

public class TicketStatusTests {
  @Test
  public void testValidEnumValues() {
    assertEquals(TicketStatus.fromString("Pending"), TicketStatus.PENDING);
    assertEquals(TicketStatus.fromString("PENDING"), TicketStatus.PENDING);

    assertEquals(TicketStatus.fromString("Approved"), TicketStatus.APPROVED);
    assertEquals(TicketStatus.fromString("APPROVED"), TicketStatus.APPROVED);

    assertEquals(TicketStatus.fromString("Denied"), TicketStatus.DENIED);
    assertEquals(TicketStatus.fromString("DENIED"), TicketStatus.DENIED);
  }

  @Test
  public void testInvalidEnumValues() {
    String expectedMessage = "Invalid ticket status. Value must be Pending, Approved or Denied, case insensitive";

    assertThrows(InvalidEnumValueException.class, () -> TicketStatus.fromString("Waiting on someone to finally check it"), expectedMessage);
    assertThrows(InvalidEnumValueException.class, () -> TicketStatus.fromString("I get my money back"), expectedMessage);
    assertThrows(InvalidEnumValueException.class, () -> TicketStatus.fromString("No money back lol"), expectedMessage);
    assertThrows(InvalidEnumValueException.class, () -> TicketStatus.fromString("Whatever happened to it"), expectedMessage);
  }

  @Test
  public void testNullValue() {
    assertEquals(TicketStatus.fromString(null), null);
  }
}
