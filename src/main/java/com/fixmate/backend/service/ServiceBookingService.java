package com.fixmate.backend.service;

import com.fixmate.backend.model.ServiceBooking;
import com.fixmate.backend.model.Status;
import com.fixmate.backend.model.User;
import com.fixmate.backend.repository.ServiceBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
