package project1.app.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import project1.app.DTO.UserInfoDTO;
import project1.app.DTO.UserSignUpDTO;
import project1.app.Exceptions.Status409.EmailAlreadyTakenException;
import project1.app.Exceptions.Status409.UsernameAlreadyTakenException;
import project1.app.Exceptions.Status500.UserCreationError;
import project1.app.Models.User;
import project1.app.Repository.UserRepository;
import project1.app.Utils.PasswordUtil;

@Service
public class UserService {
  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return this.userRepository.findAll();
  }

  public Boolean isUsernameAvailable(String username) {
    User user = this.userRepository.findByUsernameIgnoreCase(username);

    // Could also return user == null; but this is more readable
    if (user == null) {
      return true;
    }

    return false;
  }

  // TODO: Make custom exceptions for each one and use appropriate status codes
  public UserInfoDTO SignUpUser(UserSignUpDTO userInfo) {
    User userWithUsername = this.userRepository.findByUsernameIgnoreCase(userInfo.getUsername());
    
    if (userWithUsername != null) {
      // 409 Conflict - Username already taken
      throw new UsernameAlreadyTakenException("Username already taken");
    }

    User userWithEmail = this.userRepository.findByEmailIgnoreCase(userInfo.getEmail());

    if (userWithEmail != null) {
      // 409 Conflict - Email already taken
      throw new EmailAlreadyTakenException("Email already taken");
    }

    // Since password is validated by jakarta there's no additional check needed here
    String hashedPassword = PasswordUtil.hashPassword(userInfo.getPassword());
    // TODO: figure out
    // Not sure if this is fine or if a new object should be created
    userInfo.setPassword(hashedPassword);
    
    this.userRepository.save(new User(userInfo));

    User newUser = this.userRepository.findByUsernameIgnoreCase(userInfo.getUsername());

    if (newUser == null) {
      // 500 Internal Server Error - Something went wrong with sql
      throw new UserCreationError("User Creation Error");
    }

    UserInfoDTO newUserInfo = new UserInfoDTO(userInfo.getUsername(), userInfo.getEmail());

    return newUserInfo;
  }
}
