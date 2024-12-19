package project1.app.ServiceTests;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import project1.app.Service.UserService;
import project1.app.DTO.AuthUserInfoDTO;
import project1.app.DTO.DisplayUserInfoDTO;
import project1.app.DTO.UserInfoDTO;
import project1.app.DTO.UserLoginDTO;
import project1.app.DTO.UserSignUpDTO;
import project1.app.Enums.UserRole;
import project1.app.Exceptions.Status401.InvalidLoginInformationException;
import project1.app.Exceptions.Status409.EmailAlreadyTakenException;
import project1.app.Exceptions.Status409.UsernameAlreadyTakenException;
import project1.app.Exceptions.Status500.UserCreationError;
import project1.app.Models.User;
import project1.app.Repository.UserRepository;

// https://www.geeksforgeeks.org/unit-testing-in-spring-boot-project-using-mockito-and-junit/
// Used site above for reference
@ExtendWith(MockitoExtension.class)
// Without this mockito throws exception if any mock values are not used
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTests {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private String user1User = "user1";
  private String user2User = "ghersi";

  private String user1Email = "user1@gmail.com";
  private String user2Email = "testemail@test.com";
  // "testpassword" with 12 cost factor
  // just used PasswordUtil and hashed it on the go
  private String user1PasswordHash = "$2a$12$V7kp4sGMcYR5qj26XVwsf.mKiLxKDBLF2Hd1Fqlh4A6EZpp2F3QAa";
  private String user1Pass = "testpassword";

  @BeforeEach
  public void setup() {
    User user1 = new User();
    user1.setId(1L);
    user1.setUsername(user1User);
    user1.setEmail(user1Email);
    user1.setPasswordHash(user1PasswordHash);

    User user2 = new User();
    user2.setId(2L);
    user2.setUsername(user2User);
    user2.setEmail(user2Email);

    // getAllUsers
    when(userRepository.findAll()).thenReturn(List.of(user1, user2));

    // isUsernameAvailable
    // https://davidvlijmincx.com/posts/mockito_thenreturn_and_thenanswer/
    when(userRepository.findByUsernameIgnoreCase(Mockito.anyString())).thenAnswer(invocation -> {
      String username = invocation.getArgument(0);

      if (username.equalsIgnoreCase(user1.getUsername())) {
        return user1;
      } else if (username.equalsIgnoreCase(user2.getUsername())) {
        return user2;
      }

      return null;
    });
    // SignUpUser
    when(userRepository.findByEmailIgnoreCase(Mockito.anyString())).thenAnswer(invocation -> {
      String email = invocation.getArgument(0);

      if (email.equalsIgnoreCase(user1Email)) {
        return user1;
      } else if (email.equalsIgnoreCase(user2Email)) {
        return user2;
      }

      return null;
    });
  }

  @Test
  public void testGetAllUsers() {
    List<User> users = this.userService.getAllUsers();

    assertEquals(2, users.size());
    assertEquals(1L, users.get(0).getId());
    assertEquals(user2Email, users.get(1).getEmail());
  }

  public void testIsUsernameAvailable() {
    boolean takenUsername = this.userService.isUsernameAvailable("GhERsI");
    boolean availableUsername = this.userService.isUsernameAvailable("ghersi1");

    assertEquals(false, takenUsername);
    assertEquals(true, availableUsername);
  }

  @Test
  public void testSignUpUser() {
    UserSignUpDTO userInfo = new UserSignUpDTO();

    // Username already taken
    userInfo.setUsername(user1User);
    assertThrows(UsernameAlreadyTakenException.class, () -> this.userService.SignUpUser(userInfo));
    userInfo.setUsername("unique username");

    // Email already taken
    userInfo.setEmail(user1Email);
    assertThrows(EmailAlreadyTakenException.class, () -> this.userService.SignUpUser(userInfo));
    userInfo.setEmail("uniquemail@gmail.com");

    // Simulate user creation failing
    when(this.userRepository.findByUsernameIgnoreCase(Mockito.anyString())).thenReturn(null);
    assertThrows(UserCreationError.class, () -> this.userService.SignUpUser(userInfo));

    // Simulate new user being created
    // .thenReturn chained simulates multiple calls
    // Since service first checks that username is not taken, it should return null
    // first and then the created user second
    when(this.userRepository.findByUsernameIgnoreCase(userInfo.getUsername()))
        .thenReturn(null)
        .thenAnswer(invocation -> {
          User newUser = new User(userInfo);
          newUser.setId(3L);

          return newUser;

        });

    // new User defaults to EMPLOYEE if not set
    DisplayUserInfoDTO displayUserInfo = new DisplayUserInfoDTO(
        userInfo.getUsername(),
        userInfo.getEmail(),
        3L,
        UserRole.EMPLOYEE);
    AuthUserInfoDTO authUserInfo = new AuthUserInfoDTO(3L, UserRole.EMPLOYEE);
    assertEquals(new UserInfoDTO(displayUserInfo, authUserInfo), this.userService.SignUpUser(userInfo));
  }

  @Test
  public void LoginUser() {
    UserLoginDTO userInfo = new UserLoginDTO();
    // Email that doesn't exist
    userInfo.setEmail("uniquemail@gmail.com");
    assertThrows(InvalidLoginInformationException.class, () -> this.userService.LoginUser(userInfo));
    userInfo.setEmail(user1Email);

    // Invalid password
    userInfo.setPassword("password");
    assertThrows(InvalidLoginInformationException.class, () -> this.userService.LoginUser(userInfo));
    userInfo.setPassword(user1Pass);

    // Valid login information
    // does not default to EMPLOYEE since no new user is created here
    DisplayUserInfoDTO displayUserInfo = new DisplayUserInfoDTO(
        user1User,
        user1Email,
        1L,
        null);
    AuthUserInfoDTO authUserInfo = new AuthUserInfoDTO(1L, null);

    assertEquals(new UserInfoDTO(displayUserInfo, authUserInfo), this.userService.LoginUser(userInfo));
  }
}
