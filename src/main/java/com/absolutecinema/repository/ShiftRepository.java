package com.absolutecinema.repository;

import com.absolutecinema.entity.Shift;
import com.absolutecinema.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByEmployeeAndStartTimeBetweenOrderByStartTimeDesc(
            Employee employee,
            LocalDateTime startDate,
            LocalDateTime endDate);
}