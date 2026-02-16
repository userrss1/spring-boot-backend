package com.example;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String email;

    @Column(name = "password_hash")  // 🔥 Map to correct column name
    private String password;

    private String fullName;
    private String phone;
    private String address;

    private LocalDateTime createdAt = LocalDateTime.now();
}