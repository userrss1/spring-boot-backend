package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder encoder;

    public Customer register(Customer customer) {
        customer.setPassword(encoder.encode(customer.getPassword()));
        return customerRepo.save(customer);
    }

    public Customer findByUsername(String username) {
        return customerRepo.findByUsername(username).orElse(null);
    }
}
