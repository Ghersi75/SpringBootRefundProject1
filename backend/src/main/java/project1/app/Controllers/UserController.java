package project1.app.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import project1.app.Models.User;
import project1.app.Service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/user")
public class UserController {
  UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public String LoginHandler(@CookieValue(value = "token", defaultValue = "nothing") String token, HttpServletResponse res) {
    System.out.println(token);
    Cookie cookie = new Cookie("token", "test");

    res.addCookie(cookie);

    return "Logged in";
  }

  @GetMapping("test")
  public List<User> getAllUsers() {
    return this.userService.getAllUsers();
  }
  
  
}
