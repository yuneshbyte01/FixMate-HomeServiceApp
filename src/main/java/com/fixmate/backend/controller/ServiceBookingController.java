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

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class ServiceBookingController {

    private final UserRepository userRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceBookingService serviceBookingService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> bookService(@RequestBody ServiceBookingRequestDTO request, Authentication auth) {
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

        ServiceBooking saved = serviceBookingService.bookService(booking);

        ServiceBookingResponseDTO responseDTO = ServiceBookingResponseDTO.builder()
                .id(saved.getId())
                .serviceTypeName(saved.getServiceType().getName())
                .description(saved.getDescription())
                .bookingDate(saved.getBookingDate())
                .bookingTime(saved.getBookingTime())
                .status(saved.getStatus().toString())
                .createdAt(saved.getCreatedAt().toString())
                .build();

        return ResponseEntity.ok(responseDTO);
    }



    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<ServiceBookingResponseDTO>> getMyBookings(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ServiceBooking> bookings = serviceBookingService.getServiceBookingsByUser(user);

        List<ServiceBookingResponseDTO> responseDTOs = bookings.stream()
                .map(booking -> ServiceBookingResponseDTO.builder()
                        .id(booking.getId())
                        .serviceTypeName(booking.getServiceType().getName())
                        .description(booking.getDescription())
                        .bookingDate(booking.getBookingDate())
                        .bookingTime(booking.getBookingTime())
                        .status(booking.getStatus().toString())
                        .createdAt(booking.getCreatedAt().toString())
                        .build())
                .toList();

        return ResponseEntity.ok(responseDTOs);
    }


}
