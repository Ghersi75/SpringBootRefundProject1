package project1.app.EnumTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import project1.app.Enums.ReimbursementType;

public class ReimburesementTypeTests {
  @Test
  public void testValidEnumValues() {
    assertEquals(ReimbursementType.fromString("Travel"), ReimbursementType.TRAVEL);
    assertEquals(ReimbursementType.fromString("Lodging"), ReimbursementType.LODGING);
    assertEquals(ReimbursementType.fromString("Food"), ReimbursementType.FOOD);
    assertEquals(ReimbursementType.fromString("Other"), ReimbursementType.OTHER);
  }
}
