package com.absolutecinema.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "address")
    private String address;

    @Column(name = "hiredate")
    private LocalDate hiredate;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "employee")
    private Set<Shift> shifts;
}