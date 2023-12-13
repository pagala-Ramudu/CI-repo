package org.ci.demo.serviceImpl;

import org.ci.demo.dto.EmployeeAttendanceRequestDTO;
import org.ci.demo.dto.EmployeeAttendanceResponseDTO;

import org.ci.demo.entity.EmployeeAttendance;
import org.ci.demo.exception.DuplicateEntityException;
import org.ci.demo.exception.EmployeeNotFoundException;
import org.ci.demo.repository.EmployeeAttendanceRepository;
import org.ci.demo.service.EmployeeAttendanceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;


@Service
public class EmployeeAttendanceServiceImpl implements EmployeeAttendanceService {

    @Autowired
    private  EmployeeAttendanceRepository attendanceRepository;

    @Override
    public EmployeeAttendanceResponseDTO addEmployeeAttendance(EmployeeAttendanceRequestDTO requestDTO) throws DuplicateEntityException {
        // Check for duplicate getEma_employeeId
        checkForDuplicateEmployeeId(requestDTO.getEmaemployeeId());

        EmployeeAttendance attendance = mapToEntity(requestDTO);
        attendance.setCreatedAt(LocalDateTime.now());
        attendance.setUpdatedAt(LocalDateTime.now());
        EmployeeAttendance savedAttendance = attendanceRepository.save(attendance);
        return mapToResponseDTO(savedAttendance);
    }


    @Override
    public Page<EmployeeAttendanceResponseDTO> listEmployeeAttendance(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        Page<EmployeeAttendance> employeeAttendances = attendanceRepository.findAll(PageRequest.of(page, size));
        if(employeeAttendances==null && employeeAttendances.isEmpty()){
            // Return an empty page
            return Page.empty();
        }
        return mapEmployeeToEmployeeResponsePage(employeeAttendances);
    }

    @Override
    public EmployeeAttendanceResponseDTO getEmployeeAttendanceDetails(Long emaId) {
        EmployeeAttendance attendance = attendanceRepository.findById(emaId)
                .orElseThrow(() -> new EmployeeNotFoundException("employee not found with id" +emaId));
        return mapToResponseDTO(attendance);
    }

    @Override
    public EmployeeAttendanceResponseDTO editEmployeeAttendance(Long emaId, EmployeeAttendanceRequestDTO requestDTO) throws DuplicateEntityException {
        EmployeeAttendance existingAttendance = attendanceRepository.findById(emaId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + emaId));

        checkForDuplicateEmployeeId(requestDTO.getEmaemployeeId());

        // Update employee details directly without using BeanUtils
            existingAttendance.setEmaEmployeeId(requestDTO.getEmaemployeeId());
            existingAttendance.setEmaDate(requestDTO.getEmaDate());
            existingAttendance.setEmaAttendanceStatus(requestDTO.getEmaAttendanceStatus());
            existingAttendance.setUpdatedAt(LocalDateTime.now());

            EmployeeAttendance updatedAttendance = attendanceRepository.save(existingAttendance);
            return mapToResponseDTO(updatedAttendance);

    }

    @Override
    public void deleteEmployeeAttendance(Long emaId) {
        if (!attendanceRepository.existsById(emaId)) {
            throw new EmployeeNotFoundException("User not found with ID: " +emaId);
        }
        attendanceRepository.deleteById(emaId);
    }

    private EmployeeAttendanceResponseDTO mapToResponseDTO(EmployeeAttendance attendance) {
        EmployeeAttendanceResponseDTO responseDTO = new EmployeeAttendanceResponseDTO();
        responseDTO.setEmaId(attendance.getEmaId());
        responseDTO.setEmaEmployeeId(attendance.getEmaEmployeeId());
        responseDTO.setEma_date(attendance.getEmaDate());
        responseDTO.setEmaAttendanceStatus(attendance.getEmaAttendanceStatus());
        responseDTO.setCreatedAt(attendance.getCreatedAt());
        responseDTO.setUpdatedAt(attendance.getUpdatedAt());
        return responseDTO;
    }

    private EmployeeAttendance mapToEntity(EmployeeAttendanceRequestDTO requestDTO) {
        EmployeeAttendance attendance = new EmployeeAttendance();
        attendance.setEmaEmployeeId(requestDTO.getEmaemployeeId());
        attendance.setEmaDate(requestDTO.getEmaDate());
        attendance.setEmaAttendanceStatus(requestDTO.getEmaAttendanceStatus());
        return attendance;
    }

    public static Page<EmployeeAttendanceResponseDTO> mapEmployeeToEmployeeResponsePage(Page<EmployeeAttendance> employeePage) {
        return employeePage.map(employeeAttendance -> {
            EmployeeAttendanceResponseDTO responseDTO = new EmployeeAttendanceResponseDTO();
            BeanUtils.copyProperties(employeeAttendance, responseDTO);
            return responseDTO;
        });
    }


    private void checkForDuplicateEmployeeId(long ema_employeeId) throws DuplicateEntityException {
        //check ema_employeeId is already present or not
        if (attendanceRepository.existsByEmaEmployeeId(ema_employeeId)) {
            //Handle the case where the employee with the given ema_employeeId is already exist
            throw new DuplicateEntityException("employeeId is already present");
        }
    }
}