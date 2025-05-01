package com.absolutecinema.repository;

import com.absolutecinema.entity.Salary;
import com.absolutecinema.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.YearMonth;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findByEmployeeAndYearMonth(Employee employee, YearMonth yearMonth);
}