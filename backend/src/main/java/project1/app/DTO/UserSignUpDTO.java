package project1.app.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDTO {
  @NotBlank(message = "Username cannot be blank")
  // Min and Max are inclusive
  @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
  private String username;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email must be valid")
  private String email;

  @NotBlank(message = "Password cannot be blank")
  // @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
  // https://stackoverflow.com/a/21456918
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$", message = "Password must be between 8 and 50 characters, and must contain at least 1 uppercase letter, 1 lowercase letter, 1 special character, and 1 number")
  private String password;

  // This will be null at sign up time and can later be changed
  // TODO: Fix this logic?
  private String firstName;
  private String lastName;

  @Pattern(regexp = "EMPLOYEE|MANAGER", message = "Role must be EMPLOYEE or MANAGER")
  private String role;
}
