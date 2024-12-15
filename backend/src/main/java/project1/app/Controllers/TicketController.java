package project1.app.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project1.app.Models.Ticket;
import project1.app.Service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {
  private TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }
  
  @GetMapping("/all")
  public List<Ticket> getAllTicketsHandler() {
    
    return this.ticketService.getAllTickets();
  }
}
