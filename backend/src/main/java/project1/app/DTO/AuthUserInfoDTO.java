package project1.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project1.app.Enums.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserInfoDTO {
  private Long userId;
  private UserRole userRole;
}
