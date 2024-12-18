package project1.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewTicketDTO {
  private int amount;
  private String description;
  // This will get parsed into a ReimbursementType in the service layer for simplicity instead of implementing an http converter
  private String reimbursementType;
}
