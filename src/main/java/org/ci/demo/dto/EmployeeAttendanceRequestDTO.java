package org.ci.demo.dto;

import lombok.Data;
import org.ci.demo.dictionary.AttendanceStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class EmployeeAttendanceRequestDTO {

    @NotNull(message = "ema_employeeId cannot blank")
    private Long emaemployeeId;

    @NotNull(message = "date cannot blank")
    @PastOrPresent(message = "date must be past or present")
    private LocalDate emaDate;

    @NotNull(message = "Attendance status cannot be null")
   // @Pattern(regexp = "^(ABSENT|PRESENT)$", message = "Invalid attendance status")
    private AttendanceStatus emaAttendanceStatus;


}