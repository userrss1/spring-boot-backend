package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired private CustomerRepo customerRepo;
    @Autowired private JwtUtil jwt;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> data) {

        Admin a = adminRepo.findByUsername(data.get("username")).orElse(null);
        if (a == null)
            return ResponseEntity.status(401).body("Invalid credentials");

        if (!encoder.matches(data.get("password"), a.getPassword()))
            return ResponseEntity.status(401).body("Invalid credentials");

        String token = jwt.generateToken(a.getId() + "", "ADMIN");

        return ResponseEntity.ok(Map.of(
                "access_token", token,
                "user", a,
                "user_type", "admin"
        ));
    }

    @PostMapping("/customer/login")
    public ResponseEntity<?> customerLogin(@RequestBody Map<String, String> data) {

        Customer c = customerRepo.findByUsername(data.get("username")).orElse(null);
        if (c == null) return ResponseEntity.status(401).body("Invalid credentials");

        // FIX: Use encoder.matches instead of plain text comparison
        if (!encoder.matches(data.get("password"), c.getPassword()))
            return ResponseEntity.status(401).body("Invalid credentials");

        String token = jwt.generateToken(c.getId() + "", "customer");

        return ResponseEntity.ok(Map.of(
                "access_token", token,
                "user", c,
                "user_type", "customer"
        ));
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> adminRegister(@RequestBody Admin admin) {
        // Check if username already exists
        if (adminRepo.findByUsername(admin.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body("Username already exists");
        }

        // Hash the password before saving
        admin.setPassword(encoder.encode(admin.getPassword()));
        Admin saved = adminRepo.save(admin);

        // Don't return the password in response
        saved.setPassword(null);

        return ResponseEntity.ok(Map.of(
                "message", "Admin registration successful",
                "user", saved
        ));
    }

    // NEW: Customer Registration
    @PostMapping("/customer/register")
    public ResponseEntity<?> customerRegister(@RequestBody Customer customer) {
        // Check if username already exists
        if (customerRepo.findByUsername(customer.getUsername()).isPresent()) {
            return ResponseEntity.status(400).body("Username already exists");
        }

        // Hash the password before saving
        customer.setPassword(encoder.encode(customer.getPassword()));
        Customer saved = customerRepo.save(customer);

        // Don't return the password in response
        saved.setPassword(null);

        return ResponseEntity.ok(Map.of(
                "message", "Registration successful",
                "user", saved
        ));
    }
}