package org.ci.demo.repository;


import org.ci.demo.dto.EmployeeAttendanceRequestDTO;
import org.ci.demo.entity.EmployeeAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAttendanceRepository extends JpaRepository<EmployeeAttendance, Long> {
    boolean existsByEmaEmployeeId(long ema_employeeId);
} 