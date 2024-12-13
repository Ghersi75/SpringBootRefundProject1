package project1.app.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import project1.app.Models.User;
import project1.app.Repository.UserRepository;

@Service
public class UserService {
  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers() {
    return this.userRepository.findAll();
  }
  
}
