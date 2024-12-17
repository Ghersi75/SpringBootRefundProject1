package project1.app.Enums;

import project1.app.Exceptions.Status400.InvalidEnumValueException;

public enum UserRole {
  EMPLOYEE("EMPLOYEE"),
  MANAGER("MANAGER");

  private final String value;

  UserRole(String value) {
    this.value = value;
  }

  public static UserRole fromString(String value) {
    if (value == null) {
      return null;
    }

    for (UserRole userRole : UserRole.values()) {
      if (userRole.value.equalsIgnoreCase(value)) {
        return userRole;
      }
    }

    throw new InvalidEnumValueException("Invalid user role. Value must be Manager or Employee, case insensitive");
  }
}
