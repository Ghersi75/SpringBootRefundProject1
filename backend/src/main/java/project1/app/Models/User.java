package project1.app.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project1.app.DTO.UserSignUpDTO;

enum Role {
  EMPLOYEE,
  MANAGER
}

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String username;
  
  @Column(name = "password_hash")
  private String passwordHash;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_role")
  private Role userRole;

  public User(UserSignUpDTO userInfo) {
    this.username = userInfo.getUsername();
    this.passwordHash = userInfo.getPassword();
    this.firstName = userInfo.getFirstName();
    this.lastName = userInfo.getLastName();
    this.email = userInfo.getEmail();
    if (userInfo.getRole() == null) {
      this.userRole = Role.EMPLOYEE;
    } else {
      this.userRole = userInfo.getRole() == "MANAGER" ? Role.MANAGER : Role.EMPLOYEE;
    }
  }
}
