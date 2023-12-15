package org.ci.demo.serviceImpl;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.ci.demo.dto.EmployeeRequestDTO;
import org.ci.demo.dto.EmployeeResponseDTO;
import org.ci.demo.entity.Employee;
import org.ci.demo.exception.DuplicateEntityException;
import org.ci.demo.exception.EmployeeNotFoundException;
import org.ci.demo.exception.InvalidRequestException;
import org.ci.demo.exception.ResourceNotFoundException;
import org.ci.demo.helper.EmployeeHelper;
import org.ci.demo.repository.EmployeeRepository;
import org.ci.demo.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    //method to save employee in DB
    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) throws DuplicateEntityException {

        // Check for duplicate email or phone number
        checkForDuplicateEmail(requestDTO.getEmail());
        checkForDuplicatePhoneNumber(requestDTO.getPhoneNumber());

        //create employee object and copy properties from requestDTO to employee
        Employee employee = new Employee();

        // Update employee details
        if (requestDTO.getFirstName() != null) {
            employee.setFirstName(requestDTO.getFirstName());
        } else {
            throw new InvalidRequestException("firstname field must be provided for update");
        }

        if (requestDTO.getEmail() != null) {
            employee.setEmail(requestDTO.getEmail());
        } else {
            throw new InvalidRequestException("email field must be provided for update");
        }

        if (requestDTO.getPhoneNumber() != null) {
            employee.setPhoneNumber(requestDTO.getPhoneNumber());
        } else {
            throw new InvalidRequestException("PhoneNumber field must be provided for update");
        }

        if (requestDTO.getLastName() != null) {
            employee.setLastName(requestDTO.getLastName());
        } else {
            throw new InvalidRequestException("lastname field must be provided for update");
        }

        if (requestDTO.getAge() != 0) {
            employee.setAge(requestDTO.getAge());
        } else {
            throw new InvalidRequestException("age fields must be provided for update");
        }

        if (requestDTO.getSalary() != 0) {
            employee.setSalary(requestDTO.getSalary());
        } else {
            throw new InvalidRequestException("salary fields must be provided for update");
        }


        if (requestDTO.getDepartment() != null) {
            employee.setDepartment(requestDTO.getDepartment());
        } else {
            throw new InvalidRequestException("department fields must be provided for update");
        }

        //save an employee into DB
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeHelper.mapEmployeeToResponseDTO(savedEmployee);
    }

    //get employee by id method
    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {

        //fetch employee data from the DB by employee id
        Employee employee = employeeRepository.findById(id)

                //throw an exception if employee is not present
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        //return employeeResponseDTO
        return EmployeeHelper.mapEmployeeToResponseDTO(employee);
    }

    //method to get list of employees
    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {

        //fetch list of employees data from DB
        List<Employee> employees = employeeRepository.findAll();

        // Handle the case where no employees are present, for example, return an empty list
        if (employees == null || employees.isEmpty()) {
            return Collections.emptyList();
        }

        //convert the employee to employeeResponseDTO and return the list of employees
        return employees.stream().map(EmployeeHelper::mapEmployeeToResponseDTO).collect(Collectors.toList());
    }

    //method to implement pagination
    @Override
    public Page<EmployeeResponseDTO> getAllEmployeesPaged(int page, int size) {
        Page<Employee> employeesPage = employeeRepository.findAll(PageRequest.of(page, size));

        // check employees are null or empty
        if (employeesPage == null || employeesPage.isEmpty()) {
            return Page.empty();
        }
        //return page of employees
        return EmployeeHelper.mapEmployeePageToResponsePage(employeesPage);
    }

    //method to implement pagination and sorting
    @Override
    public Page<EmployeeResponseDTO> getAllEmployeesPaginationAndSorting(int page, int size, String field) {

        //fetch the page of employees by paging and sorting
        Page<Employee> employeesPage = employeeRepository.findAll(PageRequest.of(page, size).withSort(Sort.by(field)));

        if (employeesPage == null || employeesPage.isEmpty()) {
            // Return an empty page
            return Page.empty();
        }
        return EmployeeHelper.mapEmployeePageToResponsePage(employeesPage);
    }

    //delete employee by id
    @Override
    public void deleteEmployeeById(long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("User not found with ID: " + id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(long id, EmployeeRequestDTO requestDTO) throws EmployeeNotFoundException, InvalidRequestException, DuplicateEntityException {

        // Fetch the employee data from DB
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));

        // Update employee details
        if (requestDTO.getFirstName() != null) {
            checkForDuplicateFirstName(requestDTO.getFirstName(), id);
            employee.setFirstName(requestDTO.getFirstName());
        } else {
            throw new InvalidRequestException("firstname field must be provided for update");
        }

        if (requestDTO.getEmail() != null) {
            checkForDuplicateEmail(requestDTO.getEmail(), id);
            employee.setEmail(requestDTO.getEmail());
        } else {
            throw new InvalidRequestException("email field must be provided for update");
        }

        if (requestDTO.getPhoneNumber() != null) {
            checkForDuplicatePhoneNumber(requestDTO.getPhoneNumber(), id);
            employee.setPhoneNumber(requestDTO.getPhoneNumber());
        } else {
            throw new InvalidRequestException("PhoneNumber field must be provided for update");
        }

        if (requestDTO.getLastName() != null) {
            employee.setLastName(requestDTO.getLastName());
        } else {
            throw new InvalidRequestException("lastname field must be provided for update");
        }

        if (requestDTO.getAge() != 0) {
            employee.setAge(requestDTO.getAge());
        } else {
            throw new InvalidRequestException("age fields must be provided for update");
        }

        if (requestDTO.getSalary() != 0) {
            employee.setSalary(requestDTO.getSalary());
        } else {
            throw new InvalidRequestException("salary fields must be provided for update");
        }

        if (requestDTO.getDepartment() != null) {
            employee.setDepartment(requestDTO.getDepartment());
        } else {
            throw new InvalidRequestException("department fields must be provided for update");
        }



        // Save the updated employee
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeHelper.mapEmployeeToResponseDTO(savedEmployee);
    }

    private void checkForDuplicatePhoneNumber(String phoneNumber, long currentEmployeeId) throws DuplicateEntityException {
        Employee existingEmployee = employeeRepository.findByPhoneNumber(phoneNumber);
        if (existingEmployee != null && existingEmployee.getId() != currentEmployeeId) {
            throw new DuplicateEntityException("Phone number " + phoneNumber + " is already associated with another employee");
        }
    }

    private void checkForDuplicateEmail(String email, long currentEmployeeId) throws DuplicateEntityException {
        Employee existingEmployee = employeeRepository.findByEmail(email);
        if (existingEmployee != null && existingEmployee.getId() != currentEmployeeId) {
            throw new DuplicateEntityException("Email " + email + " is already associated with another employee");
        }
    }

    private void checkForDuplicateFirstName(String firstName, long currentEmployeeId) throws DuplicateEntityException {
        Employee existingEmployee = employeeRepository.findByFirstName(firstName);
        if (existingEmployee != null && existingEmployee.getId() != currentEmployeeId) {
            throw new DuplicateEntityException("First name " + firstName + " is already associated with another employee");
        }
    }

    //check for duplicate email
    private void checkForDuplicateEmail(String email) throws DuplicateEntityException {

        //check email is already present or not
        if (employeeRepository.existsByEmail(email)) {
            throw new DuplicateEntityException("Email is already present");
        }
    }

    //check for duplicate phonenumber
    private void checkForDuplicatePhoneNumber(String phoneNumber) throws DuplicateEntityException {

        //check mobileNumber is already present or not
        if (employeeRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateEntityException("Phone number is already present");
        }

    }



}