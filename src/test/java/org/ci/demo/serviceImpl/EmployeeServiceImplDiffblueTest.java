package org.ci.demo.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.ci.demo.dto.EmployeeRequestDTO;
import org.ci.demo.dto.EmployeeResponseDTO;
import org.ci.demo.entity.Employee;
import org.ci.demo.exception.DuplicateEntityException;
import org.ci.demo.exception.ResourceNotFoundException;
import org.ci.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmployeeServiceImpl.class})
@ExtendWith(SpringExtension.class)
class EmployeeServiceImplDiffblueTest {
    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    /**
     * Method under test: {@link EmployeeServiceImpl#createEmployee(EmployeeRequestDTO)}
     */
    @Test
    void testCreateEmployee() throws DuplicateEntityException {
        when(employeeRepository.existsByEmail(Mockito.<String>any())).thenReturn(true);

        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setAge(1);
        requestDTO.setDepartment("Department");
        requestDTO.setEmail("jane.doe@example.org");
        requestDTO.setFirstName("Jane");
        requestDTO.setLastName("Doe");
        requestDTO.setPhoneNumber("6625550144");
        requestDTO.setSalary(10.0d);
        assertThrows(DuplicateEntityException.class, () -> employeeServiceImpl.createEmployee(requestDTO));
        verify(employeeRepository).existsByEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#createEmployee(EmployeeRequestDTO)}
     */
    @Test
    void testCreateEmployee2() throws DuplicateEntityException {
        when(employeeRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);
        when(employeeRepository.existsByPhoneNumber(Mockito.<String>any())).thenReturn(true);

        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setAge(1);
        requestDTO.setDepartment("Department");
        requestDTO.setEmail("jane.doe@example.org");
        requestDTO.setFirstName("Jane");
        requestDTO.setLastName("Doe");
        requestDTO.setPhoneNumber("6625550144");
        requestDTO.setSalary(10.0d);
        assertThrows(DuplicateEntityException.class, () -> employeeServiceImpl.createEmployee(requestDTO));
        verify(employeeRepository).existsByEmail(Mockito.<String>any());
        verify(employeeRepository).existsByPhoneNumber(Mockito.<String>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#createEmployee(EmployeeRequestDTO)}
     */
    @Test
    void testCreateEmployee3() throws DuplicateEntityException {
        when(employeeRepository.existsByEmail(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setAge(1);
        requestDTO.setDepartment("Department");
        requestDTO.setEmail("jane.doe@example.org");
        requestDTO.setFirstName("Jane");
        requestDTO.setLastName("Doe");
        requestDTO.setPhoneNumber("6625550144");
        requestDTO.setSalary(10.0d);
        assertThrows(ResourceNotFoundException.class, () -> employeeServiceImpl.createEmployee(requestDTO));
        verify(employeeRepository).existsByEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#createEmployee(EmployeeRequestDTO)}
     */
    @Test
    void testCreateEmployee4() throws DuplicateEntityException {
        Employee employee = new Employee();
        employee.setAge(1);
        employee.setDepartment("Department");
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(1L);
        employee.setLastName("Doe");
        employee.setPhoneNumber("6625550144");
        employee.setSalary(10.0d);
        when(employeeRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);
        when(employeeRepository.existsByPhoneNumber(Mockito.<String>any())).thenReturn(false);
        when(employeeRepository.save(Mockito.<Employee>any())).thenReturn(employee);

        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setAge(1);
        requestDTO.setDepartment("Department");
        requestDTO.setEmail("jane.doe@example.org");
        requestDTO.setFirstName("Jane");
        requestDTO.setLastName("Doe");
        requestDTO.setPhoneNumber("6625550144");
        requestDTO.setSalary(10.0d);
        EmployeeResponseDTO actualCreateEmployeeResult = employeeServiceImpl.createEmployee(requestDTO);
        assertEquals(1, actualCreateEmployeeResult.getAge());
        assertEquals(10.0d, actualCreateEmployeeResult.getSalary());
        assertEquals("6625550144", actualCreateEmployeeResult.getPhoneNumber());
        assertEquals("Doe", actualCreateEmployeeResult.getLastName());
        assertEquals(1L, actualCreateEmployeeResult.getId().longValue());
        assertEquals("Jane", actualCreateEmployeeResult.getFirstName());
        assertEquals("jane.doe@example.org", actualCreateEmployeeResult.getEmail());
        assertEquals("Department", actualCreateEmployeeResult.getDepartment());
        verify(employeeRepository).existsByEmail(Mockito.<String>any());
        verify(employeeRepository).existsByPhoneNumber(Mockito.<String>any());
        verify(employeeRepository).save(Mockito.<Employee>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getEmployeeById(Long)}
     */
    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setAge(1);
        employee.setDepartment("Department");
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(1L);
        employee.setLastName("Doe");
        employee.setPhoneNumber("6625550144");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        EmployeeResponseDTO actualEmployeeById = employeeServiceImpl.getEmployeeById(1L);
        assertEquals(1, actualEmployeeById.getAge());
        assertEquals(10.0d, actualEmployeeById.getSalary());
        assertEquals("6625550144", actualEmployeeById.getPhoneNumber());
        assertEquals("Doe", actualEmployeeById.getLastName());
        assertEquals(1L, actualEmployeeById.getId().longValue());
        assertEquals("Jane", actualEmployeeById.getFirstName());
        assertEquals("jane.doe@example.org", actualEmployeeById.getEmail());
        assertEquals("Department", actualEmployeeById.getDepartment());
        verify(employeeRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getEmployeeById(Long)}
     */
    @Test
    void testGetEmployeeById2() {
        Optional<Employee> emptyResult = Optional.empty();
        when(employeeRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> employeeServiceImpl.getEmployeeById(1L));
        verify(employeeRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getEmployeeById(Long)}
     */
    @Test
    void testGetEmployeeById3() {
        when(employeeRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> employeeServiceImpl.getEmployeeById(1L));
        verify(employeeRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(employeeServiceImpl.getAllEmployees().isEmpty());
        verify(employeeRepository).findAll();
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees2() {
        Employee employee = new Employee();
        employee.setAge(1);
        employee.setDepartment("Department");
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(1L);
        employee.setLastName("Doe");
        employee.setPhoneNumber("6625550144");
        employee.setSalary(10.0d);

        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        assertEquals(1, employeeServiceImpl.getAllEmployees().size());
        verify(employeeRepository).findAll();
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees3() {
        Employee employee = new Employee();
        employee.setAge(1);
        employee.setDepartment("Department");
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(1L);
        employee.setLastName("Doe");
        employee.setPhoneNumber("6625550144");
        employee.setSalary(10.0d);

        Employee employee2 = new Employee();
        employee2.setAge(0);
        employee2.setDepartment("org.ci.demo.entity.Employee");
        employee2.setEmail("john.smith@example.org");
        employee2.setFirstName("John");
        employee2.setId(2L);
        employee2.setLastName("Smith");
        employee2.setPhoneNumber("8605550118");
        employee2.setSalary(0.5d);

        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee2);
        employeeList.add(employee);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        assertEquals(2, employeeServiceImpl.getAllEmployees().size());
        verify(employeeRepository).findAll();
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees4() {
        when(employeeRepository.findAll()).thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> employeeServiceImpl.getAllEmployees());
        verify(employeeRepository).findAll();
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployeesPaged(int, int)}
     */
    @Test
    void testGetAllEmployeesPaged() {
        when(employeeRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(employeeServiceImpl.getAllEmployeesPaged(1, 3).toList().isEmpty());
        verify(employeeRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployeesPaged(int, int)}
     */
    @Test
    void testGetAllEmployeesPaged2() {
        Employee employee = new Employee();
        employee.setAge(1);
        employee.setDepartment("Department");
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(1L);
        employee.setLastName("Doe");
        employee.setPhoneNumber("6625550144");
        employee.setSalary(10.0d);

        ArrayList<Employee> content = new ArrayList<>();
        content.add(employee);
        PageImpl<Employee> pageImpl = new PageImpl<>(content);
        when(employeeRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        assertEquals(1, employeeServiceImpl.getAllEmployeesPaged(1, 3).toList().size());
        verify(employeeRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployeesPaged(int, int)}
     */
    @Test
    void testGetAllEmployeesPaged3() {
        Employee employee = new Employee();
        employee.setAge(1);
        employee.setDepartment("Department");
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(1L);
        employee.setLastName("Doe");
        employee.setPhoneNumber("6625550144");
        employee.setSalary(10.0d);

        Employee employee2 = new Employee();
        employee2.setAge(0);
        employee2.setDepartment("org.ci.demo.entity.Employee");
        employee2.setEmail("john.smith@example.org");
        employee2.setFirstName("John");
        employee2.setId(2L);
        employee2.setLastName("Smith");
        employee2.setPhoneNumber("8605550118");
        employee2.setSalary(0.5d);

        ArrayList<Employee> content = new ArrayList<>();
        content.add(employee2);
        content.add(employee);
        PageImpl<Employee> pageImpl = new PageImpl<>(content);
        when(employeeRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        assertEquals(2, employeeServiceImpl.getAllEmployeesPaged(1, 3).toList().size());
        verify(employeeRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployeesPaged(int, int)}
     */
    @Test
    void testGetAllEmployeesPaged4() {
        when(employeeRepository.findAll(Mockito.<Pageable>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> employeeServiceImpl.getAllEmployeesPaged(1, 3));
        verify(employeeRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployeespaginationandSorting(int, int, String)}
     */
    @Test
    void testGetAllEmployeespaginationandSorting() {
        when(employeeRepository.findAll(Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(employeeServiceImpl.getAllEmployeespaginationandSorting(1, 3, "Field").toList().isEmpty());
        verify(employeeRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployeespaginationandSorting(int, int, String)}
     */
    @Test
    void testGetAllEmployeespaginationandSorting2() {
        Employee employee = new Employee();
        employee.setAge(1);
        employee.setDepartment("Department");
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(1L);
        employee.setLastName("Doe");
        employee.setPhoneNumber("6625550144");
        employee.setSalary(10.0d);

        ArrayList<Employee> content = new ArrayList<>();
        content.add(employee);
        PageImpl<Employee> pageImpl = new PageImpl<>(content);
        when(employeeRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        assertEquals(1, employeeServiceImpl.getAllEmployeespaginationandSorting(1, 3, "Field").toList().size());
        verify(employeeRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployeespaginationandSorting(int, int, String)}
     */
    @Test
    void testGetAllEmployeespaginationandSorting3() {
        Employee employee = new Employee();
        employee.setAge(1);
        employee.setDepartment("Department");
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(1L);
        employee.setLastName("Doe");
        employee.setPhoneNumber("6625550144");
        employee.setSalary(10.0d);

        Employee employee2 = new Employee();
        employee2.setAge(0);
        employee2.setDepartment("org.ci.demo.entity.Employee");
        employee2.setEmail("john.smith@example.org");
        employee2.setFirstName("John");
        employee2.setId(2L);
        employee2.setLastName("Smith");
        employee2.setPhoneNumber("8605550118");
        employee2.setSalary(0.5d);

        ArrayList<Employee> content = new ArrayList<>();
        content.add(employee2);
        content.add(employee);
        PageImpl<Employee> pageImpl = new PageImpl<>(content);
        when(employeeRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        assertEquals(2, employeeServiceImpl.getAllEmployeespaginationandSorting(1, 3, "Field").toList().size());
        verify(employeeRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#getAllEmployeespaginationandSorting(int, int, String)}
     */
    @Test
    void testGetAllEmployeespaginationandSorting4() {
        when(employeeRepository.findAll(Mockito.<Pageable>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class,
                () -> employeeServiceImpl.getAllEmployeespaginationandSorting(1, 3, "Field"));
        verify(employeeRepository).findAll(Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#updateEmployee(long, EmployeeRequestDTO)}
     */
    @Test
    void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setAge(1);
        employee.setDepartment("Department");
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(1L);
        employee.setLastName("Doe");
        employee.setPhoneNumber("6625550144");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee2 = new Employee();
        employee2.setAge(1);
        employee2.setDepartment("Department");
        employee2.setEmail("jane.doe@example.org");
        employee2.setFirstName("Jane");
        employee2.setId(1L);
        employee2.setLastName("Doe");
        employee2.setPhoneNumber("6625550144");
        employee2.setSalary(10.0d);
        when(employeeRepository.save(Mockito.<Employee>any())).thenReturn(employee2);
        when(employeeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setAge(1);
        requestDTO.setDepartment("Department");
        requestDTO.setEmail("jane.doe@example.org");
        requestDTO.setFirstName("Jane");
        requestDTO.setLastName("Doe");
        requestDTO.setPhoneNumber("6625550144");
        requestDTO.setSalary(10.0d);
        EmployeeResponseDTO actualUpdateEmployeeResult = employeeServiceImpl.updateEmployee(1L, requestDTO);
        assertEquals(1, actualUpdateEmployeeResult.getAge());
        assertEquals(10.0d, actualUpdateEmployeeResult.getSalary());
        assertEquals("6625550144", actualUpdateEmployeeResult.getPhoneNumber());
        assertEquals("Doe", actualUpdateEmployeeResult.getLastName());
        assertEquals(1L, actualUpdateEmployeeResult.getId().longValue());
        assertEquals("Jane", actualUpdateEmployeeResult.getFirstName());
        assertEquals("jane.doe@example.org", actualUpdateEmployeeResult.getEmail());
        assertEquals("Department", actualUpdateEmployeeResult.getDepartment());
        verify(employeeRepository).save(Mockito.<Employee>any());
        verify(employeeRepository).findById(Mockito.<Long>any());
        assertEquals(1, requestDTO.getAge());
        assertEquals(10.0d, requestDTO.getSalary());
        assertEquals("6625550144", requestDTO.getPhoneNumber());
        assertEquals("Doe", requestDTO.getLastName());
        assertEquals("Jane", requestDTO.getFirstName());
        assertEquals("jane.doe@example.org", requestDTO.getEmail());
        assertEquals("Department", requestDTO.getDepartment());
    }

    /**
     * Method under test: {@link EmployeeServiceImpl#updateEmployee(long, EmployeeRequestDTO)}
     */
    @Test
    void testUpdateEmployee2() {
        Employee employee = new Employee();
        employee.setAge(1);
        employee.setDepartment("Department");
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(1L);
        employee.setLastName("Doe");
        employee.setPhoneNumber("6625550144");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.save(Mockito.<Employee>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        when(employeeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setAge(1);
        requestDTO.setDepartment("Department");
        requestDTO.setEmail("jane.doe@example.org");
        requestDTO.setFirstName("Jane");
        requestDTO.setLastName("Doe");
        requestDTO.setPhoneNumber("6625550144");
        requestDTO.setSalary(10.0d);
        assertThrows(ResourceNotFoundException.class, () -> employeeServiceImpl.updateEmployee(1L, requestDTO));
        verify(employeeRepository).save(Mockito.<Employee>any());
        verify(employeeRepository).findById(Mockito.<Long>any());
    }
}

