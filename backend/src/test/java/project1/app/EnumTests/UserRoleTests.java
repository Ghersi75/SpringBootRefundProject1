package project1.app.EnumTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import project1.app.Enums.UserRole;
import project1.app.Exceptions.Status400.InvalidEnumValueException;

public class UserRoleTests {
  @Test
  public void testValidEnumValues() {
    assertEquals(UserRole.fromString("Employee"), UserRole.EMPLOYEE);
    assertEquals(UserRole.fromString("EMPLOYEE"), UserRole.EMPLOYEE);

    assertEquals(UserRole.fromString("Manager"), UserRole.MANAGER);
    assertEquals(UserRole.fromString("MaNaGeR"), UserRole.MANAGER);
  }

  @Test
  public void testInvalidEnumValues() {
    String expectedMessage = "Invalid user role. Value must be Manager or Employee, case insensitive";

    assertThrows(InvalidEnumValueException.class, () -> UserRole.fromString("I just work here"), expectedMessage);
    assertThrows(InvalidEnumValueException.class, () -> UserRole.fromString("Boss????"), expectedMessage);
    assertThrows(InvalidEnumValueException.class, () -> UserRole.fromString("Just a guy from down the street"), expectedMessage);
  }

  @Test
  public void testNullValue() {
    assertEquals(UserRole.fromString(null), null);
  }
}
