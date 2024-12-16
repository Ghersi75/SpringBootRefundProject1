package project1.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
  private DisplayUserInfoDTO displayUserInfoDTO;
  private AuthUserInfoDTO authUserInfoDTO;
}
