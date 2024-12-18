package project1.app.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project1.app.DTO.AuthUserInfoDTO;
import project1.app.DTO.NewTicketDTO;
import project1.app.Enums.TicketStatus;
import project1.app.Enums.UserRole;
import project1.app.Exceptions.Status500.Status500Exception;
import project1.app.Models.Ticket;
import project1.app.Service.TicketService;
import project1.app.Utils.JWTUtil;

@RestController
@RequestMapping("/ticket")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class TicketController {
  private TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }
  
  @GetMapping("")
  public List<Ticket> getAllTicketsHandler(@RequestParam(required = false) Long userId, @RequestParam(required = false) String ticketStatus, @CookieValue(value = "jwt", defaultValue = "") String jwt) {
    if (jwt.isEmpty()) {
      // TODO: Not authorized, clear cookie and sign out
      throw new Status500Exception("Missing JWT");
    }
    AuthUserInfoDTO userInfoDTO = JWTUtil.extractUserInfoFromJWT(jwt);
    switch (userInfoDTO.getUserRole()) {
      case EMPLOYEE:
        if (userId != null && userId != userInfoDTO.getUserId()) {
          // Employee can't request other employees' tickets
          // TODO: Fix exception thrown
          throw new Status500Exception("Not authorized");
        }
        return this.ticketService.getAllTicketsFiltered(userInfoDTO.getUserId(), TicketStatus.fromString(ticketStatus));
      // Since we only have two options and switch needs a default, MANAGER can be default for this case
      // TODO: Figure out a proper way of doing this
      default:
        // Managers only care about pending tickets
        return this.ticketService.getAllTicketsFiltered(userId, TicketStatus.PENDING);
    }
  }

  @PostMapping("")
  public Ticket createTicket(@RequestBody NewTicketDTO ticketInfo, @CookieValue(value = "jwt", defaultValue = "") String jwt) {
    if (jwt.isEmpty()) {
      // TODO: Not authorized, clear cookie and sign out
      throw new Status500Exception("Missing JWT");
    }
    AuthUserInfoDTO userInfoDTO = JWTUtil.extractUserInfoFromJWT(jwt);
    
    if (userInfoDTO.getUserRole() == UserRole.MANAGER) {
      // TODO: Create proper exception
      throw new Status500Exception("Only Employees can create tickets");
    }

    // TODO: Once JWT is implemented, get userId from it
    return this.ticketService.createTicket(ticketInfo, userInfoDTO.getUserId());
  }
}
