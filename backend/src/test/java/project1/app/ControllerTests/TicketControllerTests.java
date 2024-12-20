package project1.app.ControllerTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import project1.app.Controllers.TicketController;
import project1.app.DTO.NewTicketDTO;
import project1.app.Service.TicketService;

@WebMvcTest(controllers = TicketController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TicketControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TicketService ticketService;

  @Autowired
  ObjectMapper objectMapper;

  // Header
  // "alg": "HS512"
  // Payload
  // "sub": userId
  // "userRole": userRole
  // SecretKey
  // 48b51d8ae1e4db6476cf7ea6aa3973445f6732bb219c201a88c41cfbc573f232
  // Put that info on https://jwt.io/ and DO NOT check base64 encoded

  // Hardcoded values checked with instructions above and used for all tests
  private String userId1RoleManagerJWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlclJvbGUiOiJNQU5BR0VSIn0.OgO6tSVj2LVuu5nCvbMjnWUkz-ct2k1LPmyWQoX8IZFJ_V3KHS8Gsse6PaGQq329QP324b9EO1i6a4eIueiTrw";
  private String userId2222RoleEmployeeJWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMjIyIiwidXNlclJvbGUiOiJFTVBMT1lFRSJ9.lJk5mQUauHatlzrYGjSvnc_pbMEDF2Rdq31pDmIF8_T-M1djWs_5hdypzNt3fDT6NHcWYo0ddHsoihROY4c6ow";
  private String userId3324324RoleManagerJWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMzI0MzI0IiwidXNlclJvbGUiOiJNQU5BR0VSIn0.JSk719TWBALUGZDSv0Pg8duwZSdDLYOTvDocJ6FELIG9kYrSfOUhsPHkAOetyA-xEqRvYRCSN_xZ4bwCjfJLdA";


  @Test
  public void testGetAllTickets() throws Exception {
    // Missing JWT
    mockMvc.perform(get("/ticket"))
      .andExpect(status().isUnauthorized());

    // Manager JWT fetching all tickets
    mockMvc.perform(get("/ticket")
    .cookie(new Cookie("jwt", userId1RoleManagerJWT)))
    .andExpect(status().isOk());

    // Employee JWT fetching their own tickets
    // userId param defaults to userId found in JWT if none is provided
    mockMvc.perform(get("/ticket")
    .cookie(new Cookie("jwt", userId2222RoleEmployeeJWT)))
    .andExpect(status().isOk());

    // Employee JWT fetching another employee's tickets
    mockMvc.perform(get("/ticket")
    .param("userId", "222")
    .cookie(new Cookie("jwt", userId2222RoleEmployeeJWT)))
    .andExpect(status().isUnauthorized());
  }

  @Test
  public void testCreateTicket() throws Exception {
    // No NewTicketDTO
    mockMvc.perform(post("/ticket"))
      .andExpect(status().isBadRequest());

    NewTicketDTO ticketInfo = new NewTicketDTO(1000, "Spent money", "Travel");
    // No JWT
    mockMvc.perform(post("/ticket")
      .contentType("application/json")
      .content(objectMapper.writeValueAsString(ticketInfo)))
      .andExpect(status().isUnauthorized());
  }

  @Test
  public void testUpdateTicket() throws Exception {
    // not UpdateTicketDTO
    mockMvc.perform(patch("/ticket"))
      .andExpect(status().isBadRequest());
  }
}
