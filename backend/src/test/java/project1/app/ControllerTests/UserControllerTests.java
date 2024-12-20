package project1.app.ControllerTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import project1.app.Controllers.UserController;
import project1.app.DTO.AuthUserInfoDTO;
import project1.app.DTO.DisplayUserInfoDTO;
import project1.app.DTO.UserInfoDTO;
import project1.app.DTO.UserSignUpDTO;
import project1.app.Enums.UserRole;
import project1.app.Service.UserService;

// https://www.youtube.com/watch?v=BZBFw6fBeIU

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
  @Autowired
  private MockMvc mockMvc;

  // https://stackoverflow.com/questions/79243535/what-is-the-replacement-for-the-deprecated-mockbeans-in-springboot-3-4-0
  // Deprecated but with no replacement at the moment, makes no sense
  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  private String user1User = "user1";
  private String user1Email = "user1@gmail.com";
  private Long user1Id = 1L;
  private UserRole user1Role = UserRole.EMPLOYEE;
  private String user1RoleString = "EMPLOYEE";
  private String user1Password = "Password123$";

  @Test
  public void testCheckUsername() throws Exception {
    when(this.userService.isUsernameAvailable(Mockito.anyString()))
      .thenReturn(true)
      .thenReturn(false);
    ResultActions response = mockMvc.perform(get("/user/check-username")
      .param("username", "new username thats not taken"))
      .andExpect(status().isOk());

    System.out.println(response);
  }

  @Test
  public void testSignUpHandler() throws Exception {
    when(this.userService.SignUpUser(Mockito.any(UserSignUpDTO.class))).thenAnswer(invocation -> {
      DisplayUserInfoDTO displayUserInfoDTO = new DisplayUserInfoDTO(user1User, user1Email, user1Id, user1Role);
      AuthUserInfoDTO authUserInfoDTO = new AuthUserInfoDTO(user1Id, user1Role);
      return new UserInfoDTO(displayUserInfoDTO, authUserInfoDTO);
    });

    // TODO: Add tests for invalid body
    UserSignUpDTO userInfo = new UserSignUpDTO(user1Email, user1Email, user1Password, null, null, user1RoleString);

    mockMvc.perform(post("/user/signup")
      .contentType("application/json")
      .content(objectMapper.writeValueAsString(userInfo)))
      .andExpect(status().isCreated())
      .andExpect(cookie().exists("jwt"))
      .andExpect(cookie().exists("userInfo"));
  }
}
