package org.ci.demo.helper;

import org.ci.demo.dto.EmployeeResponseDTO;
import org.ci.demo.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

public class EmployeeHelper {

    //converting page of employees to EmployeeResponseDTO
    public static Page<EmployeeResponseDTO> mapEmployeePageToResponsePage(Page<Employee> employeePage) {
        return employeePage.map(employee -> {
            EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
            BeanUtils.copyProperties(employee, responseDTO);
            return responseDTO;
        });
    }

    //method to convert employee to EmployeeResponseDTO
    public static EmployeeResponseDTO mapEmployeeToResponseDTO(Employee employee) {
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        BeanUtils.copyProperties(employee, responseDTO);
        return responseDTO;
    }
}
