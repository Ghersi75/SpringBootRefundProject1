package project1.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project1.app.Enums.TicketStatus;
import project1.app.Models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
  // Query uses Model object, so SELECT FROM Ticket since Ticket is the model name
  // Also, t.user.id references the User user from the Ticket entity and the id from the User entity
  // https://www.baeldung.com/spring-data-jpa-null-parameters
  // If either value is null it will skip the check
  // For example, if userId is null the condition will be (NULL IS NULL OR :userId = t.user.id)
  // Since NULL IS NULL is true, the second part of it won't need to be checked since OR only needs either one to be a true condition
  @Query("SELECT t FROM Ticket t WHERE (:userId IS NULL OR :userId = t.user.id) AND (:ticketStatus IS NULL OR :ticketStatus = t.ticketStatus) ORDER BY t.timeAdded DESC")
  List<Ticket> findByUserIdAndTicketStatus(@Param("userId") Long userId, @Param("ticketStatus") TicketStatus ticketStatus);
}