package project1.app.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Main {
  @GetMapping("/hello")
  public String getTest() {
      return "hello";
  }
}
