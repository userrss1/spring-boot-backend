package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/customers")
@PreAuthorize("hasRole('ADMIN')") // All endpoints require ADMIN role
public class AdminCustomerController {

    @Autowired
    private CustomerRepo customerRepo;

    // GET /api/admin/customers - Get all customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();

        // Remove passwords from response
        customers.forEach(c -> c.setPassword(null));

        return ResponseEntity.ok(customers);
    }

    // GET /api/admin/customers/{id} - Get single customer
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Integer id) {
        Customer customer = customerRepo.findById(id)
                .orElse(null);

        if (customer == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Customer not found"));
        }

        // Remove password from response
        customer.setPassword(null);

        return ResponseEntity.ok(customer);
    }

    // DELETE /api/admin/customers/{id} - Delete customer
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
        if (!customerRepo.existsById(id)) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Customer not found"));
        }

        customerRepo.deleteById(id);

        return ResponseEntity.ok(
                Map.of("message", "Customer deleted successfully")
        );
    }
}