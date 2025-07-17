package com.fixmate.backend.dto;

import com.fixmate.backend.model.Status;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
