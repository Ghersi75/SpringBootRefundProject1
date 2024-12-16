package project1.app.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project1.app.DTO.NewTicketDTO;
import project1.app.Enums.TicketStatus;
import project1.app.Models.Ticket;
import project1.app.Service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {
  private TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }
  
  @GetMapping("")
  public List<Ticket> getAllTicketsHandler(@RequestParam(required = false) Long userId, @RequestParam(required = false) String ticketStatus ) {
    // TicketStatus ticketStatusEnum;
    return this.ticketService.getAllTicketsFiltered(userId, TicketStatus.fromString(ticketStatus));
  }

  @PostMapping("")
  public Ticket createTicket(@RequestBody NewTicketDTO ticketInfo) {
    // TODO: Once JWT is implemented, get userId from it
    return this.ticketService.createTicket(ticketInfo);
  }
}
