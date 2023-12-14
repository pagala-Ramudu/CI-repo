package org.ci.demo.serviceImpl;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ci.demo.dto.EmployeeRequestDTO;
import org.ci.demo.dto.EmployeeResponseDTO;
import org.ci.demo.dto.EmployeeUpdateRequestDTO;
import org.ci.demo.entity.Employee;
import org.ci.demo.exception.DuplicateEntityException;
import org.ci.demo.exception.EmployeeNotFoundException;
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
		BeanUtils.copyProperties(requestDTO, employee);

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
		return employees.stream()
				.map(EmployeeHelper::mapEmployeeToResponseDTO)
				.collect(Collectors.toList());
	}

	//method to implement pagination
	@Override
	public Page<EmployeeResponseDTO> getAllEmployeesPaged(int page, int size) {
		Page<Employee> employeesPage = employeeRepository.findAll(PageRequest.of(page, size));

		if (employeesPage == null || employeesPage.isEmpty()) {
			// Return an empty page
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


	//method to update employee from db
	@Override
	public EmployeeResponseDTO updateEmployee(long id, EmployeeUpdateRequestDTO requestDTO) throws EmployeeNotFoundException, DuplicateEntityException {
		// Fetch the employee data from DB
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));

		// Update employee details directly without using BeanUtils
		employee.setFirstName(requestDTO.getFirstName());
		employee.setLastName(requestDTO.getLastName());
		employee.setAge(requestDTO.getAge());
		employee.setDepartment(requestDTO.getDepartment());
		employee.setSalary(requestDTO.getSalary());

		// Save the updated employee
		Employee savedEmployee = employeeRepository.save(employee);

		return EmployeeHelper.mapEmployeeToResponseDTO(savedEmployee);
	}

	//delete employee by id
	@Override
	public void deleteEmployeeById(long id) {
		if (!employeeRepository.existsById(id)) {
			throw new EmployeeNotFoundException("User not found with ID: " +id);
		}
		employeeRepository.deleteById(id);
	}



	//check for duplicate email
	private void checkForDuplicateEmail(String email) throws DuplicateEntityException {
		//check email is already present or not
		if (employeeRepository.existsByEmail(email)) {
			//Handle the case where the employee with the given email is already exist
			throw new DuplicateEntityException("Email is already present");
		}
	}

	//check for duplicate phonenumber
	private void checkForDuplicatePhoneNumber(String phoneNumber) throws DuplicateEntityException {
		//check mobileNumber is already present or not
		if (employeeRepository.existsByPhoneNumber(phoneNumber)) {
			//Handle the case where the employee with the given mobileNumber is already exist
			throw new DuplicateEntityException("Phone number is already present");
		}
	}



}