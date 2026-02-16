package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepo repo;

    @GetMapping
    public List<Product> list(@RequestParam(required = false) Integer category_id) {
        if (category_id != null)
            return repo.findByCategoryId(category_id);
        return repo.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product create(@RequestBody Product p) {
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable int id, @RequestBody Product p) {
        Product old = repo.findById(id).orElseThrow();
        old.setName(p.getName());
        old.setDescription(p.getDescription());
        old.setPrice(p.getPrice());
        old.setStock(p.getStock());
        old.setImageUrl(p.getImageUrl());
        return repo.save(old);
    }
}

