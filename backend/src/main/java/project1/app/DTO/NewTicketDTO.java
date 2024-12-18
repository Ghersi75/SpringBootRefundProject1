package project1.app.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewTicketDTO {
  @Positive(message = "Amount must be greater than 0")
  private int amount;
  @NotEmpty(message = "Description cannot be empty")
  @Size(max = 500, message = "Description must be less than 500 characters")
  private String description;
  // This will get parsed into a ReimbursementType in the service layer for simplicity instead of implementing an http converter
  @NotEmpty(message = "ReimbursementType cannot be empty")
  private String reimbursementType;
}
