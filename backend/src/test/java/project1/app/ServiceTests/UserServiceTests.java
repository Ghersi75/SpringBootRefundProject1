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
    User user2 = new User();
    user2.setId(2L);
    user1.setUsername("ghersi");
    user2.setEmail("testemail@test.com");

    // getAllUsers
    when(userRepository.findAll()).thenReturn(List.of(user1, user2));

    // isUsernameAvailable
    // https://davidvlijmincx.com/posts/mockito_thenreturn_and_thenanswer/
    when(userRepository.findByUsernameIgnoreCase("user1")).thenReturn(user1);
    when(userRepository.findByUsernameIgnoreCase("ghersi")).thenReturn(user2);

    when(userRepository.findByUsernameIgnoreCase(Mockito.anyString())).thenAnswer(invocation -> {
      String username = invocation.getArgument(0, String.class).toLowerCase();

      if (username.equalsIgnoreCase(user1.getUsername()) || username.equalsIgnoreCase(user2.getUsername())) {
        // This will call methods defined above with actual values
        return invocation.callRealMethod();
      }

      return null;
    });
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


}
