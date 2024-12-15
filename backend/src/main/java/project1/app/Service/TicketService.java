package project1.app.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import project1.app.Models.Ticket;
import project1.app.Repository.TicketRepository;

@Service
public class TicketService {
  private TicketRepository ticketRepository;

  public TicketService(TicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
  }

  public List<Ticket> getAllTickets() {
    return this.ticketRepository.findAll();
  }
}
