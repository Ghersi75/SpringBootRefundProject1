package project1.app.ControllerTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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

  @Test
  public void testLoginHandler() throws Exception {
    when(this.userService.isUsernameAvailable(Mockito.anyString()))
      .thenReturn(true)
      .thenReturn(false);
    ResultActions response = mockMvc.perform(get("/user/check-username")
      .param("username", "new username thats not taken"))
      .andExpect(status().isOk());

    System.out.println(response);
  }
}
