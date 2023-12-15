package org.ci.demo.controller;


import org.ci.demo.dto.EmployeeRequestDTO;
import org.ci.demo.dto.EmployeeResponseDTO;
import org.ci.demo.exception.DuplicateEntityException;
import org.ci.demo.serviceImpl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.*;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    //method to create employee
    @PostMapping("/save")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO) throws DuplicateEntityException {
        EmployeeResponseDTO responseDTO = employeeService.createEmployee(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    //method to get employee by id
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDTO responseDTO = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    //method to get all employees
    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> responseDTOList = employeeService.getAllEmployees();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployeesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<EmployeeResponseDTO> responseDTOPage = employeeService.getAllEmployeesPaged(page, size);
        return new ResponseEntity<>(responseDTOPage, HttpStatus.OK);
    }


    //method to get employees by paging and sorting order
    @GetMapping("/pagingAndSorting")
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployeesPaginationandsorting(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String field) {
        Page<EmployeeResponseDTO> responseDTOList = employeeService.getAllEmployeesPaginationAndSorting(page, size, field);
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    //update employee
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable long id, @RequestBody EmployeeRequestDTO requestDTO) throws DuplicateEntityException {
        EmployeeResponseDTO responseDTOList = employeeService.updateEmployee(id, requestDTO);
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    //method to delete employee by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("User deleted Successfulyy");
    }


}
