package org.ci.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ci.demo.dictionary.AttendanceStatus;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee_attendance")
public class EmployeeAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ema_id")
    private Long emaId;

    @Column(name = "ema_employee_id")
    private Long emaEmployeeId;

    @Column(name = "ema_date")
    private LocalDate emaDate;

    @Column(name = "ema_attendance_status")
    @Enumerated(EnumType.STRING)
    private AttendanceStatus emaAttendanceStatus;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

