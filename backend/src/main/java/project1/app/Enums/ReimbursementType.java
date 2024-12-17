package project1.app.Enums;

import project1.app.Exceptions.Status400.InvalidEnumValueException;

public enum ReimbursementType {
  TRAVEL("TRAVEL"),
  LODGING("LODGING"),
  FOOD("FOOD"),
  OTHER("OTHER");

  private final String value;

  ReimbursementType(String value) {
    this.value = value;
  }

  public static ReimbursementType fromString(String value) {
    if (value == null) {
      return null;
    }

    for (ReimbursementType reimbursementType : ReimbursementType.values()) {
      if (reimbursementType.value.equalsIgnoreCase(value)) {
        return reimbursementType;
      }
    }

    throw new InvalidEnumValueException("Invalid reimbursement type. Value must be Travel, Lodging, Food or Other, case insensitive.");
  }
}
