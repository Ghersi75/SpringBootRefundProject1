package project1.app.Models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project1.app.Enums.ReimbursementType;
import project1.app.Enums.TicketStatus;

@Entity
@Table(name = "tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  // name refers to the field name of the foreign key in the database, in this case, in the users database, id is the foreign key field name
  @JoinColumn(name = "user_id", nullable = false)
  // https://stackoverflow.com/questions/31319358/jsonmanagedreference-vs-jsonbackreference for reference
  // Prevents infinite recurion when User has Ticker and Ticker has User as a field
  @JsonBackReference
  private User user;

  // Store as int and format on the frontend
  private int amount;

  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "ticket_status")
  private TicketStatus ticketStatus;

  @JsonFormat(pattern = "hh:mm - dd/MM/yy")
  @Column(name = "time_added")
  private LocalDateTime timeAdded;

  @Enumerated(EnumType.STRING)
  @Column(name = "reimbursement_type")
  private ReimbursementType reimbursementType;
}
