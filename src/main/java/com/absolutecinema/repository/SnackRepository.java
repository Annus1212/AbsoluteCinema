package com.absolutecinema.repository;

import com.absolutecinema.entity.Snack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Long> {
    List<Snack> findByQuantityGreaterThan(Integer quantity);
}