package project1.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project1.app.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsernameIgnoreCase(String username);
}
