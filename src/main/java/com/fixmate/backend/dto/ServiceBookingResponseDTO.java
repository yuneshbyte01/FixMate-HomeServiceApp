package com.fixmate.backend.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceBookingResponseDTO {
    private Long id;
    private String serviceTypeName;
    private String description;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private String status;
    private String createdAt;
}
