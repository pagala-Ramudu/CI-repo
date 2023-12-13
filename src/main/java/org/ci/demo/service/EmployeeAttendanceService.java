package org.ci.demo.service;

import org.ci.demo.dto.EmployeeAttendanceRequestDTO;
import org.ci.demo.dto.EmployeeAttendanceResponseDTO;
import org.ci.demo.exception.DuplicateEntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeAttendanceService {


    Page<EmployeeAttendanceResponseDTO> listEmployeeAttendance(int page, int size);


    EmployeeAttendanceResponseDTO getEmployeeAttendanceDetails(Long emaId);

    EmployeeAttendanceResponseDTO addEmployeeAttendance(EmployeeAttendanceRequestDTO requestDTO) throws DuplicateEntityException;

    EmployeeAttendanceResponseDTO editEmployeeAttendance(Long emaId, EmployeeAttendanceRequestDTO requestDTO) throws DuplicateEntityException;

    void deleteEmployeeAttendance(Long emaId);
}