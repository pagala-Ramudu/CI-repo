package org.ci.demo.service;



import java.util.List;

import org.ci.demo.dto.EmployeeRequestDTO;
import org.ci.demo.dto.EmployeeResponseDTO;
import org.ci.demo.exception.DuplicateEntityException;
import org.springframework.data.domain.Page;

public interface EmployeeService {

	EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) throws DuplicateEntityException;

	EmployeeResponseDTO getEmployeeById(Long id);

	List<EmployeeResponseDTO> getAllEmployees();
	
	Page<EmployeeResponseDTO> getAllEmployeesPaged(int page, int size);


	Page<EmployeeResponseDTO> getAllEmployeespaginationandSorting(int page, int size, String field);

	EmployeeResponseDTO updateEmployee(long id, EmployeeRequestDTO requestDTO);

	

}
