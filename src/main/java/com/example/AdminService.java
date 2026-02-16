package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;   // ✅ Correct name

    @Autowired
    private PasswordEncoder encoder;

    public Admin register(Admin admin) {
        admin.setPassword(encoder.encode(admin.getPassword()));
        return adminRepo.save(admin);    // ✅ USE adminRepo, not repo
    }

    public Admin findByUsername(String username) {
        return adminRepo.findByUsername(username).orElse(null);
    }
}


