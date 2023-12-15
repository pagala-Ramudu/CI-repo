package org.ci.demo.entity;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private String email;
    private String department;
    private double salary;


}
