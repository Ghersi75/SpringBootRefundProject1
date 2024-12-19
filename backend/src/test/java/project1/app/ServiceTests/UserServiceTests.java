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
import project1.app.DTO.UserSignUpDTO;
import project1.app.Enums.UserRole;
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

  @BeforeEach
  public void setup() {
    User user1 = new User();
    user1.setId(1L);
    user1.setUsername("user1");
    user1.setEmail("user1@email.com");
    User user2 = new User();
    user2.setId(2L);
    user2.setUsername("ghersi");
    user2.setEmail("testemail@test.com");

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

      if (email.equalsIgnoreCase("user1@gmail.com")) {
        return user1;
      } else if (email.equalsIgnoreCase("testemail@test.com")) {
        return user2;
      }

      return null;
    });

    System.out.println("Email: " + this.userRepository.findByEmailIgnoreCase("testemail@test.com"));
  }

  @Test
  public void testGetAllUsers() {
    List<User> users = this.userService.getAllUsers();

    assertEquals(2, users.size());
    assertEquals(1L, users.get(0).getId());
    assertEquals("testemail@test.com", users.get(1).getEmail());
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
    userInfo.setUsername("user1");
    assertThrows(UsernameAlreadyTakenException.class, () -> this.userService.SignUpUser(userInfo));
    userInfo.setUsername("unique username");

    // Email already taken
    userInfo.setEmail("user1@gmail.com");
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
    DisplayUserInfoDTO displayUserInfo = new DisplayUserInfoDTO(userInfo.getUsername(), userInfo.getEmail(), 3L,
        UserRole.EMPLOYEE);
    AuthUserInfoDTO authUserInfo = new AuthUserInfoDTO(3L, UserRole.EMPLOYEE);
    assertEquals(new UserInfoDTO(displayUserInfo, authUserInfo), this.userService.SignUpUser(userInfo));
  }
}
