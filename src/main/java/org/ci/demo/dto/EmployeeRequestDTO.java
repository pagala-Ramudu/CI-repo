package org.ci.demo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EmployeeRequestDTO {

	@NotBlank(message = "First name is required")
	@Column(name = "first_name")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Column(name = "last_name")
	private String lastName;

	@Min(value = 0, message = "Age should not be greater than 0")
	@Max(value = 60, message = "Age should not be less than than 60")
	@Column(name = "age")
	private int age;

	@NotBlank(message = "Department is required")
	@Column(name = "department")
	private String department;

	@Min(value = 0, message = "Salary should not be less than 0")
	@Positive(message = "Salary must be positive")
	@Column(name = "salary")
	private double salary;

	@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
	private String phoneNumber;
	@Email(message = "invalid email format")
	private String email;

}
