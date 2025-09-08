package com.example.houseentry.service;

import com.example.houseentry.dto.DOB;
import com.example.houseentry.dto.Member;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberRegistryService {

    private final Map<LocalDate, String> birthdateToName = Collections.synchronizedMap(new HashMap<>());

    public boolean register(DOB dob) {
        LocalDate date = LocalDate.of(dob.getYear(), dob.getMonth(), dob.getDay());
        return birthdateToName.putIfAbsent(date, null) == null;
    }

    public boolean register(Member member) {
        LocalDate date = LocalDate.of(member.getYear(), member.getMonth(), member.getDay());
        String name = member.getName() == null ? null : member.getName().trim();
        String previous = birthdateToName.putIfAbsent(date, name);
        if (previous == null) return true;
        // If already exists but no name stored and new name provided, update
        if (previous == null && name != null) {
            birthdateToName.put(date, name);
        }
        return false;
    }

    public boolean isRegistered(DOB dob) {
        LocalDate date = LocalDate.of(dob.getYear(), dob.getMonth(), dob.getDay());
        return birthdateToName.containsKey(date);
    }

    public List<Member> listAll() {
        synchronized (birthdateToName) {
            return birthdateToName.entrySet().stream()
                    .map(e -> {
                        Member m = new Member();
                        m.setName(e.getValue());
                        m.setYear(e.getKey().getYear());
                        m.setMonth(e.getKey().getMonthValue());
                        m.setDay(e.getKey().getDayOfMonth());
                        return m;
                    })
                    .sorted(Comparator.comparing(Member::getYear)
                            .thenComparing(Member::getMonth)
                            .thenComparing(Member::getDay))
                    .collect(Collectors.toList());
        }
    }

    public void seedFromDates(List<LocalDate> dates) {
        if (dates == null) return;
        for (LocalDate d : dates) {
            birthdateToName.putIfAbsent(d, null);
        }
    }

    public boolean remove(DOB dob) {
        LocalDate date = LocalDate.of(dob.getYear(), dob.getMonth(), dob.getDay());
        return birthdateToName.remove(date) != null;
    }
}


