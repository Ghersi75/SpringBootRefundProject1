package project1.app.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.annotation.Nullable;
import project1.app.DTO.NewTicketDTO;
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

  public Ticket createTicket(NewTicketDTO ticketInfo) {
    // TODO: Fix once JWT works and userId is added to NewTicketDTO
    Optional<User> userWithId = this.userRepository.findById(1L);

    // User doesn't exist
    if (userWithId.isEmpty()) {
      // TODO: Make proper exception
      throw new RuntimeException();
    }

    // Won't throw exceptions since it's not empty
    User user = userWithId.get();

    // Will throw error if invalid
    ReimbursementType reimbursementType = ReimbursementType.fromString(ticketInfo.getReimbursementType());

    // Everything else is as expected
    Ticket newTicket = new Ticket(user, ticketInfo.getAmount(), ticketInfo.getDescription(), reimbursementType);

    return this.ticketRepository.save(newTicket);
  }
}
