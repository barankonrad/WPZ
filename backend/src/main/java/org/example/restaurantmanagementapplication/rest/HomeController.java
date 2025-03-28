package org.example.restaurantmanagementapplication.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping
  public ResponseEntity<String> home() {
    return ResponseEntity.ok("Hello World");
  }

  @GetMapping("/admin")
  public ResponseEntity<String> admin() {
    return ResponseEntity.ok("Hello Admin");
  }
}
