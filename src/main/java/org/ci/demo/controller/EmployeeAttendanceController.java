package org.ci.demo.controller;

import org.ci.demo.dto.EmployeeAttandanceUpdateRequestDTO;
import org.ci.demo.dto.EmployeeAttendanceRequestDTO;
import org.ci.demo.dto.EmployeeAttendanceResponseDTO;
import org.ci.demo.exception.DuplicateEntityException;
import org.ci.demo.service.EmployeeAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee-attendance")
public class EmployeeAttendanceController {

    @Autowired
    private  EmployeeAttendanceService attendanceService;



    @GetMapping
    public ResponseEntity<Page<EmployeeAttendanceResponseDTO>> listEmployeeAttendance(@RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size) {
        Page<EmployeeAttendanceResponseDTO> attendanceList = attendanceService.listEmployeeAttendance(page, size);
        return ResponseEntity.ok(attendanceList);
    }
    @GetMapping("/{emaId}")
    public ResponseEntity<EmployeeAttendanceResponseDTO> getEmployeeAttendanceDetails(@PathVariable Long emaId) {
        EmployeeAttendanceResponseDTO attendanceDetails = attendanceService.getEmployeeAttendanceDetails(emaId);
        return ResponseEntity.ok(attendanceDetails);
    }

    @PostMapping("/save")
    public ResponseEntity<EmployeeAttendanceResponseDTO> addEmployeeAttendance(
            @RequestBody EmployeeAttendanceRequestDTO requestDTO) throws DuplicateEntityException {
        EmployeeAttendanceResponseDTO savedAttendance = attendanceService.addEmployeeAttendance(requestDTO);
        return new ResponseEntity<>(savedAttendance, HttpStatus.CREATED);
    }

    @PutMapping("/{emaId}")
    public ResponseEntity<EmployeeAttendanceResponseDTO> editEmployeeAttendance(
            @PathVariable Long emaId, @RequestBody EmployeeAttandanceUpdateRequestDTO requestDTO) throws DuplicateEntityException {
        EmployeeAttendanceResponseDTO updatedAttendance = attendanceService.editEmployeeAttendance(emaId, requestDTO);
        return ResponseEntity.ok(updatedAttendance);
    }

    @DeleteMapping("/{emaId}")
    public ResponseEntity<Void> deleteEmployeeAttendance(@PathVariable Long emaId) {
        attendanceService.deleteEmployeeAttendance(emaId);
        return ResponseEntity.noContent().build();
    }
}
