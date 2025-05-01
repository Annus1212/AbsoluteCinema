package com.absolutecinema.repository;

import com.absolutecinema.entity.Snack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Long> {
    // Additional query methods can be added here if needed
}