package com.fixmate.backend.config;

import com.fixmate.backend.model.ServiceType;
import com.fixmate.backend.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ServiceTypeRepository serviceTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize default service types if they don't exist
        if (serviceTypeRepository.count() == 0) {
            serviceTypeRepository.save(ServiceType.builder()
                    .name("Electrician")
                    .description("Professional electrical services for your home and office")
                    .build());

            serviceTypeRepository.save(ServiceType.builder()
                    .name("Plumber")
                    .description("Expert plumbing solutions for all your water and drainage needs")
                    .build());

            serviceTypeRepository.save(ServiceType.builder()
                    .name("Carpenter")
                    .description("Skilled carpentry services for furniture and home improvements")
                    .build());

            serviceTypeRepository.save(ServiceType.builder()
                    .name("Painter")
                    .description("Professional painting services for interior and exterior spaces")
                    .build());

            serviceTypeRepository.save(ServiceType.builder()
                    .name("HVAC Technician")
                    .description("Heating, ventilation, and air conditioning services")
                    .build());

            serviceTypeRepository.save(ServiceType.builder()
                    .name("Cleaner")
                    .description("Professional cleaning services for homes and offices")
                    .build());

            System.out.println("Default service types initialized successfully!");
        }
    }
}