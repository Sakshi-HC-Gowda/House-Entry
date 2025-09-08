package com.example.houseentry.controller;

import com.example.houseentry.dto.DOB;
import com.example.houseentry.dto.PasswordResponse;
import com.example.houseentry.service.PasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PasswordController {

    private final PasswordService service;

    public PasswordController(PasswordService service) {
        this.service = service;
    }

    @PostMapping("/generate-password")
    public ResponseEntity<PasswordResponse> generate(@RequestBody DOB dob) {
        if (!isValid(dob)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PasswordResponse(-1, "Invalid Input"));
        }
        PasswordResponse response = service.generate(dob);
        if (response.getPassword() >= 0) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private boolean isValid(DOB dob) {
        if (dob == null) return false;
        int year = dob.getYear();
        int month = dob.getMonth();
        int day = dob.getDay();
        if (year < 1900 || year > 3000) return false;
        if (month < 1 || month > 12) return false;
        if (day < 1 || day > 31) return false;
        return true;
    }
}
