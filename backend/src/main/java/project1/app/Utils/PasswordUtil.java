package project1.app.Utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
  public static String hashPassword(String password) {
    // The higher this number the slower but safer the hashing will be
    int saltRounds = 12;
    return BCrypt.hashpw(password, BCrypt.gensalt(saltRounds));
  }

  public static boolean checkPassword(String plainPassword, String hashedPassword) {
    return(BCrypt.checkpw(plainPassword, hashedPassword));
  }
}
