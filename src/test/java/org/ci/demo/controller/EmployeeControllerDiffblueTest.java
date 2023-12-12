package org.ci.demo.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.ci.demo.dto.EmployeeRequestDTO;
import org.ci.demo.dto.EmployeeResponseDTO;
import org.ci.demo.serviceImpl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {EmployeeController.class})
@ExtendWith(SpringExtension.class)
class EmployeeControllerDiffblueTest {
    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeServiceImpl employeeServiceImpl;

    /**
     * Method under test: {@link EmployeeController#createEmployee(EmployeeRequestDTO)}
     */
    @Test
    void testCreateEmployee() throws Exception {
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setAge(1);
        employeeResponseDTO.setDepartment("Department");
        employeeResponseDTO.setEmail("jane.doe@example.org");
        employeeResponseDTO.setFirstName("Jane");
        employeeResponseDTO.setId(1L);
        employeeResponseDTO.setLastName("Doe");
        employeeResponseDTO.setPhoneNumber("6625550144");
        employeeResponseDTO.setSalary(10.0d);
        when(employeeServiceImpl.createEmployee(Mockito.<EmployeeRequestDTO>any())).thenReturn(employeeResponseDTO);

        EmployeeRequestDTO employeeRequestDTO = new EmployeeRequestDTO();
        employeeRequestDTO.setAge(1);
        employeeRequestDTO.setDepartment("Department");
        employeeRequestDTO.setEmail("jane.doe@example.org");
        employeeRequestDTO.setFirstName("Jane");
        employeeRequestDTO.setLastName("Doe");
        employeeRequestDTO.setPhoneNumber("6625550144");
        employeeRequestDTO.setSalary(10.0d);
        String content = (new ObjectMapper()).writeValueAsString(employeeRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employees/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"age\":1,\"department\":\"Department\",\"salary\":10.0,\"phoneNumber"
                                        + "\":\"6625550144\",\"email\":\"jane.doe@example.org\"}"));
    }

    /**
     * Method under test: {@link EmployeeController#getEmployeeById(Long)}
     */
    @Test
    void testGetEmployeeById() throws Exception {
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setAge(1);
        employeeResponseDTO.setDepartment("Department");
        employeeResponseDTO.setEmail("jane.doe@example.org");
        employeeResponseDTO.setFirstName("Jane");
        employeeResponseDTO.setId(1L);
        employeeResponseDTO.setLastName("Doe");
        employeeResponseDTO.setPhoneNumber("6625550144");
        employeeResponseDTO.setSalary(10.0d);
        when(employeeServiceImpl.getEmployeeById(Mockito.<Long>any())).thenReturn(employeeResponseDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees/{id}", 1L);
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"age\":1,\"department\":\"Department\",\"salary\":10.0,\"phoneNumber"
                                        + "\":\"6625550144\",\"email\":\"jane.doe@example.org\"}"));
    }

    /**
     * Method under test: {@link EmployeeController#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees() throws Exception {
        when(employeeServiceImpl.getAllEmployees()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees/getAll");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link EmployeeController#getAllEmployeesPaged(int, int)}
     */
    @Test
    void testGetAllEmployeesPaged() throws Exception {
        when(employeeServiceImpl.getAllEmployeesPaged(anyInt(), anyInt())).thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/employees/paged");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    /**
     * Method under test: {@link EmployeeController#updateEmployee(long, EmployeeRequestDTO)}
     */
    @Test
    void testUpdateEmployee() throws Exception {
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setAge(1);
        employeeResponseDTO.setDepartment("Department");
        employeeResponseDTO.setEmail("jane.doe@example.org");
        employeeResponseDTO.setFirstName("Jane");
        employeeResponseDTO.setId(1L);
        employeeResponseDTO.setLastName("Doe");
        employeeResponseDTO.setPhoneNumber("6625550144");
        employeeResponseDTO.setSalary(10.0d);
        when(employeeServiceImpl.updateEmployee(anyLong(), Mockito.<EmployeeRequestDTO>any()))
                .thenReturn(employeeResponseDTO);

        EmployeeRequestDTO employeeRequestDTO = new EmployeeRequestDTO();
        employeeRequestDTO.setAge(1);
        employeeRequestDTO.setDepartment("Department");
        employeeRequestDTO.setEmail("jane.doe@example.org");
        employeeRequestDTO.setFirstName("Jane");
        employeeRequestDTO.setLastName("Doe");
        employeeRequestDTO.setPhoneNumber("6625550144");
        employeeRequestDTO.setSalary(10.0d);
        String content = (new ObjectMapper()).writeValueAsString(employeeRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/employees/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"age\":1,\"department\":\"Department\",\"salary\":10.0,\"phoneNumber"
                                        + "\":\"6625550144\",\"email\":\"jane.doe@example.org\"}"));
    }

    /**
     * Method under test: {@link EmployeeController#getAllEmployeesPaginationandsorting(int, int, String)}
     */
    @Test
    void testGetAllEmployeesPaginationandsorting() throws Exception {
        when(employeeServiceImpl.getAllEmployeespaginationandSorting(anyInt(), anyInt(), Mockito.<String>any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get("/employees/pagingAndSorting")
                .param("field", "foo");
        MockHttpServletRequestBuilder paramResult2 = paramResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult2.param("size", String.valueOf(1));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}

