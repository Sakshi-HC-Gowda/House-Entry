package com.example.houseentry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.houseentry.service.MemberRegistryService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class HouseEntryApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(HouseEntryApiApplication.class, args);
    }
}

@RestController
class HealthController {
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}

@RestController
class SeedController {
    @Bean
    public Object seedMembers(MemberRegistryService registryService,
                              @Value("${houseentry.seed-dobs:}") String seed) {
        if (seed != null && !seed.trim().isEmpty()) {
            List<LocalDate> dates = Arrays.stream(seed.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(s -> {
                        try { return LocalDate.parse(s); } catch (Exception e) { return null; }
                    })
                    .filter(d -> d != null)
                    .collect(Collectors.toList());
            registryService.seedFromDates(dates);
        }
        return new Object();
    }
}
