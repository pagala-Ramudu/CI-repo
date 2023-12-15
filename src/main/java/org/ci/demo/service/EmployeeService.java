package org.ci.demo.service;


import java.util.List;

import org.ci.demo.dto.EmployeeRequestDTO;
import org.ci.demo.dto.EmployeeResponseDTO;
import org.ci.demo.exception.DuplicateEntityException;
import org.ci.demo.exception.EmployeeNotFoundException;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) throws DuplicateEntityException;

    EmployeeResponseDTO getEmployeeById(Long id);

    List<EmployeeResponseDTO> getAllEmployees();

    Page<EmployeeResponseDTO> getAllEmployeesPaged(int page, int size);


    Page<EmployeeResponseDTO> getAllEmployeesPaginationAndSorting(int page, int size, String field);

    EmployeeResponseDTO updateEmployee(long id, EmployeeRequestDTO requestDTO) throws EmployeeNotFoundException, DuplicateEntityException;

    void deleteEmployeeById(long id);

}
