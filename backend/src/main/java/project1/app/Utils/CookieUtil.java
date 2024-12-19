package project1.app.Utils;

import java.net.URLEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import project1.app.Exceptions.Status500.CookieCreationException;

public class CookieUtil {
  public static Cookie CreateCookie(String key, Object value, String path, boolean httpOnly, boolean secure, int maxAge) {
    Cookie cookie;

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      // Throws RuntimeException so Transactional would rollback automatically
      String objectAsString = objectMapper.writeValueAsString(value);
      // Throws checked Exception so Transaction rollbackFor would kick in and revert the tranasaction
      String EncodedObjectAsString = URLEncoder.encode(objectAsString, "UTF-8");
      cookie = new Cookie(key, EncodedObjectAsString);
      // Make it available on the entire website instaed of the path it makes the api call from
      cookie.setPath(path);
      // Allow the cookie to be accessed through javascript
      cookie.setHttpOnly(httpOnly);
      // Only send the cookie through HTTPS
      cookie.setSecure(secure);
      // Setting the cookie age to -1 defaults to deleting once the session is over (close browser)
      // Could set this to any number (of seconds) and the cookie will be deleted by the browser once it expires
      cookie.setMaxAge(maxAge);
    } catch (Exception e) {
      throw new CookieCreationException("Cookie Creation Error");
    }

    return cookie;
  }
}
