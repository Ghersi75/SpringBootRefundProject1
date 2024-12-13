package project1.app.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import project1.app.Models.User;
import project1.app.Service.UserService;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
  UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public Map<String, Boolean> LoginHandler(@CookieValue(value = "token", defaultValue = "nothing") String token, HttpServletResponse res) {
    System.out.println(token);
    Cookie cookie = new Cookie("token", "test");

    res.addCookie(cookie);

    return Map.of("logged_in", false);
  }

  @GetMapping("test")
  public List<User> getAllUsers() {
    return this.userService.getAllUsers();
  }
  
  @GetMapping("/check-username") 
  public Map<String, Boolean> checkUsername(@RequestParam String username) {
    System.out.println("Username: " + username);

    return Map.of("isAvailable", this.userService.isUsernameAvailable(username));
  }
}
