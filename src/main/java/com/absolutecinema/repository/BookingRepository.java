package com.absolutecinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.absolutecinema.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Additional query methods can be defined here if needed
}