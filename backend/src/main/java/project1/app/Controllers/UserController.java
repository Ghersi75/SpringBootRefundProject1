package project1.app.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import project1.app.DTO.AuthUserInfoDTO;
import project1.app.DTO.UserInfoDTO;
import project1.app.DTO.UserLoginDTO;
import project1.app.DTO.UserSignUpDTO;
import project1.app.Enums.UserRole;
import project1.app.Models.User;
import project1.app.Service.UserService;
import project1.app.Utils.CookieUtil;
import project1.app.Utils.JWTUtil;

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
    String JWT = JWTUtil.generateToken(1L, UserRole.MANAGER);
    System.out.println(JWT);

    System.out.println(JWTUtil.extractUserInfoFromJWT(JWT));

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

  // TODO: Abstract away try catch boilerplate
  @PostMapping("/signup")
  // 201 Created
  @ResponseStatus(HttpStatus.CREATED)
  // Rollback for any exception
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Boolean> SingUpHandler(@Valid @RequestBody() UserSignUpDTO userInfo, HttpServletResponse res) {
    // userService.SignUpUser should never return null, so this should be safe
    UserInfoDTO newUserInfo = this.userService.SignUpUser(userInfo);
    Cookie userInfoCookie = CookieUtil.CreateCookie("userInfo", newUserInfo.getDisplayUserInfoDTO(), "/", false, true, -1);
    res.addCookie(userInfoCookie);
    AuthUserInfoDTO authUserInfo = newUserInfo.getAuthUserInfoDTO();
    String jwt = JWTUtil.generateToken(authUserInfo.getUserId(), authUserInfo.getUserRole());
    Cookie userAuthCookie = CookieUtil.CreateCookie("jwt", jwt, "/", true, true, -1);
    res.addCookie(userAuthCookie);
    return Map.of("success", true);
  }

  // TODO: Abstract away try catch boilerplate
  @PostMapping("/login")
  // 200 OK
  @ResponseStatus(HttpStatus.OK)
  // Rollback for any exception
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Boolean> LoginHandler(@Valid @RequestBody() UserLoginDTO userInfo, HttpServletResponse res) {
    UserInfoDTO loggedInUserInfo = this.userService.LoginUser(userInfo);
    Cookie userInfoCookie = CookieUtil.CreateCookie("userInfo", loggedInUserInfo.getDisplayUserInfoDTO(), "/", false, true, -1);
    res.addCookie(userInfoCookie);
    AuthUserInfoDTO authUserInfo = loggedInUserInfo.getAuthUserInfoDTO();
    String jwt = JWTUtil.generateToken(authUserInfo.getUserId(), authUserInfo.getUserRole());
    Cookie userAuthCookie = CookieUtil.CreateCookie("jwt", jwt, "/", true, true, -1);
    res.addCookie(userAuthCookie);
    return Map.of("success", true);
  }
}
