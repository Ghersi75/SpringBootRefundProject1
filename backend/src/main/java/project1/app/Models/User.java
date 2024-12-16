package project1.app.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project1.app.DTO.UserSignUpDTO;
import project1.app.Enums.UserRole;

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
  private UserRole userRole;

  // mappedBy refers to the name of the variable in the ticket entity, which is user in this case (private User user)
  // cascade makes sure that anything referencing this user will update all associated tickets
  // orphanRemoval gets rid of any tickets that are no longer associated with this user
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  // https://stackoverflow.com/questions/31319358/jsonmanagedreference-vs-jsonbackreference for reference
  // Prevents infinite recurion when User has Ticker and Ticker has User as a field
  @JsonManagedReference
  private List<Ticket> tickets;

  public User(UserSignUpDTO userInfo) {
    this.username = userInfo.getUsername();
    this.passwordHash = userInfo.getPassword();
    this.firstName = userInfo.getFirstName();
    this.lastName = userInfo.getLastName();
    this.email = userInfo.getEmail();
    this.userRole = userInfo.getRole() == null ? UserRole.EMPLOYEE : UserRole.fromString(userInfo.getRole());
  }
}
