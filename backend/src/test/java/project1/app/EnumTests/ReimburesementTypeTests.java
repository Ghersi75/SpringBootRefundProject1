package project1.app.EnumTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import project1.app.Enums.ReimbursementType;
import project1.app.Exceptions.Status400.InvalidEnumValueException;

public class ReimburesementTypeTests {
  @Test
  public void testValidEnumValues() {
    assertEquals(ReimbursementType.fromString("Travel"), ReimbursementType.TRAVEL);
    assertEquals(ReimbursementType.fromString("TRaveL"), ReimbursementType.TRAVEL);

    assertEquals(ReimbursementType.fromString("Lodging"), ReimbursementType.LODGING);
    assertEquals(ReimbursementType.fromString("lodGing"), ReimbursementType.LODGING);

    assertEquals(ReimbursementType.fromString("Food"), ReimbursementType.FOOD);
    assertEquals(ReimbursementType.fromString("FOod"), ReimbursementType.FOOD);

    assertEquals(ReimbursementType.fromString("Other"), ReimbursementType.OTHER);
    assertEquals(ReimbursementType.fromString("OTHER"), ReimbursementType.OTHER);
  }

  @Test
  public void testInvalidEnumValues() {
    String expectedMessage = "Invalid reimbursement type. Value must be Travel, Lodging, Food or Other, case insensitive.";

    assertThrows(InvalidEnumValueException.class, () -> ReimbursementType.fromString("travell"), expectedMessage);
    assertThrows(InvalidEnumValueException.class, () -> ReimbursementType.fromString("lodge"), expectedMessage);
    assertThrows(InvalidEnumValueException.class, () -> ReimbursementType.fromString("eating"), expectedMessage);
    assertThrows(InvalidEnumValueException.class, () -> ReimbursementType.fromString("no idea"), expectedMessage);
  }

  @Test
  public void testNullValue() {
    assertEquals(ReimbursementType.fromString(null), null);
  }
}
