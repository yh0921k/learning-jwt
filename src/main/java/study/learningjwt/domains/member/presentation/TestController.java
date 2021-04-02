package study.learningjwt.domains.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/api/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("Test API");
  }
}
