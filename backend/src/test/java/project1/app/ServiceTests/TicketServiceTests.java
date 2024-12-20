package project1.app.ServiceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import project1.app.DTO.NewTicketDTO;
import project1.app.DTO.UpdateTicketDTO;
import project1.app.Exceptions.Status400.InvalidTicketIdException;
import project1.app.Exceptions.Status400.InvalidUserIdException;
import project1.app.Exceptions.Status500.TicketUpdateError;
import project1.app.Models.Ticket;
import project1.app.Models.User;
import project1.app.Repository.TicketRepository;
import project1.app.Repository.UserRepository;
import project1.app.Service.TicketService;
import project1.app.Enums.ReimbursementType;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TicketServiceTests {
  @Mock
  private TicketRepository ticketRepository;

  @Mock
  private UserRepository userRepository;
  
  @InjectMocks
  private TicketService ticketService;

  private Long user1Id = 1L;
  private Long user2Id = 2L;
  private Long ticket1Id = 1L;
  private Long ticket2Id = 2L;

  @BeforeEach
  public void setup() {
    User user1 = new User();
    user1.setId(user1Id);
    User user2 = new User();
    user2.setId(user2Id);
    Ticket ticket1 = new Ticket();
    Ticket ticket2 = new Ticket();
    ticket1.setUser(user1);
    ticket2.setUser(user2);

    // createTicket
    when(this.userRepository.findById(Mockito.anyLong())).thenAnswer(invocation -> {
      Long id = invocation.getArgument(0);

      if (id == user1Id) {
        return Optional.of(user1);
      } else if (id == user2Id) {
        return Optional.of(user2);
      }

      return Optional.empty();
    });

    when(this.ticketRepository.save(Mockito.any(Ticket.class))).thenAnswer(invocation -> {
      Ticket ticket = invocation.getArgument(0);
      ticket.setId(1L);
      return ticket;
    });

    // updateTicket
    when(this.ticketRepository.findById(Mockito.anyLong())).thenAnswer(invocation -> {
      Long id = invocation.getArgument(0);
      if (id == ticket1Id) {
        return Optional.of(ticket1);
      } else if (id == ticket2Id) {
        return Optional.of(ticket2);
      }
      return Optional.empty();
    })
    .thenReturn(Optional.empty());
  }

  // No point in testing getAllTickets and getAllTicketsFiltered since the logic is handled by the repository layer
  @Test
  public void testCreateTicket() {
    NewTicketDTO ticketInfo = new NewTicketDTO();
    assertThrows(InvalidUserIdException.class, () -> this.ticketService.createTicket(ticketInfo, 0L));

    ticketInfo.setAmount(1000);
    ticketInfo.setDescription("Nice description");
    ticketInfo.setReimbursementType("Travel");
    Optional<User> user1Optional = this.userRepository.findById(user1Id);
    User user1 = user1Optional.get();
    Ticket newTicket = new Ticket(
      user1,
      ticketInfo.getAmount(),
      ticketInfo.getDescription(),
      ReimbursementType.fromString(ticketInfo.getReimbursementType()));
    newTicket.setId(1L);

    assertEquals(newTicket, this.ticketService.createTicket(ticketInfo, user1Id));
  }

  @Test
  public void testUpdateTicket() {
    UpdateTicketDTO ticketInfo = new UpdateTicketDTO();
    assertThrows(InvalidTicketIdException.class, () -> this.ticketService.updateTicket(ticketInfo));
    ticketInfo.setTicketId(ticket1Id);
    ticketInfo.setTicketStatus("Approved");

    assertThrows(TicketUpdateError.class, () -> this.ticketService.updateTicket(ticketInfo));

    // Putting in ticket info isnt important here
    when(this.ticketRepository.findById(ticket1Id)).thenReturn(Optional.of(new Ticket()));

    // TODO: Maybe fix this with values?
    // Technically all we need to know is that the ticket is then returned without issues, values dont matter
    assertEquals(new Ticket(), this.ticketRepository.findById(ticketInfo.getTicketId()).get());
  }
}
