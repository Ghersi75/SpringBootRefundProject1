package project1.app.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTicketDTO {
  @Positive(message = "TicketId must be positive")
  private Long ticketId;

  @NotEmpty(message = "TicketStatus cannot be blank")
  private String ticketStatus;
}
