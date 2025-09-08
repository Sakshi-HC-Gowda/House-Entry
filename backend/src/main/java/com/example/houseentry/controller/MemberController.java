package com.example.houseentry.controller;

import com.example.houseentry.dto.DOB;
import com.example.houseentry.dto.Member;
import com.example.houseentry.service.MemberRegistryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {

    private final MemberRegistryService registryService;

    public MemberController(MemberRegistryService registryService) {
        this.registryService = registryService;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody Member member) {
        if (!isValid(member.getYear(), member.getMonth(), member.getDay())) {
            return ResponseEntity.badRequest().body("Invalid Input");
        }
        boolean added = registryService.register(member);
        return ResponseEntity.ok().body(added ? "Registered" : "Already Registered (updated name if empty)");
    }

    @GetMapping
    public List<Member> list() {
        return registryService.listAll();
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> registerBulk(@RequestBody List<Member> members) {
        if (members == null || members.isEmpty()) {
            return ResponseEntity.badRequest().body("Empty list");
        }
        int before = registryService.listAll().size();
        for (Member m : members) {
            if (isValid(m.getYear(), m.getMonth(), m.getDay())) {
                registryService.register(m);
            }
        }
        int added = registryService.listAll().size() - before;
        return ResponseEntity.ok().body("Added " + added + " member(s)");
    }

    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody DOB dob) {
        if (!isValid(dob)) {
            return ResponseEntity.badRequest().body("Invalid Input");
        }
        boolean removed = registryService.remove(dob);
        return ResponseEntity.ok(removed ? "Removed" : "Not Found");
    }

    private boolean isValid(DOB dob) {
        if (dob == null) return false;
        return isValid(dob.getYear(), dob.getMonth(), dob.getDay());
    }

    private boolean isValid(int year, int month, int day) {
        if (year < 1900 || year > 3000) return false;
        if (month < 1 || month > 12) return false;
        if (day < 1 || day > 31) return false;
        return true;
    }
}


