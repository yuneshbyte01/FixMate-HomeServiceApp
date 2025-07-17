package com.fixmate.backend.controller;

import com.fixmate.backend.dto.ServiceBookingRequestDTO;
import com.fixmate.backend.dto.ServiceBookingResponseDTO;
import com.fixmate.backend.model.ServiceBooking;
import com.fixmate.backend.model.ServiceType;
import com.fixmate.backend.model.Status;
import com.fixmate.backend.model.User;
import com.fixmate.backend.repository.ServiceTypeRepository;
import com.fixmate.backend.repository.UserRepository;
import com.fixmate.backend.service.ServiceBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class ServiceBookingController {

    private final UserRepository userRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceBookingService serviceBookingService;

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
}
