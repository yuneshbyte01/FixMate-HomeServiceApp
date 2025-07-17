package com.fixmate.backend.repository;

import com.fixmate.backend.model.ServiceBooking;
import com.fixmate.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceBookingRepository extends JpaRepository<ServiceBooking, Long> {
    List<ServiceBooking> findByUser(User user);
}
