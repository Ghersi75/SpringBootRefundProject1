package project1.app.UtilTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import project1.app.Utils.PasswordUtil;

public class PasswordUtilTests {
  @Test
  public void testPasswordHashingMatchesPassword() {
    // Test that hashing works as expected
    // Since any password input can output any number of results, the best way to check that my hashing works is to use BCrypt check password
    // A bit silly to test this way since all PasswordUtil does is run bcrypt hash and checkpw, but why not
    String plainTextPassword = "test";
    assertTrue(BCrypt.checkpw(plainTextPassword, PasswordUtil.hashPassword(plainTextPassword)));

    plainTextPassword = "VerySecurePassword123456$$$$";
    assertTrue(BCrypt.checkpw(plainTextPassword, PasswordUtil.hashPassword(plainTextPassword)));

    plainTextPassword = "password123";
    assertTrue(BCrypt.checkpw(plainTextPassword, PasswordUtil.hashPassword(plainTextPassword)));
  }

  @Test
  public void testPasswordHashingMismatch() {
    String plainTextPassword = "test";
    assertFalse(BCrypt.checkpw(plainTextPassword, PasswordUtil.hashPassword(":test2")));

    plainTextPassword = "VerySecurePassword123456$$$$";
    assertFalse(BCrypt.checkpw(plainTextPassword, PasswordUtil.hashPassword("AlmostVerySecurePass")));

    plainTextPassword = "password123";
    assertFalse(BCrypt.checkpw(plainTextPassword, PasswordUtil.hashPassword("Password123")));
  }

  @Test
  public void testPasswordCheckValid() {
    String plainTextPassword = "test";
    assertTrue(PasswordUtil.checkPassword(plainTextPassword, BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12))));

    plainTextPassword = "VerySecurePassword123456$$$$";
    assertTrue(PasswordUtil.checkPassword(plainTextPassword, BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12))));

    plainTextPassword = "password123";
    assertTrue(PasswordUtil.checkPassword(plainTextPassword, BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12))));
  }

  @Test
  public void testPasswordCheckInvalid() {
    String plainTextPassword = "test";
    assertFalse(PasswordUtil.checkPassword(plainTextPassword, BCrypt.hashpw("tesT", BCrypt.gensalt(12))));

    plainTextPassword = "VerySecurePassword123456$$$$";
    assertFalse(PasswordUtil.checkPassword(plainTextPassword, BCrypt.hashpw("VerySecurepassword123456$$$$", BCrypt.gensalt(12))));

    plainTextPassword = "password123";
    assertFalse(PasswordUtil.checkPassword(plainTextPassword, BCrypt.hashpw("passworD123", BCrypt.gensalt(12))));
  }
}
