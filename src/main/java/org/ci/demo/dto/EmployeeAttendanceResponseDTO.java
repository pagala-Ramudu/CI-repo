package org.ci.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ci.demo.dictionary.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAttendanceResponseDTO {

    private Long emaId;
    private Long emaEmployeeId;
    private LocalDate ema_date;
    private AttendanceStatus emaAttendanceStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // getters and setters
}