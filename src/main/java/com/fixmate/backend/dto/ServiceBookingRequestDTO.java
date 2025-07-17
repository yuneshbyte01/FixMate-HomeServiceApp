package com.fixmate.backend.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceBookingRequestDTO {
    private Long serviceTypeId;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private String description;
}
