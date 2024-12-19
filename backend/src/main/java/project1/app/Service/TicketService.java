package project1.app.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.annotation.Nullable;
import project1.app.DTO.NewTicketDTO;
import project1.app.DTO.UpdateTicketDTO;
import project1.app.Enums.ReimbursementType;
import project1.app.Enums.TicketStatus;
import project1.app.Models.Ticket;
import project1.app.Models.User;
import project1.app.Repository.TicketRepository;
import project1.app.Repository.UserRepository;

@Service
public class TicketService {
  private TicketRepository ticketRepository;
  private UserRepository userRepository;

  public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
    this.ticketRepository = ticketRepository;
    this.userRepository = userRepository;
  }

  public List<Ticket> getAllTickets() {
    return this.ticketRepository.findAll();
  }
  
  public List<Ticket> getAllTicketsFiltered(@Nullable Long userId, @Nullable TicketStatus ticketStatus) {
    return this.ticketRepository.findByUserIdAndTicketStatus(userId, ticketStatus);
  }

  public Ticket createTicket(NewTicketDTO ticketInfo, Long userId) {
    Optional<User> userWithId = this.userRepository.findById(userId);

    // User doesn't exist
    if (userWithId.isEmpty()) {
      // TODO: Make proper exception
      throw new RuntimeException("User not found");
    }

    // Won't throw exceptions since it's not empty
    User user = userWithId.get();

    // Will throw error if invalid
    ReimbursementType reimbursementType = ReimbursementType.fromString(ticketInfo.getReimbursementType());

    // Everything else is as expected
    Ticket newTicket = new Ticket(user, ticketInfo.getAmount(), ticketInfo.getDescription(), reimbursementType);

    return this.ticketRepository.save(newTicket);
  }

  public Ticket updateTicket(UpdateTicketDTO ticketInfo) {
    // TODO: Check valid ticketId

    Optional<Ticket> ticketFound = this.ticketRepository.findById(ticketInfo.getTicketId());

    if (ticketFound.isEmpty()) {
      // TODO: Add proper exception
      throw new RuntimeException("Ticket not found");
    }

    Ticket ticket = ticketFound.get();

    ticket.setTicketStatus(TicketStatus.fromString(ticketInfo.getTicketStatus()));
    this.ticketRepository.save(ticket);

    Optional<Ticket> updatedTicket = this.ticketRepository.findById(ticketInfo.getTicketId());

    if (updatedTicket.isEmpty()) {
      // TODO: Proper error handling
      throw new RuntimeException("Something broke rip");
    }

    return updatedTicket.get();
  }
}
