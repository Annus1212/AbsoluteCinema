package com.absolutecinema.repository;

import com.absolutecinema.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByActiveTrue();

    boolean existsByEmail(String email);
}