package org.ci.demo.repository;


import org.ci.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}

