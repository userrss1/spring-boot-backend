package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private ProductRepo productRepo;
    @Autowired private CategoryRepo categoryRepo;
    @Autowired private AdminRepo adminRepo;
    @Autowired private CustomerRepo customerRepo;

    @GetMapping
    public Map<String, Long> get() {
        return Map.of(
                "total_products", productRepo.count(),
                "total_categories", categoryRepo.count(),
                "total_admins", adminRepo.count(),
                "total_customers", customerRepo.count()
        );
    }
}

