package com.example.houseentry.service;

import com.example.houseentry.dto.DOB;
import com.example.houseentry.dto.PasswordResponse;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class PasswordService {

    private final MemberRegistryService registryService;

    public PasswordService(MemberRegistryService registryService) {
        this.registryService = registryService;
    }

    public PasswordResponse generate(DOB dob) {
        try {
            if (!registryService.isRegistered(dob)) {
                return new PasswordResponse(-1, "Access Denied");
            }

            LocalDate birthDate = LocalDate.of(dob.getYear(), dob.getMonth(), dob.getDay());
            LocalDate today = LocalDate.now();
            long days = ChronoUnit.DAYS.between(birthDate, today);

            if (days > 0) {
                return new PasswordResponse(days, "Door Opened");
            } else {
                return new PasswordResponse(-1, "Invalid Date");
            }
        } catch (Exception e) {
            return new PasswordResponse(-1, "Invalid Input");
        }
    }
}
