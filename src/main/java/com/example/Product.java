package com.example;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    private Double price;
    private Integer stock;
    private String imageUrl;

    @ManyToOne
    private Category category;

    private LocalDateTime createdAt = LocalDateTime.now();
}

