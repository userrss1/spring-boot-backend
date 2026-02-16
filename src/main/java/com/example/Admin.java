package com.example;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String email;

    @Column(name = "password_hash")  // 🔥 Add this mapping
    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();
}