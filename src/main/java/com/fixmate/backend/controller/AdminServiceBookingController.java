package com.fixmate.backend.controller;

import com.fixmate.backend.dto.ServiceBookingResponseDTO;
import com.fixmate.backend.dto.ServiceBookingStatusUpdateDTO;
import com.fixmate.backend.service.ServiceBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bookings")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminServiceBookingController {

    private final ServiceBookingService bookingService;

    @GetMapping
    public ResponseEntity<List<ServiceBookingResponseDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookingsForAdmin());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateBookingStatus(
            @PathVariable Long id,
            @RequestBody ServiceBookingStatusUpdateDTO statusDTO) {
        bookingService.updateBookingStatus(id, statusDTO.getStatus());
        return ResponseEntity.ok("Booking status updated successfully.");
    }
}
