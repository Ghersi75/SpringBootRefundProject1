package project1.app.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


// Unlike SignUp, it's probably best not to give hints about password pattern
// Instead it makes more sense to just check if the user exists in the
@Data
public class UserLoginDTO {
  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email must be valid")
  private String email;

  @NotBlank(message = "Password cannot be blank")
  private String password;
}
