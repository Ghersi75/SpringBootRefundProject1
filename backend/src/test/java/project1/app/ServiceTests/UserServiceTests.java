package project1.app.ServiceTests;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import project1.app.Service.UserService;
import project1.app.Models.User;
import project1.app.Repository.UserRepository;

// https://www.geeksforgeeks.org/unit-testing-in-spring-boot-project-using-mockito-and-junit/
// Used site above for reference
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  public void setup() {
    User user1 = new User();
    user1.setId(1L);
    User user2 = new User();
    user2.setEmail("testemail@test.com");

    when(userRepository.findAll()).thenReturn(List.of(user1, user2));
  }

  @Test
  public void testGetAllUsers() {
    List<User> users = userService.getAllUsers();

    assertEquals(2, users.size());
    assertEquals(1L, users.get(0).getId());
    assertEquals("testemail@test.com", users.get(1).getEmail());
  }
}
