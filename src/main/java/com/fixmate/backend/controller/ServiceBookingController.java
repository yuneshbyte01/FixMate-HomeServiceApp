package com.fixmate.backend.controller;

import com.fixmate.backend.dto.ServiceBookingRequestDTO;
import com.fixmate.backend.dto.ServiceBookingResponseDTO;
import com.fixmate.backend.model.ServiceBooking;
import com.fixmate.backend.model.ServiceType;
import com.fixmate.backend.model.Status;
import com.fixmate.backend.model.User;
import com.fixmate.backend.model.Role;
import com.fixmate.backend.repository.ServiceTypeRepository;
import com.fixmate.backend.repository.UserRepository;
import com.fixmate.backend.service.ServiceBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow CORS for frontend
public class ServiceBookingController {

    private final UserRepository userRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceBookingService serviceBookingService;

    // Public booking endpoint for HTML form (no authentication required)
    @PostMapping("/public")
    public ResponseEntity<ServiceBookingResponseDTO> bookServicePublic(@RequestBody PublicBookingRequest request) {
        // Find or create a guest user
        User guestUser = userRepository.findByEmail("guest@example.com")
                .orElseGet(() -> {
                    User user = User.builder()
                            .email("guest@example.com")
                            .name("Guest User")
                            .role(Role.USER)
                            .isActive(true)
                            .build();
                    return userRepository.save(user);
                });

        // Find service type by name
        ServiceType serviceType = serviceTypeRepository.findByName(request.getServiceType())
                .orElseThrow(() -> new RuntimeException("Service type not found: " + request.getServiceType()));

        ServiceBooking booking = ServiceBooking.builder()
                .user(guestUser)
                .serviceType(serviceType)
                .description(request.getServiceDescription())
                .bookingDate(LocalDate.parse(request.getPreferredDate()))
                .bookingTime(LocalTime.parse(request.getPreferredTime()))
                .status(Status.PENDING)
                .build();

        ServiceBooking savedBooking = serviceBookingService.bookService(booking);
        return ResponseEntity.ok(serviceBookingService.convertToDTO(savedBooking));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ServiceBookingResponseDTO> bookService(@RequestBody ServiceBookingRequestDTO request, Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ServiceType serviceType = serviceTypeRepository.findById(request.getServiceTypeId())
                .orElseThrow(() -> new RuntimeException("Service type not found"));

        ServiceBooking booking = ServiceBooking.builder()
                .user(user)
                .serviceType(serviceType)
                .description(request.getDescription())
                .bookingDate(request.getBookingDate())
                .bookingTime(request.getBookingTime())
                .status(Status.PENDING)
                .build();

        ServiceBooking savedBooking = serviceBookingService.bookService(booking);
        return ResponseEntity.ok(serviceBookingService.convertToDTO(savedBooking));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<ServiceBookingResponseDTO>> getMyBookings(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ServiceBooking> bookings = serviceBookingService.getServiceBookingsByUser(user);

        List<ServiceBookingResponseDTO> responseDTOs = bookings.stream()
                .map(serviceBookingService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOs);
    }

    // DTO for public booking request
    public static class PublicBookingRequest {
        public String customerName;
        public String customerEmail;
        public String customerPhone;
        public String customerAddress;
        public String serviceType;
        public String preferredDate;
        public String preferredTime;
        public String urgency;
        public String serviceDescription;
        public String specialRequests;

        // Getters
        public String getCustomerName() { return customerName; }
        public String getCustomerEmail() { return customerEmail; }
        public String getCustomerPhone() { return customerPhone; }
        public String getCustomerAddress() { return customerAddress; }
        public String getServiceType() { return serviceType; }
        public String getPreferredDate() { return preferredDate; }
        public String getPreferredTime() { return preferredTime; }
        public String getUrgency() { return urgency; }
        public String getServiceDescription() { return serviceDescription; }
        public String getSpecialRequests() { return specialRequests; }
    }
}
