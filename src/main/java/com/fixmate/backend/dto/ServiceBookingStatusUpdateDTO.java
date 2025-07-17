package com.fixmate.backend.dto;

import com.fixmate.backend.model.Status;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ServiceBookingStatusUpdateDTO {
    private Status status;
}
