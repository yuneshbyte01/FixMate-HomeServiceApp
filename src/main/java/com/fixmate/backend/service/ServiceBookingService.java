package com.fixmate.backend.service;

import com.fixmate.backend.dto.ServiceBookingResponseDTO;
import com.fixmate.backend.model.ServiceBooking;
import com.fixmate.backend.model.Status;
import com.fixmate.backend.model.User;
import com.fixmate.backend.repository.ServiceBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceBookingService {

    private final ServiceBookingRepository serviceBookingRepository;

    public ServiceBooking bookService(ServiceBooking booking) {
        if (booking.getStatus() == null) {
            booking.setStatus(Status.PENDING);
        }
        return serviceBookingRepository.save(booking);
    }

    public List<ServiceBooking> getServiceBookingsByUser(User user) {
        return serviceBookingRepository.findByUser(user);
    }

    public List<ServiceBookingResponseDTO> getAllBookingsForAdmin() {
        return serviceBookingRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public void updateBookingStatus(Long id, Status newStatus) {
        ServiceBooking booking = serviceBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == Status.COMPLETED) {
            throw new IllegalStateException("Cannot update a completed booking.");
        }

        booking.setStatus(newStatus);
        serviceBookingRepository.save(booking);
    }

    private ServiceBookingResponseDTO mapToResponseDTO(ServiceBooking booking) {
        return ServiceBookingResponseDTO.builder()
                .id(booking.getId())
                .serviceTypeName(booking.getServiceType().getName())
                .description(booking.getDescription())
                .bookingDate(booking.getBookingDate())
                .bookingTime(booking.getBookingTime())
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }

    public ServiceBookingResponseDTO convertToDTO(ServiceBooking booking) {
        return mapToResponseDTO(booking);
    }
}
