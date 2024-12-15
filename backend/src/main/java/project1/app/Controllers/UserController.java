package project1.app.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import project1.app.DTO.UserInfoDTO;
import project1.app.DTO.UserSignUpDTO;
import project1.app.Models.User;
import project1.app.Service.UserService;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// TODO: At some point, figure out how to trim strings from request body before they're turned into objects
// " username" should be treated as "username" for example
@RestController
@RequestMapping("/user")
// Allow credentials true necessary for browser cookies to be stored locally
// Request from frontend also needs credentials = true to work properly
// Not sure why this causes issues now, but it's fixed this way
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {
  UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  public List<User> GetAllUsersHandler() {
    return this.userService.getAllUsers();
  }

  @GetMapping("/login")
  public Map<String, Boolean> LoginHandler(@CookieValue(value = "token", defaultValue = "nothing") String token, HttpServletResponse res) {
    System.out.println(token);
    Cookie cookie = new Cookie("token", "test");

    res.addCookie(cookie);
    return Map.of("logged_in", false);
  }

  @GetMapping("/check-username")
  public Map<String, Boolean> checkUsername(@RequestParam String username) {
    System.out.println("Username: " + username);

    return Map.of("isAvailable", this.userService.isUsernameAvailable(username));
  }

  @PostMapping("/signup")
  // 201 Created
  @ResponseStatus(HttpStatus.CREATED)
  // Rollback for any exception
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Boolean> SingUpHandler(@Valid @RequestBody() UserSignUpDTO userInfo, HttpServletResponse res) {
    // userService.SignUpUser should never return null, so this should be safe
    UserInfoDTO newUserInfo = this.userService.SignUpUser(userInfo);

    try {
      ObjectMapper objectMapper = new ObjectMapper();

      // Throws RuntimeException so Transactional would rollback automatically
      String userInfoString = objectMapper.writeValueAsString(newUserInfo);
      // Throws checked Exception so Transaction rollbackFor would kick in and revert the tranasaction
      userInfoString = URLEncoder.encode(userInfoString, "UTF-8");
      Cookie userInfoCookie = new Cookie("userInfo", userInfoString);
      // Make it available on the entire website instaed of the path it makes the api call from
      userInfoCookie.setPath("/");
      // Allow the cookie to be accessed through javascript
      userInfoCookie.setHttpOnly(false);
      // Only send the cookie through HTTPS
      userInfoCookie.setSecure(true);
      // Setting the cookie age to -1 defaults to deleting once the session is over (close browser)
      // Could set this to any number (of seconds) and the cookie will be deleted by the browser once it expires
      userInfoCookie.setMaxAge(-1);

      res.addCookie(userInfoCookie);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return Map.of("success", true);
  }
}
